package sfera.reportproyect.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sfera.reportproyect.dto.ApiResponse;
import sfera.reportproyect.dto.request.ReqReport;
import sfera.reportproyect.dto.request.ReqReportDTO;
import sfera.reportproyect.dto.response.ResReport;
import sfera.reportproyect.entity.User;
import sfera.reportproyect.entity.enums.Priority;
import sfera.reportproyect.entity.enums.ReportEnum;
import sfera.reportproyect.service.ReportService;

import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping
    @Operation(description = "Priority -> HIGH, LOWER, MIDDLE  \n Date Format -> dd/MM/yyyy HH:mm")
    public ResponseEntity<ApiResponse<String>> saveReport(@AuthenticationPrincipal User user,
                                                          @RequestBody ReqReport reqReport){
        return ResponseEntity.ok(reportService.saveReport(user, reqReport));
    }


    @PutMapping
    @Operation(description = "Priority -> HIGH, LOWER, MIDDLE  \n Date Format -> dd/MM/yyyy HH:mm")
    public ResponseEntity<ApiResponse<String>> updateReport(@AuthenticationPrincipal User user,
                                                            @RequestBody ReqReportDTO reqReportDTO){
        return ResponseEntity.ok(reportService.updateReport(user, reqReportDTO));
    }


    @DeleteMapping("/{reportId}")
    public ResponseEntity<ApiResponse<String>> deleteReport(@PathVariable Long reportId){
        return ResponseEntity.ok(reportService.deleteReport(reportId));
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ResReport>> changeStatus(@PathVariable Long id,
                                                               @RequestParam ReportEnum dto) {
        return ResponseEntity.ok(reportService.changeStatus(id, dto));
    }


    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream() {
        SseEmitter emitter = reportService.addEmitter();

        try {
            emitter.send(SseEmitter.event()
                    .name("reports")
                    .data(reportService.getAll()));
        } catch (Exception e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }



    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ResReport>>> search(@RequestParam(required = false) Long filialId,
                                                               @RequestParam(required = false) Long categoryId,
                                                               @RequestParam(required = false) Long reportTypeId,
                                                               @RequestParam(required = false) Long departmentId,
                                                               @RequestParam(required = false) Long dangerTypeId,
                                                               @RequestParam(required = false) Priority priority){
        return ResponseEntity.ok(reportService.search(
                filialId, categoryId, reportTypeId, departmentId, dangerTypeId, priority
        ));
    }
}
