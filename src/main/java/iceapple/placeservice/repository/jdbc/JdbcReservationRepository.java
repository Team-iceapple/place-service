package iceapple.placeservice.repository.jdbc;

import iceapple.placeservice.domain.Reservation;
import iceapple.placeservice.dto.request.ReservationRequest;
import iceapple.placeservice.repository.ReservationRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;

public class JdbcReservationRepository implements ReservationRepository {

    @Override
    public List<Reservation> searchReservationInfo(final String studentNumber, final String password) {
        return List.of();
    }

    @Override
    public Reservation createReservation(final ReservationRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<String> cancelReservations(final List<String> ids) {
        return null;
    }
}
