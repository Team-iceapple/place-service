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

    // GET /admin/places
    @GetMapping
    public ResponseEntity<Map<String, List<AdminPlaceResponse>>> getAllPlaces() {
        try {
            List<AdminPlaceResponse> places = placeService.findPlaces().stream()
                    .map(p -> new AdminPlaceResponse(p.getId(), p.getName(), p.getDescription()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(Map.of("places", places));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{place_id}")
    public ResponseEntity<Map<String, AdminPlaceResponse>> getPlace(@PathVariable("place_id") String placeId) {
        try {
            Place p = placeService.adminGetPlace(placeId);
            AdminPlaceResponse body = new AdminPlaceResponse(p.getId(), p.getName(), p.getDescription());
            return ResponseEntity.ok(Map.of("place", body));
        } catch (Exception e) {
            e.printStackTrace(); // 원인 확인용 (원하면 제거)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // POST /admin/places : 회의실 등록
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

    // DELETE /admin/places/{place_id} : 회의실 삭제
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