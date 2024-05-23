import com.spital.DTO.SpecializationDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SpecializationDTOTest {

    @Test
    public void testNoArgsConstructor() {
        SpecializationDTO specialization = new SpecializationDTO();
        assertNull(specialization.getSpecializationID());
        assertNull(specialization.getSpecializationName());
        assertNull(specialization.getMedic());
        assertNull(specialization.getRoom());
    }

    @Test
    public void testAllArgsConstructor() {
        SpecializationDTO specialization = new SpecializationDTO(1, "Cardiology", "Dr. Smith", "Room 101");
        assertEquals(1, specialization.getSpecializationID());
        assertEquals("Cardiology", specialization.getSpecializationName());
        assertEquals("Dr. Smith", specialization.getMedic());
        assertEquals("Room 101", specialization.getRoom());
    }

    @Test
    public void testSettersAndGetters() {
        SpecializationDTO specialization = new SpecializationDTO();
        specialization.setSpecializationID(1);
        specialization.setSpecializationName("Cardiology");
        specialization.setMedic("Dr. Smith");
        specialization.setRoom("Room 101");

        assertEquals(1, specialization.getSpecializationID());
        assertEquals("Cardiology", specialization.getSpecializationName());
        assertEquals("Dr. Smith", specialization.getMedic());
        assertEquals("Room 101", specialization.getRoom());
    }

}
