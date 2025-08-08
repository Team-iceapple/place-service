package iceapple.placeservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRequest {
    @JsonProperty("student_number")
    private String studentNumber;

    @JsonProperty("phone_number")
    private String phoneNumber;
    private String password;

    @JsonProperty("place_id")
    private String placeId;
    private LocalDateTime date;
    private List<Integer> times;

    public ReservationRequest() {

    }

    public ReservationRequest(final String studentNumber, final String phoneNumber, final String password,
                              final String roomId, final LocalDateTime date,
                              final List<Integer> times) {
        this.studentNumber = studentNumber;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.placeId = roomId;
        this.date = date;
        this.times = times;
    }
}
