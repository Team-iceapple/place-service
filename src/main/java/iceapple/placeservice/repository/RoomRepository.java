package iceapple.placeservice.repository;

import iceapple.placeservice.entity.Room;
<<<<<<< HEAD
import iceapple.placeservice.util.TimeCount;
=======
import iceapple.placeservice.dto.response.RoomTimeCountResponse;
>>>>>>> 525b061 (resolve #2 feat: jdbc repository 구현)
import java.time.LocalDate;
import java.util.List;

public interface RoomRepository {
    List<Room> findAll();
    List<TimeCount> findTimeCount(String id, LocalDate date);
    String findRoomNameById(String id);
}
