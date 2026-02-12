package sfera.reportproyect.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sfera.reportproyect.dto.ApiResponse;
import sfera.reportproyect.dto.LocationDTO;
import sfera.reportproyect.dto.request.ReqLocation;
import sfera.reportproyect.dto.response.ResPageable;
import sfera.reportproyect.service.LocationService;

import java.util.List;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> saveLocation(@RequestBody ReqLocation reqLocation) {
        return ResponseEntity.ok(locationService.addLocation(reqLocation));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ResPageable>> getLocationsPageable(@RequestParam(required = false) String name,
                                                                         @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(locationService.getLocationPage(name, page, size));
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<ApiResponse<String>> updateLocation(@PathVariable Long locationId,
                                                              @RequestBody ReqLocation reqLocation) {
        return ResponseEntity.ok(locationService.updateLocation(locationId, reqLocation));

    }

    @GetMapping("/get-list-locations")
    public ResponseEntity<ApiResponse<List<LocationDTO>>> getLocations() {
        return ResponseEntity.ok(locationService.getLocationList());
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<ApiResponse<String>> deleteLocation(@PathVariable Long locationId) {
        return ResponseEntity.ok(locationService.deleteLocation(locationId));
    }


}
