package iceapple.placeservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import iceapple.placeservice.entity.Place;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ReservationPlaceResponse {
    private final String id;
    private final List<Integer> times;
    private final LocalDateTime date;
    private final Place place;

    @JsonProperty("res_count")
    private final int resCount;
}