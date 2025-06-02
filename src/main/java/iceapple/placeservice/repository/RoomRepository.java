package iceapple.placeservice.repository;

import iceapple.placeservice.domain.Room;
import iceapple.placeservice.dto.response.RoomTimeCountResponse;
import iceapple.placeservice.util.TimeCount;
import java.time.LocalDate;
import java.util.List;
import java.util.Timer;

public interface RoomRepository {
    List<Room> findAll();
    List<TimeCount> findTimeCount(String id, LocalDate date);
    String findRoomNameById(String id);
}
