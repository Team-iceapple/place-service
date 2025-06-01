package iceapple.placeservice.service;

import iceapple.placeservice.domain.Room;
import iceapple.placeservice.dto.response.RoomTimeCountResponse;
import iceapple.placeservice.repository.RoomRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;

public class RoomService {

    RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findRooms() {
        return roomRepository.findAll();
    }

    public RoomTimeCountResponse findByRoomId(String id, LocalDate date) {
        return roomRepository.findById(id, date);
    }
}

