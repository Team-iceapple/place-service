package iceapple.placeservice.repository;

import iceapple.placeservice.entity.Reservation;
import iceapple.placeservice.dto.request.ReservationRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface ReservationRepository{

    List<Reservation> searchReservationInfo(String studentNumber, String password);

    Reservation createReservation(ReservationRequest request);

    ResponseEntity<String> cancelReservations(List<String> ids);

}
