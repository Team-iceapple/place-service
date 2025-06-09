package iceapple.placeservice.repository.jdbc;

import iceapple.placeservice.entity.Reservation;
import iceapple.placeservice.dto.request.ReservationRequest;
import iceapple.placeservice.dto.response.ReservationRoomResponse;
import iceapple.placeservice.entity.Room;
import iceapple.placeservice.repository.ReservationRepository;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
<<<<<<< HEAD
import javax.sql.DataSource;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcReservationRepository implements ReservationRepository {

    private JdbcTemplate jdbcTemplate;
=======
import java.util.UUID;
import javax.sql.DataSource;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;


public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
>>>>>>> 525b061 (resolve #2 feat: jdbc repository 구현)

    public JdbcReservationRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

<<<<<<< HEAD
=======

>>>>>>> 525b061 (resolve #2 feat: jdbc repository 구현)
    @Override
    public ResponseEntity<Void> createReservation(final ReservationRequest request) {
        String sql = "INSERT INTO reservation (id, student_number, phone_number, password, room_id, date, times) VALUES (?, ?, ?, ?, ?, ?, ?)";

        UUID id = UUID.randomUUID();
        jdbcTemplate.update(connection -> {
            PreparedStatement psmt = connection.prepareStatement(sql, new String[]{"id"});
            psmt.setString(1, id.toString());
            psmt.setString(2, request.getStudentNumber());
            psmt.setString(3, request.getPhoneNumber());
            psmt.setString(4, request.getPassword());
            psmt.setString(5, request.getRoomId());
            psmt.setTimestamp(6, Timestamp.valueOf(request.getDate()));
            psmt.setObject(7, request.getTimes().toArray(new Integer[0]));
            return psmt;
        });

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    @Override
//    public List<Reservation> searchReservationInfo(final String studentNumber, final String password) {
//        String sql = "SELECT id, times, date, room_id FROM reservation WHERE student_number = ? AND password = ?";
////
////        return jdbcTemplate.query(sql, new Object[]{studentNumber, password},
////                (rs, rowNum) -> new ReservationRoomResponse(rs.getString("id"), rs.getArray("times"), rs.getTimestamp("date").toLocalDateTime().toLocalDate()));
//        List<Reservation> reservations = new ArrayList<>();
//        return reservations;
//    };

    @Override
    public List<ReservationRoomResponse> searchReservationInfo(final String studentNumber, final String password) {
        String sql = "SELECT id, times, date, room_id FROM reservation WHERE student_number = ? AND password = ?";

        return jdbcTemplate.query(sql, new Object[]{studentNumber, password}, (rs, rowNum) -> {
            String id = rs.getString("id");

            Array sqlArray = rs.getArray("times");
            Integer[] timesArray = (Integer[]) sqlArray.getArray();
            List<Integer> times = Arrays.asList(timesArray);

            LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
            String roomId = rs.getString("room_id");
            Room room = new Room();
            room.setId(roomId);

            return new ReservationRoomResponse(id, times, date, room);
        });
    }

    @Override
    public int cancelReservations(final List<String> ids){
        String sql = "DELETE FROM reservation WHERE id = ?";
        return ids.stream()
                .mapToInt(id -> jdbcTemplate.update(sql, id))
                .sum();
    }

    @Override
    public String findNameRoom(final String roomId) {
        String sql = "SELECT name FROM room WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{roomId}, String.class);
    }
}
