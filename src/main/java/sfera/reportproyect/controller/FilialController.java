package sfera.reportproyect.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sfera.reportproyect.dto.ApiResponse;
import sfera.reportproyect.dto.request.req.ReqFilial;
import sfera.reportproyect.entity.UniversalEntity;
import sfera.reportproyect.entity.enums.TypeEnum;
import sfera.reportproyect.service.UniversalEntityService;

@RestController
@RequestMapping("/filial")
@RequiredArgsConstructor
public class FilialController {
    private final UniversalEntityService universalEntityService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> saveFilial(@RequestBody ReqFilial reqFilial) {
        return ResponseEntity.ok(universalEntityService.save(reqFilial, TypeEnum.FILIAL));
    }
}
