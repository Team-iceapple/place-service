package iceapple.placeservice.repository;

import iceapple.placeservice.entity.Room;
import iceapple.placeservice.util.TimeCount;
import java.time.LocalDate;
import java.util.List;

public interface RoomRepository {
    List<Room> findAll();
    List<TimeCount> findTimeCount(String id, LocalDate date);
    String findRoomNameById(String id);
}
