package iceapple.placeservice.service;

import iceapple.placeservice.entity.Reservation;
import iceapple.placeservice.dto.response.ReservationRoomResponse;
import iceapple.placeservice.entity.Reservation;
import iceapple.placeservice.entity.Room;
import iceapple.placeservice.dto.request.ReservationRequest;
import iceapple.placeservice.repository.ReservationRepository;
import iceapple.placeservice.repository.jdbc.JdbcReservationRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;

public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> searchReservationInfo(final String studentNumber, final String password) {
        List<Reservation> reservations = reservationRepository.searchReservationInfo(studentNumber, password);
        List<Reservation> result = new ArrayList<>();
        System.out.println(reservations);
        result.addAll(reservations);
        for (Reservation res : reservations) {
            String roomName = reservationRepository.findNameRoom(res.getRoomId());

            Room room = new Room();
            room.setId(res.getRoomId());
            room.setName(roomName);

            result.add(res);
            System.out.println(result);
        }
        return result;
    }

    public ResponseEntity<Void> createReservation(final ReservationRequest request) {
        return reservationRepository.createReservation(request);
    }


    public ResponseEntity<Void> cancelReservations(final List<String> ids) {
        int rows = reservationRepository.cancelReservations(ids);
        if (ids.size() == rows) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
