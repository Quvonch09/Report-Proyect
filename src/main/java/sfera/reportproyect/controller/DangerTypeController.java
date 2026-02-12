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
@RequestMapping("/danger-type")
@RequiredArgsConstructor
public class DangerTypeController {
    private final UniversalEntityService universalEntityService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> create(@RequestBody ReqFilial req){
        return ResponseEntity.ok(universalEntityService.save(req, TypeEnum.DANGER_TYPE));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ResPageable>> getPage(@RequestParam(required = false) String name,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(universalEntityService.getByPage(TypeEnum.DANGER_TYPE, name, page, size));

    }

    @GetMapping("/get-list")
    public ResponseEntity<ApiResponse<List<ResDTO>>> getList(){
        return ResponseEntity.ok(universalEntityService.getList(TypeEnum.DANGER_TYPE));
    }

    @GetMapping("/{dangerTypeId}")
    public ResponseEntity<ApiResponse<ResUniversalDto>> getById(@PathVariable Long dangerTypeId){
        return ResponseEntity.ok(universalEntityService.getById(dangerTypeId));
    }

    @PutMapping("/{dangerTypeId}")
    public ResponseEntity<ApiResponse<String>> update(@PathVariable Long dangerTypeId,@RequestBody ReqFilial req){
        return ResponseEntity.ok(universalEntityService.update(dangerTypeId, req));
    }

    @DeleteMapping("/{dangerTypeId}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long dangerTypeId){
        return ResponseEntity.ok(universalEntityService.delete(dangerTypeId));
    }

}
