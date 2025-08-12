package iceapple.placeservice.service;

import iceapple.placeservice.dto.ReservationSlot;
import iceapple.placeservice.dto.request.AdminReservationRequest;
import iceapple.placeservice.dto.response.AdminReservationResponse;
import iceapple.placeservice.entity.Place;
import iceapple.placeservice.entity.Reservation;
import iceapple.placeservice.dto.response.ReservationPlaceResponse;
import iceapple.placeservice.dto.request.ReservationRequest;
import iceapple.placeservice.repository.PlaceRepository;
import iceapple.placeservice.repository.ReservationRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final PlaceRepository placeRepository;
    private final PasswordEncoder passwordEncoder;

    public ReservationService(ReservationRepository reservationRepository, PlaceRepository placeRepository,
                              PasswordEncoder passwordEncoder) {
        this.reservationRepository = reservationRepository;
        this.placeRepository = placeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<ReservationPlaceResponse> searchReservationInfo(final String studentNumber,
                                                                final String inputPassword) {
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
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        System.out.println(request.getDate());
        ReservationRequest toSave = new ReservationRequest(
                request.getStudentNumber(),
                request.getPhoneNumber(),
                encodedPassword,
                request.getPlaceId(),
                request.getDate(),
                request.getTimes()
        );

        placeRepository.increaseTimeCount(
                request.getPlaceId(),
                LocalDate.from(request.getDate()),
                request.getTimes()
        );

        return reservationRepository.createReservation(toSave);
    }


    @Transactional
    public ResponseEntity<Void> cancelReservations(final List<String> ids) {
        List<ReservationSlot> slots = reservationRepository.deleteAndReturnSlots(ids);

        if (slots.size() != ids.size()) {
            return ResponseEntity.notFound().build();
        }

        for (ReservationSlot s : slots) {
            placeRepository.decreaseTimeCount(s.placeId(), s.date(), s.times());
        }
        return ResponseEntity.noContent().build();
    }


    @Transactional
    public ResponseEntity<Void> adminCreateReservation(final AdminReservationRequest request) {
        String encodedPassword = passwordEncoder.encode("admin");

        ReservationRequest toSave = new ReservationRequest(
                "0000000",
                "admin",
                encodedPassword,
                request.getPlaceId(),
                request.getDate().atStartOfDay(),
                request.getTimes()
        );

        placeRepository.increaseTimeCount(
                request.getPlaceId(),
                request.getDate(),
                request.getTimes()
        );

        return reservationRepository.createReservation(toSave);
    }


    public List<AdminReservationResponse> getReservationsByDateForAdmin(final LocalDate date) {
        List<Reservation> reservations = reservationRepository.findByReservationDate(date);
        List<AdminReservationResponse> result = new ArrayList<>();

        for (Reservation res : reservations) {
            String placeId = res.getPlaceId();
            String placeName = reservationRepository.findNamePlace(placeId);
            int count = placeRepository.findPlaceCountById(placeId);

            AdminReservationResponse.PlaceInfo placeInfo = new AdminReservationResponse.PlaceInfo(
                    placeId, placeName, count
            );

            AdminReservationResponse response = new AdminReservationResponse(
                    res.getId(), res.getTimes(), res.getStudentNumber(), res.getPhoneNumber(), placeInfo
            );

            result.add(response);
        }

        return result;
    }


    @Transactional
    public void updateAdminReservation(String reservationId, AdminReservationRequest request) {
        Reservation reservation = reservationRepository.findByReservationId(reservationId);

        if (reservation == null) {
            throw new RuntimeException("예약을 찾을 수 없습니다");
        }

        placeRepository.decreaseTimeCount(
                reservation.getPlaceId(),
                reservation.getDate().toLocalDate(),
                reservation.getTimes()
        );

        placeRepository.increaseTimeCount(
                request.getPlaceId(),
                request.getDate(),
                request.getTimes()
        );

        AdminReservationRequest updateReservation = new AdminReservationRequest(
                request.getDate(),
                request.getPlaceId(),
                request.getTimes(),
                request.getUserName()
        );


        reservationRepository.updateReservationInfo(reservationId, updateReservation);
    }

}
