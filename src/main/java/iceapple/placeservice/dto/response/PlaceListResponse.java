package iceapple.placeservice.dto.response;

import iceapple.placeservice.entity.Place;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceListResponse {
    private List<Place> places;
}
