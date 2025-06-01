package iceapple.placeservice.repository;

import iceapple.placeservice.domain.Room;
import iceapple.placeservice.dto.response.RoomTimeCountResponse;
import java.time.LocalDate;
import java.util.List;

public interface RoomRepository {
    List<Room> findAll();
    RoomTimeCountResponse findById(String id, LocalDate date);
}
