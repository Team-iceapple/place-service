package iceapple.placeservice.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RoomTimeCountResponse {
    private String roomId;
    private String name;
    private List<Integer> count;
}