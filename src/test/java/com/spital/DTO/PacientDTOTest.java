import com.spital.DTO.PacientDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PacientDTOTest {

    @Test
    public void testNoArgsConstructor() {
        PacientDTO pacient = new PacientDTO();
        assertNull(pacient.getPacientID());
        assertNull(pacient.getFirstName());
        assertNull(pacient.getLastName());
        assertEquals(0, pacient.getAge());
        assertNull(pacient.getCnp());
        assertNull(pacient.getEmail());
        assertNull(pacient.getPassword());
        assertNull(pacient.getPhoneNumber());
        assertNull(pacient.getUserType());
    }

    @Test
    public void testAllArgsConstructor() {
        PacientDTO pacient = new PacientDTO(1, "John", "Doe", 30, "1234567890123", "john.doe@example.com", "password123", "0123456789", "PACIENT");
        assertEquals(1, pacient.getPacientID());
        assertEquals("John", pacient.getFirstName());
        assertEquals("Doe", pacient.getLastName());
        assertEquals(30, pacient.getAge());
        assertEquals("1234567890123", pacient.getCnp());
        assertEquals("john.doe@example.com", pacient.getEmail());
        assertEquals("password123", pacient.getPassword());
        assertEquals("0123456789", pacient.getPhoneNumber());
        assertEquals("PACIENT", pacient.getUserType());
    }

    @Test
    public void testSettersAndGetters() {
        PacientDTO pacient = new PacientDTO();
        pacient.setPacientID(1);
        pacient.setFirstName("John");
        pacient.setLastName("Doe");
        pacient.setAge(30);
        pacient.setCnp("1234567890123");
        pacient.setEmail("john.doe@example.com");
        pacient.setPassword("password123");
        pacient.setPhoneNumber("0123456789");
        pacient.setUserType("PACIENT");

        assertEquals(1, pacient.getPacientID());
        assertEquals("John", pacient.getFirstName());
        assertEquals("Doe", pacient.getLastName());
        assertEquals(30, pacient.getAge());
        assertEquals("1234567890123", pacient.getCnp());
        assertEquals("john.doe@example.com", pacient.getEmail());
        assertEquals("password123", pacient.getPassword());
        assertEquals("0123456789", pacient.getPhoneNumber());
        assertEquals("PACIENT", pacient.getUserType());
    }

    @Test
    public void testEqualsAndHashCode() {
        PacientDTO pacient1 = new PacientDTO(1, "John", "Doe", 30, "1234567890123", "john.doe@example.com", "password123", "0123456789", "PACIENT");
        PacientDTO pacient2 = new PacientDTO(1, "John", "Doe", 30, "1234567890123", "john.doe@example.com", "password123", "0123456789", "PACIENT");
        PacientDTO pacient3 = new PacientDTO(2, "Jane", "Doe", 25, "9876543210987", "jane.doe@example.com", "password456", "9876543210", "PACIENT");

        assertEquals(pacient1, pacient2);
        assertNotEquals(pacient1, pacient3);
        assertEquals(pacient1.hashCode(), pacient2.hashCode());
        assertNotEquals(pacient1.hashCode(), pacient3.hashCode());
    }

    @Test
    public void testToString() {
        PacientDTO pacient = new PacientDTO(1, "John", "Doe", 30, "1234567890123", "john.doe@example.com", "password123", "0123456789", "PACIENT");
        String expectedString = "PacientDTO(pacientID=1, firstName=John, lastName=Doe, age=30, cnp=1234567890123, email=john.doe@example.com, password=password123, phoneNumber=0123456789, userType=PACIENT)";
        assertEquals(expectedString, pacient.toString());
    }
}
