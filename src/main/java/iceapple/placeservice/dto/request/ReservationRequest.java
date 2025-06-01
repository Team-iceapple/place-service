package iceapple.placeservice.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRequest {
    private String studentNumber;
    private String phoneNumber;
    private String password;
    private String roomId;
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
        this.roomId = roomId;
        this.date = date;
        this.times = times;
    }
}
