import com.spital.DTO.PasswordResetDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordResetDTOTest {

    @Test
    public void testNoArgsConstructor() {
        PasswordResetDTO passwordReset = new PasswordResetDTO();
        assertNull(passwordReset.getEmail());
    }

    @Test
    public void testAllArgsConstructor() {
        PasswordResetDTO passwordReset = new PasswordResetDTO("test@example.com");
        assertEquals("test@example.com", passwordReset.getEmail());
    }

    @Test
    public void testSettersAndGetters() {
        PasswordResetDTO passwordReset = new PasswordResetDTO();
        passwordReset.setEmail("test@example.com");
        assertEquals("test@example.com", passwordReset.getEmail());
    }

    @Test
    public void testBuilder() {
        PasswordResetDTO passwordReset = PasswordResetDTO.builder()
                .email("builder@example.com")
                .build();
        assertEquals("builder@example.com", passwordReset.getEmail());
    }

}
