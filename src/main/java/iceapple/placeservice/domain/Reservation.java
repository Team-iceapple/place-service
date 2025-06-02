package iceapple.placeservice.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reservation {
    private String id;
    private String studentNumber;
    private String phoneNumber;
    private String password;
    private String roomId;
    private LocalDateTime date;
    private List<Integer> times;
}
