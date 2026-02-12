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
@RequestMapping("/department-type")
@RequiredArgsConstructor
public class DepartmentTypeController {
    private final UniversalEntityService uniService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> create(@RequestBody ReqFilial req){
        return ResponseEntity.ok(uniService.save(req, TypeEnum.DEPARTMENT_TYPE));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ResPageable>> getPage(@RequestParam(required = false) String name,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(uniService.getByPage(TypeEnum.DEPARTMENT_TYPE, name, page, size));

    }

    @GetMapping("/get-list")
    public ResponseEntity<ApiResponse<List<ResDTO>>> getList(){
        return ResponseEntity.ok(uniService.getList(TypeEnum.DEPARTMENT_TYPE));
    }

    @GetMapping("/{departmentTypeId}")
    public ResponseEntity<ApiResponse<ResUniversalDto>> getById(@PathVariable Long departmentTypeId){
        return ResponseEntity.ok(uniService.getById(departmentTypeId));
    }

    @PutMapping("/{departmentTypeId}")
    public ResponseEntity<ApiResponse<String>> update(@PathVariable Long departmentTypeId,@RequestBody ReqFilial req){
        return ResponseEntity.ok(uniService.update(departmentTypeId, req));
    }

    @DeleteMapping("/{departmentTypeId}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long departmentTypeId){
        return ResponseEntity.ok(uniService.delete(departmentTypeId));
    }
}
