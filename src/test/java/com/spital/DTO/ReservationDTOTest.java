import com.spital.DTO.ReservationDTO;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationDTOTest {

    @Test
    public void testNoArgsConstructor() {
        ReservationDTO reservation = new ReservationDTO();
        assertNull(reservation.getId());
        assertNull(reservation.getPacientID());
        assertNull(reservation.getFirstName());
        assertNull(reservation.getLastName());
        assertNull(reservation.getReservationDate());
        assertNull(reservation.getSpecialization());
        assertNull(reservation.getIssue());
    }

    @Test
    public void testAllArgsConstructor() {
        LocalDateTime reservationDate = LocalDateTime.of(2024, 5, 23, 10, 0);
        ReservationDTO reservation = new ReservationDTO(1, 1, "John", "Doe", reservationDate, "Cardiology", "Heart issue");
        assertEquals(1, reservation.getId());
        assertEquals(1, reservation.getPacientID());
        assertEquals("John", reservation.getFirstName());
        assertEquals("Doe", reservation.getLastName());
        assertEquals(reservationDate, reservation.getReservationDate());
        assertEquals("Cardiology", reservation.getSpecialization());
        assertEquals("Heart issue", reservation.getIssue());
    }

    @Test
    public void testSettersAndGetters() {
        LocalDateTime reservationDate = LocalDateTime.of(2024, 5, 23, 10, 0);
        ReservationDTO reservation = new ReservationDTO();
        reservation.setId(1);
        reservation.setPacientID(1);
        reservation.setFirstName("John");
        reservation.setLastName("Doe");
        reservation.setReservationDate(reservationDate);
        reservation.setSpecialization("Cardiology");
        reservation.setIssue("Heart issue");

        assertEquals(1, reservation.getId());
        assertEquals(1, reservation.getPacientID());
        assertEquals("John", reservation.getFirstName());
        assertEquals("Doe", reservation.getLastName());
        assertEquals(reservationDate, reservation.getReservationDate());
        assertEquals("Cardiology", reservation.getSpecialization());
        assertEquals("Heart issue", reservation.getIssue());
    }

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

    @Test
    public void testToString() {
        LocalDateTime reservationDate = LocalDateTime.of(2024, 5, 23, 10, 0);
        ReservationDTO reservation = new ReservationDTO(1, 1, "John", "Doe", reservationDate, "Cardiology", "Heart issue");
        String expectedString = "ReservationDTO(id=1, pacientID=1, firstName=John, lastName=Doe, reservationDate=2024-05-23T10:00, specialization=Cardiology, issue=Heart issue)";
        assertEquals(expectedString, reservation.toString());
    }
}
