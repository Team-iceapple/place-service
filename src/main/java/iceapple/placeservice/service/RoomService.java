package iceapple.placeservice.service;

import iceapple.placeservice.entity.Room;
import iceapple.placeservice.dto.response.RoomTimeCountResponse;
import iceapple.placeservice.repository.RoomRepository;
import iceapple.placeservice.util.TimeCount;
import java.time.LocalDate;
import java.util.List;

public class RoomService {

    RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findRooms() {
        return roomRepository.findAll();
    }

    public RoomTimeCountResponse findByRoomId(String id, LocalDate date) {
        String name = roomRepository.findRoomNameById(id);
        List<TimeCount> counts = roomRepository.findTimeCount(id, date);
        int[] countArray = new int[10];

        for (TimeCount count : counts) {
            countArray[count.getTime() - 9] = count.getCount();
        }
        RoomTimeCountResponse response = RoomTimeCountResponse.builder()
                .name(name)
                .count(countArray)
                .build();

        return response;
    }
}

