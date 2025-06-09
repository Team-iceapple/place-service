package iceapple.placeservice.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Room {
    private String id;
    private String name;
    private String description;

    public Room() {}

    public Room(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Room(final String id, final String name, final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

}
