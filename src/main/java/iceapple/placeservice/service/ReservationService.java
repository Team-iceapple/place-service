package iceapple.placeservice.service;

import iceapple.placeservice.dto.ReservationSlot;
import iceapple.placeservice.entity.Place;
import iceapple.placeservice.entity.Reservation;
import iceapple.placeservice.dto.response.ReservationPlaceResponse;
import iceapple.placeservice.dto.request.ReservationRequest;
import iceapple.placeservice.repository.PlaceRepository;
import iceapple.placeservice.repository.ReservationRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final PlaceRepository placeRepository;
    private final PasswordEncoder passwordEncoder; // 추가

    public ReservationService(ReservationRepository reservationRepository, PlaceRepository placeRepository, PasswordEncoder passwordEncoder) {
        this.reservationRepository = reservationRepository;
        this.placeRepository = placeRepository;
        this.passwordEncoder = passwordEncoder; // 주입
    }

    public List<ReservationPlaceResponse> searchReservationInfo(final String studentNumber, final String inputPassword) {
        // 1. 비밀번호 없이 전체 예약 가져오기
        List<Reservation> reservations = reservationRepository.findByStudentNumber(studentNumber);
        List<ReservationPlaceResponse> result = new ArrayList<>();

        for (Reservation res : reservations) {
            // 2. 암호화된 비밀번호와 평문 비교
            if (!passwordEncoder.matches(inputPassword, res.getPassword())) {
                continue; // 비밀번호 불일치 → 스킵
            }

            // 3. 비밀번호 일치 → 응답 조립
            String placeId = res.getPlaceId();
            String placeName = reservationRepository.findNamePlace(placeId);
            Place place = new Place(placeId, placeName);

            ReservationPlaceResponse response = new ReservationPlaceResponse(
                    res.getId(), res.getTimes(), res.getDate(), place
            );
            result.add(response);
        }

        return result;
    }

    @Transactional
    public ResponseEntity<Void> createReservation(final ReservationRequest request) {
        // 비밀번호 암호화 추가
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        ReservationRequest toSave = new ReservationRequest(
                request.getStudentNumber(),
                request.getPhoneNumber(),
                encodedPassword,
                request.getPlaceId(),
                request.getDate(),
                request.getTimes()
        );

        ResponseEntity<Void> response = reservationRepository.createReservation(toSave);

        // time count 증가
        placeRepository.increaseTimeCount(
                request.getPlaceId(),
                request.getDate().toLocalDate(),
                request.getTimes()
        );

        return response;
    }

    @Transactional
    public ResponseEntity<Void> cancelReservations(final List<String> ids) {
        List<ReservationSlot> slots = reservationRepository.deleteAndReturnSlots(ids);

        // slot 개수 = ids.size 비교
        if (slots.size() != ids.size()) {
            return ResponseEntity.notFound().build();
        }

        for (ReservationSlot s : slots) {
            placeRepository.decreaseTimeCount(s.placeId(), s.date(), s.times());
        }
        return ResponseEntity.notFound().build();
    }
}
