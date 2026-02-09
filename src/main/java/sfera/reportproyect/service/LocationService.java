package sfera.reportproyect.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sfera.reportproyect.dto.ApiResponse;
import sfera.reportproyect.dto.request.ReqLocation;
import sfera.reportproyect.dto.response.ResPageable;
import sfera.reportproyect.entity.Location;
import sfera.reportproyect.entity.UniversalEntity;
import sfera.reportproyect.entity.enums.TypeEnum;
import sfera.reportproyect.exception.DataNotFoundException;
import sfera.reportproyect.repository.LocationRepository;
import sfera.reportproyect.repository.UniversalEntityRepository;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final UniversalEntityRepository universalRepo;

    public ApiResponse<String> addLocation(ReqLocation req) {
        UniversalEntity filial = universalRepo.findByIdAndActiveTrue(req.getFilialId()).
                orElseThrow(() -> new DataNotFoundException("Filial not found"));
        if (req.getName() == null || req.getName().isEmpty()) {
            return ApiResponse.error("Name cannot be empty");
        }
        boolean exists = locationRepository.existsByNameAndIdNot(req.getName(), filial.getId());
        if (exists) {
            return ApiResponse.error("Name already exists this filial");
        }

        Location location = Location.builder()
                .name(req.getName())
                .filial(filial)
                .build();
        locationRepository.save(location);
        return ApiResponse.success(null,"success");
    }

    public ApiResponse<ResPageable> getLocationPage(TypeEnum type ,String name, int page,int size) {

    }


}
