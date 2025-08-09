package iceapple.placeservice.repository.jdbc;

import iceapple.placeservice.entity.Place;
import iceapple.placeservice.repository.PlaceRepository;
import iceapple.placeservice.util.TimeCount;
import java.time.LocalDate;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JdbcPlaceRepository implements PlaceRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPlaceRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Place> findAll() {
        String sql = "SELECT * FROM place";
        List<Place> places = jdbcTemplate.query(sql, rowMapper());
        return places;
    }

    @Override
    public List<TimeCount> findTimeCount(String placeId, LocalDate date) {
        String sql = "SELECT time, count FROM time_count WHERE place_id = ? AND date = ?";
        return jdbcTemplate.query(sql, new Object[]{placeId, date},
                (rs, rowNum) -> new TimeCount(rs.getInt("time"), rs.getInt("count")));
    }


    public String findPlaceNameById(String placeId) {
        String sql = "SELECT name FROM place WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{placeId}, String.class);
    }

    @Override
    public void increaseTimeCount(final String placeId, final LocalDate date, final List<Integer> times) {
        final String sql =
                "INSERT INTO time_count (place_id, date, time, count) " +
                        "VALUES (?, ?, ?, 1) " +
                        "ON CONFLICT (place_id, date, time) DO UPDATE " +
                        "SET count = time_count.count + 1";

        jdbcTemplate.batchUpdate(sql, times, times.size(), (ps, t) -> {
            ps.setString(1, placeId);
            ps.setDate(2, java.sql.Date.valueOf(date));
            ps.setInt(3, t);
        });
    }

    @Override
    public void decreaseTimeCount(final String placeId, final LocalDate date, final List<Integer> times) {
        final String sql =
                "UPDATE time_count SET count = GREATEST(count - 1, 0) " +
                        "WHERE place_id = ? AND date = ? AND time = ?";

        jdbcTemplate.batchUpdate(sql, times, times.size(), (ps, t) -> {
            ps.setString(1, placeId);
            ps.setDate(2, java.sql.Date.valueOf(date));
            ps.setInt(3, t);
        });
    }


    private RowMapper<Place> rowMapper() {
        return (rs, rowNum) -> new Place(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("description")
        );
    }
}
