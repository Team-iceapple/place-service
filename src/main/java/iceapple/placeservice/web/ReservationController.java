package iceapple.placeservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import iceapple.placeservice.domain.Reservation;
import iceapple.placeservice.dto.request.ReservationInfoRequest;
import iceapple.placeservice.dto.request.ReservationRequest;
import iceapple.placeservice.service.ReservationService;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    ReservationService reservationService;

    @PostMapping("/reservation-info")
    public ResponseEntity<?> reservationInfo(@RequestBody ReservationInfoRequest request) {
        if(request.getStudentNumber() == null || request.getStudentNumber().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("학번은 필수입니다.");
        }
        if(request.getPassword() == null || request.getPassword().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호는 필수입니다.");
        }
        Reservation response = reservationService.searchReservationInfo(request.getStudentNumber(), request.getPassword());
        return ResponseEntity.ok(response);
    }


    @PostMapping("/reservations")
    public ResponseEntity<?> reserveRoom(@RequestBody final ReservationRequest request) {
        //todo - 검증 과정 추가로 필요함

        try {
            reservationService.createReservation(request);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    @DeleteMapping("/reservations")
    public ResponseEntity<?> cancelReservations(@RequestBody final Map<String, List<String>> request) {
        try {
            List<String> ids = request.get("reservation_id");
            reservationService.cancelReservations(ids);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
