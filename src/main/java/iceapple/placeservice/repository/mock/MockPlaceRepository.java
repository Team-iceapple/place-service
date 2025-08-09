package iceapple.placeservice.repository.mock;

import iceapple.placeservice.entity.Place;
import iceapple.placeservice.repository.PlaceRepository;
import iceapple.placeservice.util.TimeCount;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockPlaceRepository implements PlaceRepository {
    Map<String, Place> store = new HashMap<>();

    public MockPlaceRepository() {
        // 테스트용 더미 데이터
        Place place1 = new Place();
        place1.setId("r_38485");
        place1.setName("N5504");
        place1.setDescription("4팀 예약 가능.");

        Place place2 = new Place();
        place2.setId("2_4494");
        place2.setName("N5511");
        place2.setDescription("티비가 있는 방입니다.");

        store.put(place1.getId(), place1);
        store.put(place2.getId(), place2);
    }

    @Override
    public List<Place> findAll() {
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
    public String findPlaceNameById(final String id) {
        return "";
    }

    @Override
    public void increaseTimeCount(final String placeId, final LocalDate date, final List<Integer> times) {

    }

    @Override
    public void decreaseTimeCount(final String placeId, final LocalDate date, final List<Integer> times) {

    }
}
