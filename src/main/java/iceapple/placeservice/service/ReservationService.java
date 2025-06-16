package iceapple.placeservice.service;

import iceapple.placeservice.dto.RoomDTO;
import iceapple.placeservice.entity.Reservation;
import iceapple.placeservice.dto.response.ReservationRoomResponse;
import iceapple.placeservice.dto.request.ReservationRequest;
import iceapple.placeservice.repository.ReservationRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;

public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationRoomResponse> searchReservationInfo(final String studentNumber, final String password) {
        List<Reservation> reservations = reservationRepository.searchReservationInfo(studentNumber, password);
        List<ReservationRoomResponse> result = new ArrayList<>();

        for (Reservation res : reservations) {
            String roomId = res.getRoomId();
            String roomName = reservationRepository.findNameRoom(roomId);

            RoomDTO room = new RoomDTO(roomId, roomName);

            ReservationRoomResponse response = new ReservationRoomResponse(res.getId(), res.getTimes(), res.getDate(), room);
            result.add(response);
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
