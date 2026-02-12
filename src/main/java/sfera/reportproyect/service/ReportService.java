package sfera.reportproyect.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sfera.reportproyect.dto.ApiResponse;
import sfera.reportproyect.dto.CategoryStatusStat;
import sfera.reportproyect.dto.StatPoint;
import sfera.reportproyect.dto.request.ReqReport;
import sfera.reportproyect.dto.request.ReqReportDTO;
import sfera.reportproyect.dto.response.*;
import sfera.reportproyect.entity.*;
import sfera.reportproyect.entity.enums.*;
import sfera.reportproyect.exception.DataNotFoundException;
import sfera.reportproyect.mapper.ReportActivityMapper;
import sfera.reportproyect.mapper.ReportMapper;
import sfera.reportproyect.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final UniversalEntityRepository universalEntityRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final ReportMapper reportMapper;
    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    private final ReportActivityRepository reportActivityRepository;
    private final ReportActivityMapper reportActivityMapper;


    public ApiResponse<String> saveReport(User user, ReqReport req) {

        UniversalEntity dangerType =
                getUniversal(req.getDangerTypeId(), TypeEnum.DANGER_TYPE, "Danger type not found");
        UniversalEntity reportType =
                getUniversal(req.getReportTypeId(), TypeEnum.REPORT_TYPE, "Report type not found");
        UniversalEntity department =
                getUniversal(req.getDepartmentId(), TypeEnum.DEPARTMENT_TYPE, "Department not found");

        Location location = getLocation(req.getLocationId());

        if (req.isAnonymous()) {
            user = userRepository.findById(2L).orElse(null);
        }

        User assignee = getEnabledUser(req.getAssigneeId(), "Assignee User not found");

        Report report = Report.builder()
                .dangerType(dangerType)
                .reportType(reportType)
                .department(department)
                .location(location)
                .assignedUser(assignee)
                .reportUser(user)
                .category(req.getCategory())
                .name(req.getReportName())
                .description(req.getDescription())
                .reportStatus(ReportEnum.NEW_REPORT)
                .fileUrls(req.getFileUrls())
                .priority(req.getPriority())
                .startTime(req.getStartTime())
                .endTime(req.getEndTime())
                .active(true)
                .build();

        reportRepository.save(report);
        sendToAll(reportMapper.resReport(report));

        saveActivity("Report created by " + user.getFirstName() + " " + user.getLastName(), report);

        return ApiResponse.success(null, "Success");
    }



    public ApiResponse<String> updateReport(User user, ReqReportDTO reqReportDTO){
        Report report = reportRepository.findByIdAndActiveTrue(reqReportDTO.getId()).orElseThrow(
                () -> new DataNotFoundException("Report not found")
        );

        UniversalEntity dangerType =
                getUniversal(reqReportDTO.getDangerTypeId(), TypeEnum.DANGER_TYPE, "Danger type not found");
        UniversalEntity reportType =
                getUniversal(reqReportDTO.getReportTypeId(), TypeEnum.REPORT_TYPE, "Report type not found");
        UniversalEntity department =
                getUniversal(reqReportDTO.getDepartmentId(), TypeEnum.DEPARTMENT_TYPE, "Department not found");

        Location location = getLocation(reqReportDTO.getLocationId());


        if (reqReportDTO.isAnonymous()){
            user = userRepository.findById(2L).orElse(null);
        }

        User assignee = getEnabledUser(reqReportDTO.getAssigneeId(), "Assignee User not found");

        report.setDangerType(dangerType);
        report.setReportType(reportType);
        report.setDepartment(department);
        report.setLocation(location);
        report.setAssignedUser(assignee);
        report.setName(reqReportDTO.getReportName());
        report.setDescription(reqReportDTO.getDescription());
        report.setReportStatus(ReportEnum.NEW_REPORT);
        report.setReportUser(user);
        report.setCategory(reqReportDTO.getCategory());
        report.setFileUrls(reqReportDTO.getFileUrls());
        report.setPriority(reqReportDTO.getPriority());
        report.setStartTime(reqReportDTO.getStartTime());
        report.setEndTime(reqReportDTO.getEndTime());
        Report save = reportRepository.save(report);

        saveActivity("Report updated by " + user.getFirstName() + " " + user.getLastName(), save);

        sendToAll(reportMapper.resReport(save));
        return ApiResponse.success(null, "Success");
    }



    public ApiResponse<String> deleteReport(Long id){
        Report report = reportRepository.findByIdAndActiveTrue(id).orElseThrow(
                () -> new DataNotFoundException("Report not found")
        );

        report.setActive(false);
        Report save = reportRepository.save(report);
        sendToAll(reportMapper.resReport(save));
        return ApiResponse.success(null, "Success");
    }


    @Transactional
    public ApiResponse<ResReport> changeStatus(Long reportId, ReportEnum toStatus) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        ReportEnum fromStatus = report.getReportStatus();
        if (fromStatus == toStatus) return ApiResponse.success(reportMapper.resReport(report), "Success");

        report.setReportStatus(toStatus);

        // vaqtlarni avtomatik qo'yish (xohlasangiz)
        if (toStatus == ReportEnum.IN_PROGRESS && report.getStartTime() == null) {
            report.setStartTime(LocalDateTime.now());
        }
        if (toStatus == ReportEnum.COMPLETED) {
            report.setCompletedTime(LocalDateTime.now());
            report.setEndTime(LocalDateTime.now());
        }

        Report saved = reportRepository.save(report);

        saveActivity("Report status changed from " + fromStatus + " to " + toStatus, saved);

        sendToAll(reportMapper.resReport(saved));
        return ApiResponse.success(reportMapper.resReport(saved), "Success");
    }


    public ApiResponse<List<ResReport>> search(Long filialId, Category category, Long reportTypeId,
                                               Long departmentId, Long dangerTypeId, Priority priority){
        List<Report> reports = reportRepository.searchReport(filialId, category != null ? category.name() : null,
                reportTypeId, departmentId, dangerTypeId, priority != null ? priority.name() : null);
        List<ResReport> list = reports.stream().map(reportMapper::resReport).toList();

        return ApiResponse.success(list, "Success");
    }



    public ApiResponse<List<ResReport>> getAll(){
        return ApiResponse.success(
                reportRepository.findAllByActiveTrue().stream().map(reportMapper::resReport).toList(), "Success");
    }



    public ApiResponse<ResReportDTO> getOne(Long id){
        Report report = reportRepository.findByIdAndActiveTrue(id).orElseThrow(
                () -> new DataNotFoundException("Report not found")
        );

        List<ResReportActivity> activities = reportActivityRepository
                .findAllByReport_Id(id).stream().map(reportActivityMapper::reportActivity).toList();

        return ApiResponse.success(reportMapper.resReportDTO(report,activities), "Success");
    }


    public ApiResponse<ResDashBoard> getCountDashboard(User user){
        Long accident = null;
        Long observation = null;
        Long nearMiss = null;
        Long incident = null;
        Long acceptedCount = null;
        Long cancelledCount = null;
        Long newReportCount = null;
        Long inProgressCount = null;
        Long completedCount = null;
        if (user.getRole().equals(Role.ROLE_ADMIN)){
            accident = reportRepository.
                    countByCategoryAndActiveTrueAndAssignedUser_Id(Category.ACCIDENT, user.getId());
            observation = reportRepository.
                    countByCategoryAndActiveTrueAndAssignedUser_Id(Category.OBSERVATION, user.getId());
            nearMiss = reportRepository.
                    countByCategoryAndActiveTrueAndAssignedUser_Id(Category.NEAR_MISS, user.getId());
            incident = reportRepository.
                    countByCategoryAndActiveTrueAndAssignedUser_Id(Category.INCIDENT, user.getId());
            acceptedCount = reportRepository.
                    countByReportStatusAndActiveTrueAndAssignedUser_Id(ReportEnum.ACCEPTED, user.getId());
            cancelledCount = reportRepository.
                    countByReportStatusAndActiveTrueAndAssignedUser_Id(ReportEnum.CANCELLED, user.getId());
            newReportCount = reportRepository.
                    countByReportStatusAndActiveTrueAndAssignedUser_Id(ReportEnum.NEW_REPORT, user.getId());
            inProgressCount = reportRepository.
                    countByReportStatusAndActiveTrueAndAssignedUser_Id(ReportEnum.IN_PROGRESS, user.getId());
            completedCount = reportRepository.
                    countByReportStatusAndActiveTrueAndAssignedUser_Id(ReportEnum.COMPLETED, user.getId());
        } else {
            accident = reportRepository.countByCategoryAndActiveTrue(Category.ACCIDENT);
            observation = reportRepository.countByCategoryAndActiveTrue(Category.OBSERVATION);
            nearMiss = reportRepository.countByCategoryAndActiveTrue(Category.NEAR_MISS);
            incident = reportRepository.countByCategoryAndActiveTrue(Category.INCIDENT);
        }
        return ApiResponse.success(resDashBoard(accident, observation, nearMiss, incident,
                acceptedCount,cancelledCount,newReportCount,inProgressCount,completedCount),"Success");
    }


    public ApiResponse<List<StatPoint>> getStatistic(User user, Date date){
        if (user.getRole().equals(Role.ROLE_ADMIN)){
            if (date.equals(Date.YEARLY)){
                return yearly(user.getId());
            } else if (date.equals(Date.MONTHLY)){
                return monthly(user.getId());
            } else {
                return weekly(user.getId());
            }
        } else {
            if (date.equals(Date.YEARLY)){
                return yearly(null);
            } else if (date.equals(Date.MONTHLY)){
                return monthly(null);
            } else {
                return weekly(null);
            }
        }
    }



    public ApiResponse<List<StatPoint>> yearly(Long userId) {
        int year = LocalDate.now().getYear();
        LocalDateTime from = LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime to   = from.plusYears(1);

        return ApiResponse.success(map(reportRepository.countByMonthRange(from, to, userId)), "Success");
    }


    public ApiResponse<List<CategoryStatusStat>> categoryStats(User user,Date date){
        if (user.getRole().equals(Role.ROLE_ADMIN)){
            if (date.equals(Date.YEARLY)){
                int year = LocalDate.now().getYear();
                LocalDateTime from = LocalDateTime.of(year, 1, 1, 0, 0);
                LocalDateTime to   = from.plusYears(1);
                return stats(user.getId(),from, to);
            } else if (date.equals(Date.MONTHLY)){
                int year = LocalDate.now().getYear();
                int month = LocalDate.now().getMonth().getValue();
                LocalDate first = LocalDate.of(year, month, 1);
                LocalDateTime from = first.atStartOfDay();
                LocalDateTime to   = first.plusMonths(1).atStartOfDay();
                return stats(user.getId(),from,to);
            } else {
                LocalDate start = LocalDate.now().with(java.time.DayOfWeek.MONDAY);
                LocalDateTime from = start.atStartOfDay();
                LocalDateTime to   = start.plusDays(7).atStartOfDay();
                return stats(user.getId(),from, to);
            }
        } else {
            if (date.equals(Date.YEARLY)){
                int year = LocalDate.now().getYear();
                LocalDateTime from = LocalDateTime.of(year, 1, 1, 0, 0);
                LocalDateTime to   = from.plusYears(1);
                return stats(null,from, to);
            } else if (date.equals(Date.MONTHLY)){
                int year = LocalDate.now().getYear();
                int month = LocalDate.now().getMonth().getValue();
                LocalDate first = LocalDate.of(year, month, 1);
                LocalDateTime from = first.atStartOfDay();
                LocalDateTime to   = first.plusMonths(1).atStartOfDay();
                return stats(null,from,to);
            } else {
                LocalDate start = LocalDate.now().with(java.time.DayOfWeek.MONDAY);
                LocalDateTime from = start.atStartOfDay();
                LocalDateTime to   = start.plusDays(7).atStartOfDay();
                return stats(null, from, to);
            }
        }
    }


    public ApiResponse<List<CategoryStatusStat>> stats(Long userId, LocalDateTime from, LocalDateTime to) {
        List<Object[]> rows = reportRepository.countByCategoryAndStatus(from, to, userId);

        // category -> (status -> count)
        Map<String, Map<String, Long>> pivot = new LinkedHashMap<>();

        for (Object[] r : rows) {
            String category = String.valueOf(r[0]);
            String status   = String.valueOf(r[1]);
            Long count      = ((Number) r[2]).longValue();

            pivot.computeIfAbsent(category, k -> new LinkedHashMap<>())
                    .put(status, count);
        }

        // Agar chartda hamma statuslar doim ko‘rinsin desangiz (0 bo‘lsa ham):
        List<String> allStatuses = Arrays.stream(ReportEnum.values())
                .map(Enum::name)
                .toList();

        List<CategoryStatusStat> list = pivot.entrySet().stream()
                .map(e -> {
                    Map<String, Long> counts = e.getValue();
                    for (String s : allStatuses) counts.putIfAbsent(s, 0L);
                    return new CategoryStatusStat(e.getKey(), counts);
                })
                .toList();

        return ApiResponse.success(list, "Success");
    }

    public ApiResponse<List<StatPoint>> monthly(Long userId) {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonth().getValue();
        LocalDate firstDay = LocalDate.of(year, month, 1);
        LocalDateTime from = firstDay.atStartOfDay();
        LocalDateTime to   = firstDay.plusMonths(1).atStartOfDay();

        return ApiResponse.success(map(reportRepository.countByDayRange(from, to, userId)),  "Success");
    }

    public ApiResponse<List<StatPoint>> weekly(Long userId) {
        // Dushanbadan yakshanbagacha
        LocalDate start = LocalDate.now().with(java.time.DayOfWeek.MONDAY);
        LocalDateTime from = start.atStartOfDay();
        LocalDateTime to   = start.plusDays(7).atStartOfDay();

        return ApiResponse.success(map(reportRepository.countByWeekDaysRange(from, to, userId)), "Success");
    }

    private List<StatPoint> map(List<Object[]> rows) {
        return rows.stream()
                .map(r -> new StatPoint(
                        String.valueOf(r[0]).trim(),
                        ((Number) r[1]).longValue()
                ))
                .toList();
    }



    // SSE
    public SseEmitter addEmitter() {
        SseEmitter emitter = new SseEmitter(0L); // no timeout
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError((e) -> emitters.remove(emitter));

        emitters.add(emitter);
        return emitter;
    }

    public void sendToAll(Object data) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name("reports")
                        .data(data));
            } catch (Exception e) {
                // Exceptionni umuman ko'rsatmasdan emitterni olib tashlash
                emitters.remove(emitter);
            }
        }
    }


    private UniversalEntity getUniversal(Long id, TypeEnum type, String msg) {
        return universalEntityRepository
                .findByIdAndTypeEnum(id, type)
                .orElseThrow(() -> new DataNotFoundException(msg));
    }

    private User getEnabledUser(Long id, String msg) {
        return userRepository
                .findByIdAndEnabledTrue(id)
                .orElseThrow(() -> new DataNotFoundException(msg));
    }

    private Location getLocation(Long id) {
        return locationRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("Location not found"));
    }



    public void saveActivity(String msg, Report report) {
        ReportActivity reportActivity = ReportActivity.builder()
                .report(report)
                .description(msg)
                .build();
        reportActivityRepository.save(reportActivity);
    }


    private ResDashBoard resDashBoard(Long accident,Long observation, Long nearMiss, Long incident,
                                      Long acceptedCount, Long cancelledCount, Long newReportCount,
                                      Long inProgressCount, Long completedCount){
        return ResDashBoard.builder()
                .nearMissCount(nearMiss)
                .incidentCount(incident)
                .accidentCount(accident)
                .observationCount(observation)
                .newReportCount(newReportCount)
                .cancelledCount(cancelledCount)
                .inProgressCount(inProgressCount)
                .completedCount(completedCount)
                .acceptedCount(acceptedCount)
                .build();
    }

}
