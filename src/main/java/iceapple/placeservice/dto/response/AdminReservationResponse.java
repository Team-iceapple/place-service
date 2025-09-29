package iceapple.placeservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminReservationResponse {
    private String id;
    private List<Integer> times;

    @JsonProperty("student_number")
    private String studentNumber;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private PlaceInfo place;

    @JsonProperty("res_count")
    private final int resCount;

    public AdminReservationResponse(final String id, final List<Integer> times, final String studentNumber,
                                    final String phoneNumber,
                                    final PlaceInfo place, final int resCount) {
        this.id = id;
        this.times = times;
        this.studentNumber = studentNumber;
        this.phoneNumber = phoneNumber;
        this.place = place;
        this.resCount = resCount;
    }

    @Getter
    @Setter
    public static class PlaceInfo {
        private String id;
        private String name;
        private int count;

        public PlaceInfo() {
        }

        public PlaceInfo(final String id, final String name, final int count) {
            this.id = id;
            this.name = name;
            this.count = count;
        }
    }
}
