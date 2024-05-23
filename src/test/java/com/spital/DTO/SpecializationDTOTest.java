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

    @Test
    public void testEqualsAndHashCode() {
        SpecializationDTO specialization1 = new SpecializationDTO(1, "Cardiology", "Dr. Smith", "Room 101");
        SpecializationDTO specialization2 = new SpecializationDTO(1, "Cardiology", "Dr. Smith", "Room 101");
        SpecializationDTO specialization3 = new SpecializationDTO(2, "Orthopedics", "Dr. Brown", "Room 102");

        assertEquals(specialization1, specialization2);
        assertNotEquals(specialization1, specialization3);
        assertEquals(specialization1.hashCode(), specialization2.hashCode());
        assertNotEquals(specialization1.hashCode(), specialization3.hashCode());
    }

    @Test
    public void testToString() {
        SpecializationDTO specialization = new SpecializationDTO(1, "Cardiology", "Dr. Smith", "Room 101");
        String expectedString = "SpecializationDTO(specializationID=1, specializationName=Cardiology, medic=Dr. Smith, room=Room 101)";
        assertEquals(expectedString, specialization.toString());
    }
}
