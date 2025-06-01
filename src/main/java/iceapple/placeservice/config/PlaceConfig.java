package iceapple.placeservice.config;

import iceapple.placeservice.repository.ReservationRepository;
import iceapple.placeservice.repository.mock.MockReservationRepository;
import iceapple.placeservice.repository.mock.MockRoomRepository;
import iceapple.placeservice.repository.RoomRepository;
import iceapple.placeservice.service.ReservationService;
import iceapple.placeservice.service.RoomService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PlaceConfig {

    @Bean
    public RoomRepository roomRepository() {
        return new MockRoomRepository();
    }
    @Bean
    public RoomService roomService() {
        return new RoomService(roomRepository());
    }

    @Bean
    public ReservationRepository reservationRepository() {return new MockReservationRepository();}
    @Bean
    public ReservationService reservationService() { return new ReservationService(reservationRepository());}

}
