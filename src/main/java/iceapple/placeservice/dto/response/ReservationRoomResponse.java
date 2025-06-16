package iceapple.placeservice.dto.response;

import iceapple.placeservice.dto.RoomDTO;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRoomResponse{
    private String id;
    private List<Integer> times;
    private LocalDateTime date;
    private RoomDTO room;

    public ReservationRoomResponse(final String id, final List<Integer> times, final LocalDateTime date, final RoomDTO room) {
        this.id = id;
        this.times = times;
        this.date = date;
        this.room = room;
    }

}


