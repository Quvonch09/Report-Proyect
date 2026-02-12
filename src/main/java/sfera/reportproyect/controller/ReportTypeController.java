package sfera.reportproyect.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sfera.reportproyect.dto.ApiResponse;
import sfera.reportproyect.dto.request.ReqFilial;
import sfera.reportproyect.dto.response.ResDTO;
import sfera.reportproyect.dto.response.ResPageable;
import sfera.reportproyect.dto.response.ResUniversalDto;
import sfera.reportproyect.entity.enums.TypeEnum;
import sfera.reportproyect.service.UniversalEntityService;

import java.util.List;

@RestController
@RequestMapping("/report-type")
@RequiredArgsConstructor
public class ReportTypeController {
    private final UniversalEntityService uniService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> create(@RequestBody ReqFilial req){
        return ResponseEntity.ok(uniService.save(req,TypeEnum.REPORT_TYPE));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ResPageable>> geByPage(@RequestParam(required = false) String name,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(uniService.getByPage(TypeEnum.REPORT_TYPE, name, page, size));
    }

    @GetMapping("/get-list")
    public ResponseEntity<ApiResponse<List<ResDTO>>> getList(){
        return ResponseEntity.ok(uniService.getList(TypeEnum.REPORT_TYPE));
    }

    @PutMapping("/{reportTypeId}")
    public ResponseEntity<ApiResponse<String>> update(@PathVariable Long reportTypeId,@RequestBody ReqFilial req){
        return ResponseEntity.ok(uniService.update(reportTypeId,req));
    }

    @GetMapping("/{reportTypeId}")
    public ResponseEntity<ApiResponse<ResUniversalDto>> getById(@PathVariable Long reportTypeId){
        return ResponseEntity.ok(uniService.getById(reportTypeId));
    }

    @DeleteMapping("/{reportTypeId}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long reportTypeId){
        return ResponseEntity.ok(uniService.delete(reportTypeId));
    }
}
