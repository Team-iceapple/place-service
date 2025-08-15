package iceapple.placeservice.service;

import iceapple.placeservice.dto.response.PlaceTimeCountResponse;
import iceapple.placeservice.entity.Place;
import iceapple.placeservice.repository.PlaceRepository;
import iceapple.placeservice.util.TimeCount;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class PlaceService {

    PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public List<Place> findPlaces() {
        return placeRepository.findAll();
    }

    public PlaceTimeCountResponse findByPlaceId(String id, LocalDate date) {
        String name = placeRepository.findPlaceNameById(id);
        List<TimeCount> counts = placeRepository.findTimeCount(id, date);
        int[] countArray = new int[10];

        for (TimeCount count : counts) {
            int t = count.getTime();
            int idx = t-9;
            if (idx < 0 || idx > 10) {
                continue;
            }
            countArray[idx] = count.getCount();
        }
        PlaceTimeCountResponse response = PlaceTimeCountResponse.builder()
                .name(name)
                .count(countArray)
                .build();

        return response;
    }

    // 관리자용 등록
    public Place createPlace(String name, String description, Integer placeCount) {
        String id = UUID.randomUUID().toString();
        Place place = new Place(id, name, description);
        placeRepository.insert(place, placeCount);
        return place;
    }

    // 관리자용 삭제
    public void deletePlace(String placeId) {
        if (!placeRepository.existsById(placeId)) {
            throw new IllegalArgumentException("존재하지 않는 회의실입니다: " + placeId);
        }
        placeRepository.deleteById(placeId);
    }

    public Place adminGetPlace(String placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회의실입니다: " + placeId));
    }
}

