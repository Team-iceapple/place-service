package iceapple.placeservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ReservationRequest {
    @JsonProperty("student_number")
    private final String studentNumber;

    @JsonProperty("phone_number")
    private final String phoneNumber;
    private final String password;

    @JsonProperty("place_id")
    private final String placeId;
    private final LocalDateTime date;
    private final List<Integer> times;

    @JsonProperty("res_count")
    private final int resCount;
}
