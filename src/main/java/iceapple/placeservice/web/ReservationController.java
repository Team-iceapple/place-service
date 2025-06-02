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
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/reservation-info")
    public ResponseEntity<?> reservationInfo(@RequestBody final ReservationInfoRequest request) {
        System.out.println(request);
        if(request.getStudentNumber() == null || request.getStudentNumber().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("학번은 필수입니다.");
        }
        if(request.getPassword() == null || request.getPassword().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호는 필수입니다.");
        }
        List<Reservation> response = reservationService.searchReservationInfo(request.getStudentNumber(), request.getPassword());
        return ResponseEntity.ok(response);
    }


    @PostMapping()
    public Reservation createReservation(@RequestBody final ReservationRequest request) {
        //todo - 입력 정보 검증 과정 추가로 필요함
        try {
            return reservationService.createReservation(request);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @DeleteMapping()
    public ResponseEntity<String> cancelReservations(@RequestBody final Map<String, List<String>> request) {
        System.out.println(request);
        try {
            List<String> ids = request.get("reservationId");
            return reservationService.cancelReservations(ids);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
