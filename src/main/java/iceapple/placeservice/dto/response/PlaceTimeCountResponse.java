package iceapple.placeservice.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PlaceTimeCountResponse {
    private String name;
    private int[] count;
}



