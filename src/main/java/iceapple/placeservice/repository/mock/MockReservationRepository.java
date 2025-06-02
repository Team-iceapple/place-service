package iceapple.placeservice.repository.mock;

import iceapple.placeservice.entity.Reservation;
import iceapple.placeservice.dto.request.ReservationRequest;
import iceapple.placeservice.repository.ReservationRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MockReservationRepository implements ReservationRepository {
    Map<String, Reservation> store = new HashMap<>();

    public MockReservationRepository() {
        // 테스트용 더미 데이터
        Reservation reservation1 = new Reservation();
        reservation1.setId("resv123456");
        reservation1.setStudentNumber("20221032");
        reservation1.setPhoneNumber("010-2222-2929");
        reservation1.setPassword("1234");
        reservation1.setRoomId("r_38485");
        reservation1.setDate(LocalDateTime.now());
        reservation1.setTimes(List.of(9, 10, 11));

        Reservation reservation2 = new Reservation();
        reservation2.setId("resv78910");
        reservation2.setStudentNumber("20221038");
        reservation2.setPhoneNumber("010-1111-4545");
        reservation2.setPassword("0000");
        reservation2.setRoomId("2_4494");
        reservation2.setDate(LocalDateTime.now());
        reservation2.setTimes(List.of(15, 16, 17));

        store.put(reservation1.getId(), reservation1);
        store.put(reservation2.getId(), reservation2);
        System.out.println("store keys: " + store.keySet());
    }
    @Override
    public List<Reservation> searchReservationInfo(final String studentNumber, final String password) {
        return store.values().stream()
                .filter(r -> r.getStudentNumber().equals(studentNumber))
                .filter(r -> r.getPassword().equals(password))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Reservation createReservation(final ReservationRequest request) {
        Reservation reservation = new Reservation();
        reservation.setStudentNumber(request.getStudentNumber());
        reservation.setPhoneNumber(request.getPhoneNumber());
        reservation.setPassword(request.getPassword());
        reservation.setRoomId(request.getRoomId());
        reservation.setDate(request.getDate());
        reservation.setTimes(request.getTimes());
        store.put(reservation.getId(), reservation);
        return reservation;
    }

    @Override
    public ResponseEntity<String> cancelReservations(final List<String> ids) {
        for (String id : ids) {
            System.out.println(id);
            if (!store.containsKey(id)) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("해당하는 예약 ID : " + id + " 를 찾을 수 없습니다.");
            }
                store.remove(id);
        }

        System.out.println(store);
        return ResponseEntity.ok("예약 취소 성공");
    }
}
