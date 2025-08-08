package iceapple.placeservice.dto.response;

import iceapple.placeservice.entity.Place;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationPlaceResponse {
    private String id;
    private List<Integer> times;
    private LocalDateTime date;
    private Place room;

    public ReservationPlaceResponse(final String id, final List<Integer> times, final LocalDateTime date, final Place room) {
        this.id = id;
        this.times = times;
        this.date = date;
        this.room = room;
    }
}