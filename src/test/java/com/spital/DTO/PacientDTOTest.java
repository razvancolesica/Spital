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
}
