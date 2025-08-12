package iceapple.placeservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminReservationRequest {
    private LocalDate date;

    @JsonProperty("place_id")
    private String placeId;

    private List<Integer> times;

    @JsonProperty("user_name")
    private String userName;

    public AdminReservationRequest() {
    }

    public AdminReservationRequest(LocalDate date, String placeId, List<Integer> times,
                                   String userName) {
        this.date = date;
        this.placeId = placeId;
        this.times = times;
        this.userName = userName;
    }
}