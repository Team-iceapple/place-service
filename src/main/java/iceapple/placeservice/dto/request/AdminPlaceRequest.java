package iceapple.placeservice.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AdminPlaceRequest {

    private String name;
    private String description;
    private Integer placeCount;
}