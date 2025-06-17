package iceapple.placeservice.dto.response;

import iceapple.placeservice.entity.Room;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRoomResponse {
    private String id;
    private List<Integer> times;
    private LocalDateTime date;
    private Room room;

    public ReservationRoomResponse(final String id, final List<Integer> times, final LocalDateTime date, final Room room) {
        this.id = id;
        this.times = times;
        this.date = date;
        this.room = room;
    }

}


