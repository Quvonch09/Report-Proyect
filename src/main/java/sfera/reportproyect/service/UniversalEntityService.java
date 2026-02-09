package sfera.reportproyect.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sfera.reportproyect.dto.ApiResponse;
import sfera.reportproyect.dto.request.ReqFilial;
import sfera.reportproyect.entity.UniversalEntity;
import sfera.reportproyect.entity.enums.TypeEnum;
import sfera.reportproyect.repository.UniversalEntityRepository;

@Service
@RequiredArgsConstructor
public class UniversalEntityService {
    private final UniversalEntityRepository universalEntityRepository;

    public ApiResponse<String> save(ReqFilial reqFilial, TypeEnum typeEnum) {
        UniversalEntity universalEntity = UniversalEntity.builder()
                .name(reqFilial.getName())
                .active(true)
                .typeEnum(typeEnum)
                .build();
        universalEntityRepository.save(universalEntity);
        return ApiResponse.success(null,"Success");
    }
}
