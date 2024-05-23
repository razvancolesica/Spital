package com.spital.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PasswordResetTest {

    @Test
    public void testPasswordResetEntity() {
        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setIdPasswordReset(1);
        passwordReset.setEmail("john.doe@example.com");
        passwordReset.setResetCode("123456");

        assertNotNull(passwordReset.getIdPasswordReset());
        assertEquals(1, passwordReset.getIdPasswordReset());
        assertEquals("john.doe@example.com", passwordReset.getEmail());
        assertEquals("123456", passwordReset.getResetCode());
    }

    @Test
    public void testPasswordResetBuilder() {
        PasswordReset passwordReset = PasswordReset.builder()
                .idPasswordReset(1)
                .email("john.doe@example.com")
                .resetCode("123456")
                .build();

        assertNotNull(passwordReset);
        assertEquals(1, passwordReset.getIdPasswordReset());
        assertEquals("john.doe@example.com", passwordReset.getEmail());
        assertEquals("123456", passwordReset.getResetCode());
    }

    @Test
    public void testPasswordResetAllArgsConstructor() {
        PasswordReset passwordReset = new PasswordReset(1, "john.doe@example.com", "123456");

        assertNotNull(passwordReset);
        assertEquals(1, passwordReset.getIdPasswordReset());
        assertEquals("john.doe@example.com", passwordReset.getEmail());
        assertEquals("123456", passwordReset.getResetCode());
    }
}
