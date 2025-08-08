package iceapple.placeservice.repository;

import iceapple.placeservice.dto.request.ReservationRequest;
import iceapple.placeservice.entity.Reservation;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface ReservationRepository{

    List<Reservation> searchReservationInfo(String studentNumber, String password);

    ResponseEntity<Void> createReservation(ReservationRequest request);

    int cancelReservations(List<String> ids);

    String findNamePlace(String placeId);

    List<Reservation> findByStudentNumber(String studentNumber);

}
