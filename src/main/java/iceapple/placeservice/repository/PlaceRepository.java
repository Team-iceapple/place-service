package iceapple.placeservice.repository;

import iceapple.placeservice.entity.Place;
import iceapple.placeservice.util.TimeCount;
import java.time.LocalDate;
import java.util.List;

public interface PlaceRepository {
    List<Place> findAll();
    List<TimeCount> findTimeCount(String id, LocalDate date);
    String findPlaceNameById(String id);
}
