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
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


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

        if (reservations.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 학번으로 등록된 예약이 없습니다.");
        }

        List<ReservationPlaceResponse> result = new ArrayList<>();

        boolean hasAnyMatch = reservations.stream()
                .anyMatch(res -> passwordEncoder.matches(inputPassword, res.getPassword()));

        if (!hasAnyMatch) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "비밀번호가 일치하지 않습니다."
            );
        }

        return reservations.stream()
                .filter(res -> passwordEncoder.matches(inputPassword, res.getPassword()))
                .map(res -> {
                    String placeId = res.getPlaceId();
                    String placeName = reservationRepository.findNamePlace(placeId);
                    Place place = new Place(placeId, placeName);
                    return new ReservationPlaceResponse(
                            res.getId(), res.getTimes(), res.getDate(), place, res.getResCount()
                    );
                })
                .collect(Collectors.toList());
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
                request.getTimes(),
                request.getResCount()
        );

        placeRepository.increaseTimeCount(
                request.getPlaceId(),
                LocalDate.from(request.getDate()),
                request.getTimes(),
                request.getResCount()
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
            placeRepository.decreaseTimeCount(s.placeId(), s.date(), s.times(), s.resCount());
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
                request.getTimes(),
                request.getResCount()
        );

        placeRepository.increaseTimeCount(
                request.getPlaceId(),
                request.getDate(),
                request.getTimes(),
                request.getResCount()
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
                    res.getId(), res.getTimes(), res.getStudentNumber(), res.getPhoneNumber(), placeInfo,
                    res.getResCount()
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
                reservation.getTimes(),
                reservation.getResCount()
        );

        placeRepository.increaseTimeCount(
                request.getPlaceId(),
                request.getDate(),
                request.getTimes(),
                request.getResCount()
        );

        AdminReservationRequest updateReservation = new AdminReservationRequest(
                request.getDate(),
                request.getPlaceId(),
                request.getTimes(),
                request.getUserName(),
                request.getResCount()

        );

        reservationRepository.updateReservationInfo(reservationId, updateReservation);
    }

}
