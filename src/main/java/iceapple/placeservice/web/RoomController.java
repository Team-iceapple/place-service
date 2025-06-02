package iceapple.placeservice.web;

import iceapple.placeservice.entity.Room;
import iceapple.placeservice.dto.response.RoomTimeCountResponse;
import iceapple.placeservice.service.RoomService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<Room>> getRooms() {
        return ResponseEntity.ok(roomService.findRooms());
    }

    @GetMapping("/{room_id}")
    public ResponseEntity<RoomTimeCountResponse> getRoom(@PathVariable("room_id") String roomId,
                                                         @RequestParam LocalDate date) {

        return ResponseEntity.ok(roomService.findByRoomId(roomId, date));
    }


}
