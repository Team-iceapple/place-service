package iceapple.placeservice.web;

import iceapple.placeservice.dto.request.AdminPlaceRequest;
import iceapple.placeservice.dto.response.AdminPlaceResponse;
import iceapple.placeservice.entity.Place;
import iceapple.placeservice.service.PlaceService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/places")
public class AdminPlaceController {

    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<Map<String, List<AdminPlaceResponse>>> getAllPlaces() {
        try {
            List<AdminPlaceResponse> places = placeService.findPlaces().stream()
                    .map(p -> {
                        int count = placeService.findPlaceCountById(p.getId());
                        return new AdminPlaceResponse(p.getId(), p.getName(), p.getDescription(), count);
                    })
                    .toList();
            return ResponseEntity.ok(Map.of("places", places));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Void> createPlace(@RequestBody final AdminPlaceRequest request) {
        try {
            Place saved = placeService.createPlace(
                    request.getName(),
                    request.getDescription(),
                    request.getPlaceCount()
            );
            // 예약 컨트롤러와 동일하게 바디 없이 201만 돌려도 되고, 필요하면 Location 헤더 추가
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{place_id}")
    public ResponseEntity<Void> deletePlace(@PathVariable("place_id") String placeId) {
        try {
            placeService.deletePlace(placeId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}