package iceapple.placeservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AdminReservationRequest {
    private final LocalDate date;

    @JsonProperty("place_id")
    private final String placeId;

    private final List<Integer> times;

    @JsonProperty("user_name")
    private final String userName;

    @JsonProperty("res_count")
    private final int resCount;
}