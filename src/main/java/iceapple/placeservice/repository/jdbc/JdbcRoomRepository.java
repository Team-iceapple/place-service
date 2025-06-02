package iceapple.placeservice.repository.jdbc;

import iceapple.placeservice.domain.Room;
import iceapple.placeservice.repository.RoomRepository;
import iceapple.placeservice.util.TimeCount;
import java.time.LocalDate;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JdbcRoomRepository implements RoomRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcRoomRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Room> findAll() {
        String sql = "SELECT * FROM room";
        List<Room> rooms = jdbcTemplate.query(sql, rowMapper());
        return rooms;
    }

    @Override
    public List<TimeCount> findTimeCount(String roomId, LocalDate date) {
        String sql = "SELECT time, count FROM time_count WHERE room_id = ? AND date = ?";
        return jdbcTemplate.query(sql, new Object[]{roomId, date},
                (rs, rowNum) -> new TimeCount(rs.getInt("time"), rs.getInt("count")));
    }


    public String findRoomNameById(String roomId) {
        String sql = "SELECT name FROM room WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{roomId}, String.class);
    }


    private RowMapper<Room> rowMapper() {
        return (rs, rowNum) -> new Room(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("description")
        );
    }
}
