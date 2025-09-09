package iceapple.placeservice.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import iceapple.placeservice.dto.response.PlaceListResponse;
import iceapple.placeservice.dto.response.PlaceTimeCountResponse;
import iceapple.placeservice.entity.Place;
import iceapple.placeservice.service.PlaceService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/places")
public class PlaceController {

    private final PlaceService placeService;

//    @GetMapping
//    public ResponseEntity<PlaceListResponse> getPlaces() {
//        List<Place> places = placeService.findPlaces();
//
//        return ResponseEntity.ok(new PlaceListResponse(places));
//    }

    @GetMapping
    public ResponseEntity<PlaceListResponse> getPlaces() throws JsonProcessingException {
        List<Place> places = placeService.findPlaces();
        PlaceListResponse body = new PlaceListResponse(places);

        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bodyBytes = objectMapper.writeValueAsBytes(body); // 실제 JSON 직렬화

        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(bodyBytes.length); // Content-Length 명시
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @GetMapping("/{place_id}")
    public ResponseEntity<PlaceTimeCountResponse> getPlace(@PathVariable("place_id") String placeId,
                                                           @RequestParam LocalDate date) {

        return ResponseEntity.ok(placeService.findByPlaceId(placeId, date));
    }


}
