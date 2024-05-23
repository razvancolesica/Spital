import com.spital.DTO.AdminDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdminDTOTest {

    @Test
    public void testNoArgsConstructor() {
        AdminDTO admin = new AdminDTO();
        assertNull(admin.getAdminID());
        assertNull(admin.getEmail());
        assertNull(admin.getPassword());
        assertNull(admin.getUserType());
    }

    @Test
    public void testAllArgsConstructor() {
        AdminDTO admin = new AdminDTO(1, "admin@example.com", "password123", "ADMIN");
        assertEquals(1, admin.getAdminID());
        assertEquals("admin@example.com", admin.getEmail());
        assertEquals("password123", admin.getPassword());
        assertEquals("ADMIN", admin.getUserType());
    }

    @Test
    public void testSettersAndGetters() {
        AdminDTO admin = new AdminDTO();
        admin.setAdminID(1);
        admin.setEmail("admin@example.com");
        admin.setPassword("password123");
        admin.setUserType("ADMIN");

        assertEquals(1, admin.getAdminID());
        assertEquals("admin@example.com", admin.getEmail());
        assertEquals("password123", admin.getPassword());
        assertEquals("ADMIN", admin.getUserType());
    }
}
