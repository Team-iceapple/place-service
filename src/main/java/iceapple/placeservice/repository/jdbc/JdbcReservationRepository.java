package iceapple.placeservice.repository.jdbc;

import iceapple.placeservice.entity.Reservation;
import iceapple.placeservice.dto.request.ReservationRequest;
import iceapple.placeservice.repository.ReservationRepository;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcReservationRepository implements ReservationRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

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
