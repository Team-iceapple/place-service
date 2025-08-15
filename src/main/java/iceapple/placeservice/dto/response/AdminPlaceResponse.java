package iceapple.placeservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminPlaceResponse {
    private String id;
    private String name;
    private String description;
}