package iceapple.placeservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationInfoRequest {
    private String studentNumber;
    private String password;

    public ReservationInfoRequest() {
    }

    public ReservationInfoRequest(String studentNumber, String password) {
        this.studentNumber = studentNumber;
        this.password = password;
    }
}

