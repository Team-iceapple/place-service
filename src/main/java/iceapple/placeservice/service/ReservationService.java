package iceapple.placeservice.service;

import iceapple.placeservice.entity.Reservation;
import iceapple.placeservice.dto.response.ReservationRoomResponse;
import iceapple.placeservice.dto.request.ReservationRequest;
import iceapple.placeservice.entity.Room;
import iceapple.placeservice.repository.ReservationRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;


public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final PasswordEncoder passwordEncoder; // 추가

    public ReservationService(ReservationRepository reservationRepository, PasswordEncoder passwordEncoder) {
        this.reservationRepository = reservationRepository;
        this.passwordEncoder = passwordEncoder; // 주입
    }

    public List<ReservationRoomResponse> searchReservationInfo(final String studentNumber, final String inputPassword) {
        // 1. 비밀번호 없이 전체 예약 가져오기
        List<Reservation> reservations = reservationRepository.findByStudentNumber(studentNumber);
        List<ReservationRoomResponse> result = new ArrayList<>();

        for (Reservation res : reservations) {
            // 2. 암호화된 비밀번호와 평문 비교
            if (!passwordEncoder.matches(inputPassword, res.getPassword())) {
                continue; // 비밀번호 불일치 → 스킵
            }

            // 3. 비밀번호 일치 → 응답 조립
            String roomId = res.getRoomId();
            String roomName = reservationRepository.findNameRoom(roomId);
            Room room = new Room(roomId, roomName);

            ReservationRoomResponse response = new ReservationRoomResponse(
                    res.getId(), res.getTimes(), res.getDate(), room
            );
            result.add(response);
        }

        return result;
    }

    public ResponseEntity<Void> createReservation(final ReservationRequest request) {
        // 비밀번호 암호화 추가
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);

        return reservationRepository.createReservation(request);
    }

    public ResponseEntity<Void> cancelReservations(final List<String> ids) {
        int rows = reservationRepository.cancelReservations(ids);
        if (ids.size() == rows) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
