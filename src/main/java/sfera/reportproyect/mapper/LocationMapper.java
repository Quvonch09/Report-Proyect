package sfera.reportproyect.mapper;

import org.springframework.stereotype.Component;
import sfera.reportproyect.dto.response.ResLocation;
import sfera.reportproyect.entity.Location;

@Component
public class LocationMapper {

    public ResLocation toLocationDTO(Location location) {
        return ResLocation.builder()
                .id(location.getId())
                .name(location.getName())
                .filialId(location.getFilial().getId())
                .filialName(location.getFilial().getName())
                .build();
    }
}
