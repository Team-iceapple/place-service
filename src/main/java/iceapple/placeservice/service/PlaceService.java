package iceapple.placeservice.service;

import iceapple.placeservice.dto.response.PlaceTimeCountResponse;
import iceapple.placeservice.entity.Place;
import iceapple.placeservice.repository.PlaceRepository;
import iceapple.placeservice.util.TimeCount;
import java.time.LocalDate;
import java.util.List;

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
            countArray[count.getTime() - 9] = count.getCount();
        }
        PlaceTimeCountResponse response = PlaceTimeCountResponse.builder()
                .name(name)
                .count(countArray)
                .build();

        return response;
    }
}

