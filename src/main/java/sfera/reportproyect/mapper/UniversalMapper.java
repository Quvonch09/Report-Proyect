package sfera.reportproyect.mapper;

import org.springframework.stereotype.Component;
import sfera.reportproyect.dto.response.ResDTO;
import sfera.reportproyect.dto.response.ResUniversalDto;
import sfera.reportproyect.entity.UniversalEntity;

@Component
public class UniversalMapper {

    public ResUniversalDto toUniversalDTO(UniversalEntity universalEntity) {

        return ResUniversalDto.builder()
                .id(universalEntity.getId())
                .name(universalEntity.getName())
                .type(universalEntity.getTypeEnum())
                .build();
    }

    public ResDTO toGetList(UniversalEntity universalEntity) {
        return ResDTO.builder()
                .id(universalEntity.getId())
                .name(universalEntity.getName())
                .build();
    }

}
