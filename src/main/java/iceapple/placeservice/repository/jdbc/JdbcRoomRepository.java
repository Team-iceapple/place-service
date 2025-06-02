package iceapple.placeservice.repository.jdbc;

import iceapple.placeservice.domain.Room;
import iceapple.placeservice.dto.response.RoomTimeCountResponse;
import iceapple.placeservice.repository.RoomRepository;
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
    public RoomTimeCountResponse findById(final String id, final LocalDate date) {
        String sql = "SELECT * FROM time_count WHERE room_id = ? AND date = ?";
        RoomTimeCountResponse timeCount;

        return null;
    }

    private RowMapper<Room> rowMapper() {
        return (rs, rowNum) -> new Room(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("description")
        );
    }
}
