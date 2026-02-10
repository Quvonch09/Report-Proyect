package sfera.reportproyect.mapper;

import org.springframework.stereotype.Component;
import sfera.reportproyect.dto.response.ResReport;
import sfera.reportproyect.dto.response.ResReportActivity;
import sfera.reportproyect.dto.response.ResReportDTO;
import sfera.reportproyect.entity.Report;

import java.util.List;

@Component
public class ReportMapper {
    public ResReport resReport(Report report){
        return ResReport.builder()
                .id(report.getId())
                .reportName(report.getName())
                .description(report.getDescription())
                .reportType(report.getReportType() != null ? report.getReportType().getName() : null)
                .assignee(report.getAssignedUser() != null ?
                        report.getAssignedUser().getFirstName() + " " + report.getAssignedUser().getLastName() : null)
                .assigneeId(report.getAssignedUser() != null ? report.getAssignedUser().getId() : null)
                .assigneeImg(report.getAssignedUser() != null ? report.getAssignedUser().getImgUrl() : null)
                .category(report.getCategory() != null ? report.getCategory().name() : null)
                .dangerType(report.getDangerType() != null ? report.getDangerType().getName() : null)
                .department(report.getDepartment() != null ? report.getDepartment().getName() : null)
                .priority(report.getPriority())
                .reportUser(report.getReportUser() != null ?
                        report.getReportUser().getFirstName() + " " + report.getReportUser().getLastName() : null)
                .reportUserId(report.getReportUser() != null ? report.getReportUser().getId() : null)
                .reportUserImg(report.getReportUser() != null ? report.getReportUser().getImgUrl() : null)
                .fileUrls(report.getFileUrls())
                .startTime(report.getStartTime())
                .endTime(report.getEndTime())
                .status(report.getReportStatus().name())
                .location(report.getLocation() != null ? report.getLocation().getName() : null)
                .build();
    }

    public ResReportDTO resReportDTO(Report report, List<ResReportActivity> activities){
        return ResReportDTO.builder()
                .id(report.getId())
                .reportName(report.getName())
                .description(report.getDescription())
                .reportType(report.getReportType() != null ? report.getReportType().getName() : null)
                .assignee(report.getAssignedUser() != null ?
                        report.getAssignedUser().getFirstName() + " " + report.getAssignedUser().getLastName() : null)
                .assigneeId(report.getAssignedUser() != null ? report.getAssignedUser().getId() : null)
                .assigneeImg(report.getAssignedUser() != null ? report.getAssignedUser().getImgUrl() : null)
                .category(report.getCategory() != null ? report.getCategory().name() : null)
                .dangerType(report.getDangerType() != null ? report.getDangerType().getName() : null)
                .department(report.getDepartment() != null ? report.getDepartment().getName() : null)
                .priority(report.getPriority())
                .reportUser(report.getReportUser() != null ?
                        report.getReportUser().getFirstName() + " " + report.getReportUser().getLastName() : null)
                .reportUserId(report.getReportUser() != null ? report.getReportUser().getId() : null)
                .reportUserImg(report.getReportUser() != null ? report.getReportUser().getImgUrl() : null)
                .fileUrls(report.getFileUrls())
                .startTime(report.getStartTime())
                .endTime(report.getEndTime())
                .status(report.getReportStatus().name())
                .location(report.getLocation() != null ? report.getLocation().getName() : null)
                .activities(activities)
                .build();
    }
}
