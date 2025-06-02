package iceapple.placeservice.config;

import iceapple.placeservice.repository.ReservationRepository;
import iceapple.placeservice.repository.jdbc.JdbcReservationRepository;
import iceapple.placeservice.repository.jdbc.JdbcRoomRepository;
import iceapple.placeservice.repository.mock.MockReservationRepository;
import iceapple.placeservice.repository.RoomRepository;
import iceapple.placeservice.service.ReservationService;
import iceapple.placeservice.service.RoomService;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlaceConfig {
    private final DataSource dataSource;

    public PlaceConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public RoomRepository roomRepository() {
        return new JdbcRoomRepository(dataSource);
    }

    @Bean
    public RoomService roomService() {
        return new RoomService(roomRepository());
    }

    @Bean
    public ReservationRepository reservationRepository() {
        return new JdbcReservationRepository(dataSource);
    }

    @Bean
    public ReservationService reservationService() {
        return new ReservationService(reservationRepository());
    }

}
