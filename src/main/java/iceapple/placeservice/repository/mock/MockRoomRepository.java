package iceapple.placeservice.repository.mock;

import iceapple.placeservice.domain.Room;
import iceapple.placeservice.dto.response.RoomTimeCountResponse;
import iceapple.placeservice.repository.RoomRepository;
import iceapple.placeservice.util.TimeCount;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockRoomRepository implements RoomRepository {
    Map<String, Room> store = new HashMap<>();

    public MockRoomRepository() {
        // 테스트용 더미 데이터
        Room room1 = new Room();
        room1.setId("r_38485");
        room1.setName("N5504");
        room1.setDescription("4팀 예약 가능.");

        Room room2 = new Room();
        room2.setId("2_4494");
        room2.setName("N5511");
        room2.setDescription("티비가 있는 방입니다.");

        store.put(room1.getId(), room1);
        store.put(room2.getId(), room2);
    }

    @Override
    public List<Room> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<TimeCount> findTimeCount(final String id, final LocalDate date) {
        return List.of();
    }

//    @Override
//    public RoomTimeCountResponse findTimeCount(final String id, final LocalDate date) {
//        Room room = store.get(id);
//
//        if (room == null) {
//            return null;
//        }
//        // 더미 count 데이터 생성 (테스트 목적)
//        List<Integer> dummyCount = Arrays.asList(1, 0, 2, 1, 3, 0, 0, 0, 0, 0);
//
//        RoomTimeCountResponse response = RoomTimeCountResponse.builder()
//                .name(room.getName())
//                .count(dummyCount)
//                .build();
//
//        return response;
//
//    }

    @Override
    public String findRoomNameById(final String id) {
        return "";
    }
}
