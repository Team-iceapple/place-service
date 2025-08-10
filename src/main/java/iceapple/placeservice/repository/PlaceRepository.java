package iceapple.placeservice.repository;

import iceapple.placeservice.entity.Place;
import iceapple.placeservice.util.TimeCount;
import java.time.LocalDate;
import java.util.List;

public interface PlaceRepository {
    List<Place> findAll();
    List<TimeCount> findTimeCount(String id, LocalDate date);
    String findPlaceNameById(String id);
    Integer findPlaceCountById(String id);
    void increaseTimeCount(String placeId, LocalDate date, List<Integer> times);
    void decreaseTimeCount(String placeId, LocalDate date, List<Integer> times); // (취소 시)
}
