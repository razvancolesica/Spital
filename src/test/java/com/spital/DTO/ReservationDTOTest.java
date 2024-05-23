package com.spital.DTO;

import com.spital.DTO.ReservationDTO;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationDTOTest {

    @Test
    public void testEqualsAndHashCode() {
        LocalDateTime reservationDate = LocalDateTime.of(2024, 5, 23, 10, 0);
        ReservationDTO reservation1 = new ReservationDTO(1, 1, "John", "Doe", reservationDate, "Cardiology", "Heart issue");
        ReservationDTO reservation2 = new ReservationDTO(1, 1, "John", "Doe", reservationDate, "Cardiology", "Heart issue");
        ReservationDTO reservation3 = new ReservationDTO(2, 2, "Jane", "Doe", reservationDate, "Orthopedics", "Knee issue");

        assertEquals(reservation1, reservation2);
        assertNotEquals(reservation1, reservation3);
        assertEquals(reservation1.hashCode(), reservation2.hashCode());
        assertNotEquals(reservation1.hashCode(), reservation3.hashCode());
    }

}
