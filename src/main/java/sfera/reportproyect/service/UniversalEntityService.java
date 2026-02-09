package sfera.reportproyect.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sfera.reportproyect.dto.ApiResponse;
import sfera.reportproyect.dto.request.req.ReqFilial;
import sfera.reportproyect.dto.request.res.ResPageable;
import sfera.reportproyect.entity.UniversalEntity;
import sfera.reportproyect.entity.enums.TypeEnum;
import sfera.reportproyect.repository.UniversalEntityRepository;

@Service
@RequiredArgsConstructor
public class UniversalEntityService {
    private final UniversalEntityRepository universalEntityRepository;

    public ApiResponse<String> save(ReqFilial reqFilial, TypeEnum typeEnum) {
        if (reqFilial.getName() == null || reqFilial.getName().isEmpty()) {
            return ApiResponse.error("name cannot be empty");
        }

        boolean exists = universalEntityRepository.existsByTypeEnumAndNameIgnoreCase(typeEnum, reqFilial.getName());
        if (exists) {
            return ApiResponse.error("This name already exists");
        }

            UniversalEntity universalEntity = UniversalEntity.builder()
                    .name(reqFilial.getName())
                    .active(true)
                    .typeEnum(typeEnum)
                    .build();
            universalEntityRepository.save(universalEntity);
            return ApiResponse.success(null, "Success");
    }

    public ApiResponse<ResPageable> getByPage(TypeEnum typeEnum ,String name, int page,int size){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        Page<UniversalEntity> search = universalEntityRepository.search(typeEnum, name, pageRequest);


        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalElements()
                .totalPage()
                .body()
                .build();
        return ApiResponse.success(resPageable, "Success");

    }
}
