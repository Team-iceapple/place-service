package iceapple.placeservice.repository;

<<<<<<< HEAD
=======
import iceapple.placeservice.dto.response.ReservationRoomResponse;
>>>>>>> 525b061 (resolve #2 feat: jdbc repository 구현)
import iceapple.placeservice.entity.Reservation;
import iceapple.placeservice.dto.request.ReservationRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface ReservationRepository{

    List<ReservationRoomResponse> searchReservationInfo(String studentNumber, String password);

    ResponseEntity<Void> createReservation(ReservationRequest request);

    int cancelReservations(List<String> ids);

    String findNameRoom(String roomId);
}
