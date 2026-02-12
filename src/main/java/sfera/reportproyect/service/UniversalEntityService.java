package sfera.reportproyect.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sfera.reportproyect.dto.ApiResponse;
import sfera.reportproyect.dto.request.ReqFilial;
import sfera.reportproyect.dto.response.ResPageable;
import sfera.reportproyect.dto.response.ResUniversalDto;
import sfera.reportproyect.entity.UniversalEntity;
import sfera.reportproyect.entity.enums.TypeEnum;
import sfera.reportproyect.exception.BadRequestException;
import sfera.reportproyect.exception.DataNotFoundException;
import sfera.reportproyect.mapper.UniversalMapper;
import sfera.reportproyect.repository.UniversalEntityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversalEntityService {
    private final UniversalEntityRepository universalEntityRepository;
    private final UniversalMapper universalMapper;

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
        Page<UniversalEntity> universals = universalEntityRepository.search(typeEnum, name, pageRequest);

        List<ResUniversalDto> list = universals.stream().map(universalMapper::toUniversalDTO).toList();

        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalElements(universals.getTotalElements())
                .totalPage(universals.getTotalPages())
                .body(list)
                .build();
        return ApiResponse.success(resPageable, "Success");

    }

    public ApiResponse<List<ResUniversalDto>> getList(TypeEnum typeEnum){
        List<UniversalEntity> all = universalEntityRepository.findAllAndActiveTrue(typeEnum.name());
        List<ResUniversalDto> list = all.stream().map(universalMapper::toUniversalDTO).toList();
        return ApiResponse.success(list, "Success");
    }

    public ApiResponse<ResUniversalDto> getById(Long id){
        UniversalEntity uni = universalEntityRepository.findByIdAndActiveTrue(id).
                orElseThrow(() -> new DataNotFoundException("Xech nima topilmadi"));
        ResUniversalDto dto = universalMapper.toUniversalDTO(uni);
        return ApiResponse.success(dto,"Success");

    }

    public ApiResponse<String> update(Long id,ReqFilial reqFilial) {
        UniversalEntity uni = universalEntityRepository.findByIdAndActiveTrue(id).
                orElseThrow(() -> new DataNotFoundException("Xech nima topilmadi"));

        boolean exists = universalEntityRepository.existsByNameAndIdNot(reqFilial.getName(), id);
        if (exists) {
            return ApiResponse.error("This name already exists");
        }
        uni.setName(reqFilial.getName());
        universalEntityRepository.save(uni);
        return ApiResponse.success(null, "Success");

    }

    public ApiResponse<String> delete(Long id){
        UniversalEntity uni = universalEntityRepository.findByIdAndActiveTrue(id).
                orElseThrow(() -> new DataNotFoundException("Xech nima topilmadi"));
        uni.setActive(false);
        universalEntityRepository.save(uni);
        return ApiResponse.success(null, "Success");
    }



}
