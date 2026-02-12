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
@RequestMapping("/filial")
@RequiredArgsConstructor
public class FilialController {
    private final UniversalEntityService universalEntityService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> saveFilial(@RequestBody ReqFilial reqFilial) {
        return ResponseEntity.ok(universalEntityService.save(reqFilial, TypeEnum.FILIAL));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ResPageable>> getFilialByPage(@RequestParam(required = false) String name,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(universalEntityService.getByPage(TypeEnum.FILIAL, name, page, size));
    }

    @GetMapping("/get-list")
    public ResponseEntity<ApiResponse<List<ResDTO>>> getFilialList() {
        return ResponseEntity.ok(universalEntityService.getList(TypeEnum.FILIAL));
    }

    @GetMapping("/{filialId}")
    public ResponseEntity<ApiResponse<ResUniversalDto>> getFilialById(@PathVariable Long filialId) {
        return ResponseEntity.ok(universalEntityService.getById(filialId));
    }

    @PutMapping("/{filialId}")
    public ResponseEntity<ApiResponse<String>> updateFilial(@PathVariable Long filialId, @RequestBody ReqFilial reqFilial) {
        return ResponseEntity.ok(universalEntityService.update(filialId, reqFilial));
    }

    @DeleteMapping("/{filialId}")
    public ResponseEntity<ApiResponse<String>> deleteFilial(@PathVariable Long filialId) {
        return ResponseEntity.ok(universalEntityService.delete(filialId));
    }



}
