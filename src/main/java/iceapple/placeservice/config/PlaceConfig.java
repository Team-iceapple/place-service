package iceapple.placeservice.config;

import iceapple.placeservice.repository.mock.MockRoomRepository;
import iceapple.placeservice.repository.RoomRepository;
import iceapple.placeservice.service.RoomService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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


}
