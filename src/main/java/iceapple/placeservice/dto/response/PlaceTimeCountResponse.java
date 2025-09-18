package iceapple.placeservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PlaceTimeCountResponse {
    private String name;
    private int[] count;

    @JsonProperty("max_count")
    private int maxCount;
}



