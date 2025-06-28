package iceapple.placeservice.repository.jdbc;

import iceapple.placeservice.entity.Reservation;
import iceapple.placeservice.dto.request.ReservationRequest;
import iceapple.placeservice.dto.response.ReservationRoomResponse;
import iceapple.placeservice.entity.Room;
import iceapple.placeservice.repository.ReservationRepository;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.UUID;
import org.springframework.http.HttpStatus;


public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

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
/// / /        return jdbcTemplate.query(sql, new Object[]{studentNumber, password}, /                (rs, rowNum)
/// -> new ReservationRoomResponse(rs.getString("id"), rs.getArray("times"),
/// rs.getTimestamp("date").toLocalDateTime().toLocalDate()));
//        List<Reservation> reservations = new ArrayList<>();
//        return reservations;
//    };

    @Override
    public List<Reservation> searchReservationInfo(final String studentNumber, final String password) {
        String sql = "SELECT * FROM reservation WHERE student_number = ? AND password = ?";

        return jdbcTemplate.query(sql, new Object[]{studentNumber, password}, (rs, rowNum) -> {
            String id = rs.getString("id");
            String phoneNumber = rs.getString("phone_number");
            Array sqlArray = rs.getArray("times");
            Integer[] timesArray = (Integer[]) sqlArray.getArray();
            List<Integer> times = Arrays.asList(timesArray);

            LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
            String roomId = rs.getString("room_id");

            return new Reservation(id, studentNumber, phoneNumber, password, roomId, date, times);
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

    @Override
    public List<Reservation> findByStudentNumber(final String studentNumber) {
        String sql = "SELECT * FROM reservation WHERE student_number = ?";

        return jdbcTemplate.query(sql, new Object[]{studentNumber}, (rs, rowNum) -> {
            String id = rs.getString("id");
            String phoneNumber = rs.getString("phone_number");
            String encodedPassword = rs.getString("password");
            String roomId = rs.getString("room_id");
            LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();

            Array sqlArray = rs.getArray("times");
            Integer[] timesArray = (Integer[]) sqlArray.getArray();
            List<Integer> times = Arrays.asList(timesArray);

            return new Reservation(id, studentNumber, phoneNumber, encodedPassword, roomId, date, times);
        });
    }

}
