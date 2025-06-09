package iceapple.placeservice.dto.response;

import iceapple.placeservice.entity.Reservation;
import iceapple.placeservice.entity.Room;
import java.sql.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRoomResponse extends Reservation {
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


