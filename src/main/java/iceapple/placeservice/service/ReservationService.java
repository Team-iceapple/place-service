package iceapple.placeservice.service;

import iceapple.placeservice.domain.Reservation;
import iceapple.placeservice.dto.request.ReservationRequest;
import iceapple.placeservice.repository.ReservationRepository;

public class ReservationService {
    ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation searchReservationInfo(final String studentNumber, final String password) {
        return new Reservation();
    }

    public Reservation createReservation(final ReservationRequest request) {
        return new Reservation();
    }


}
