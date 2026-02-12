package sfera.reportproyect.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sfera.reportproyect.dto.ApiResponse;
import sfera.reportproyect.dto.LocationDTO;
import sfera.reportproyect.dto.request.ReqLocation;
import sfera.reportproyect.dto.response.ResDTO;
import sfera.reportproyect.dto.response.ResLocation;
import sfera.reportproyect.dto.response.ResPageable;
import sfera.reportproyect.entity.Location;
import sfera.reportproyect.entity.UniversalEntity;
import sfera.reportproyect.entity.enums.TypeEnum;
import sfera.reportproyect.exception.DataNotFoundException;
import sfera.reportproyect.mapper.LocationMapper;
import sfera.reportproyect.repository.LocationRepository;
import sfera.reportproyect.repository.UniversalEntityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final UniversalEntityRepository universalRepo;
    private final LocationMapper locationMapper;

    public ApiResponse<String> addLocation(ReqLocation req) {
        UniversalEntity filial = universalRepo.findByIdAndTypeEnum(req.getFilialId(), TypeEnum.FILIAL).
                orElseThrow(() -> new DataNotFoundException("Filial not found"));
        if (req.getName() == null || req.getName().isEmpty()) {
            return ApiResponse.error("Name cannot be empty");
        }
        boolean exists = locationRepository.existsByFilialIdAndNameIgnoreCaseAndActiveTrue(filial.getId(),req.getName());
        if (exists) {
            return ApiResponse.error("Name already exists this filial");
        }

        Location location = Location.builder()
                .name(req.getName())
                .filial(filial)
                .active(true)
                .build();
        locationRepository.save(location);
        return ApiResponse.success(null,"success");
    }

    public ApiResponse<ResPageable> getLocationPage(String name, int page,int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Location> locations = locationRepository.search(name, pageRequest);

        List<ResLocation> list = locations.stream().map(locationMapper::toLocationDTO).toList();

        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalElements(locations.getTotalElements())
                .totalPage(locations.getTotalPages())
                .body(list)
                .build();
        return ApiResponse.success(resPageable, "Success");

    }

    public ApiResponse<List<LocationDTO>> getLocationList(){
        List<Location> all = locationRepository.findAllByActiveTrue();
        List<LocationDTO> list = all.stream().map(locationMapper::toGetList).toList();
        return ApiResponse.success(list, "Success");
    }

    public ApiResponse<String> updateLocation(Long id,ReqLocation req) {
        Location location = locationRepository.findByIdAndActiveTrue(id).orElseThrow(
                () -> new DataNotFoundException("Location not found"));

        UniversalEntity filial = universalRepo.findByIdAndActiveTrue(req.getFilialId()).
                orElseThrow(() -> new DataNotFoundException("Filial not found"));

        boolean exists = locationRepository.existsByFilialIdAndNameIgnoreCaseAndIdNotAndActiveTrue(req.getFilialId(), req.getName(),id);
        if (exists) {
            return ApiResponse.error("Name already exists this filial");
        }

        location.setFilial(filial);
        location.setName(req.getName());
        locationRepository.save(location);
        return ApiResponse.success(null,"success");
    }

    public ApiResponse<String> deleteLocation(Long id){
        Location location = locationRepository.findByIdAndActiveTrue(id).
                orElseThrow(() -> new DataNotFoundException("Location not found"));
        location.setActive(false);
        locationRepository.save(location);
        return ApiResponse.success(null,"success");
    }



}
