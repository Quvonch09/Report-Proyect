package sfera.reportproyect.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sfera.reportproyect.dto.ApiResponse;
import sfera.reportproyect.dto.request.ReqFilial;
import sfera.reportproyect.dto.response.ResPageable;
import sfera.reportproyect.dto.response.ResUniversalDto;
import sfera.reportproyect.entity.enums.TypeEnum;
import sfera.reportproyect.service.UniversalEntityService;

import java.util.List;

@RestController
@RequestMapping("/report-type")
@RequiredArgsConstructor
public class ReportTypeController {
    private final UniversalEntityService universalEntityService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> saveReportType(@RequestBody ReqFilial reqFilial) {
        return ResponseEntity.ok(universalEntityService.save(reqFilial, TypeEnum.REPORT_TYPE));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ResPageable>> getReportByPage(@RequestBody String name,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(universalEntityService.getByPage(TypeEnum.REPORT_TYPE, name, page, size));
    }

    @GetMapping("/get-list")
    public ResponseEntity<ApiResponse<List<ResUniversalDto>>> getFilialList() {
        return ResponseEntity.ok(universalEntityService.getList(TypeEnum.REPORT_TYPE));
    }

    @GetMapping("/{reportTypeId}")
    public ResponseEntity<ApiResponse<ResUniversalDto>> getReportTypeById(@PathVariable Long reportTypeId) {
        return ResponseEntity.ok(universalEntityService.getById(reportTypeId));
    }

    @PutMapping("/{reportTypeId}")
    public ResponseEntity<ApiResponse<String>> updateReportType(@PathVariable Long reportTypeId, @RequestBody ReqFilial reqFilial) {
        return ResponseEntity.ok(universalEntityService.update(reportTypeId, reqFilial));
    }

    @DeleteMapping("/{reportTypeId}")
    public ResponseEntity<ApiResponse<String>> deleteReportType(@PathVariable Long reportTypeId) {
        return ResponseEntity.ok(universalEntityService.delete(reportTypeId));
    }
}
