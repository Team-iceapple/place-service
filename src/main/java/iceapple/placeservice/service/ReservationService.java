package iceapple.placeservice.service;

import iceapple.placeservice.entity.Reservation;
import iceapple.placeservice.dto.request.ReservationRequest;
import iceapple.placeservice.repository.ReservationRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;

public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> searchReservationInfo(final String studentNumber, final String password) {
        return reservationRepository.searchReservationInfo(studentNumber, password);
    }

    public Reservation createReservation(final ReservationRequest request) {
        return reservationRepository.createReservation(request);
    }


    public ResponseEntity<String> cancelReservations(final List<String> ids) {
        reservationRepository.cancelReservations(ids);
        return ResponseEntity.ok("예약 취소 성공");
    }
}
