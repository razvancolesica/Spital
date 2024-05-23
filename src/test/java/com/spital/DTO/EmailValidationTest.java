package com.spital.DTO;

import com.spital.DTO.EmailValidation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmailValidationTest {

    @Test
    public void testNoArgsConstructor() {
        EmailValidation emailValidation = new EmailValidation();
        assertNull(emailValidation.getEmail());
    }

    @Test
    public void testAllArgsConstructor() {
        EmailValidation emailValidation = new EmailValidation("test@example.com");
        assertEquals("test@example.com", emailValidation.getEmail());
    }

    @Test
    public void testSettersAndGetters() {
        EmailValidation emailValidation = new EmailValidation();
        emailValidation.setEmail("test@example.com");
        assertEquals("test@example.com", emailValidation.getEmail());
    }

    @Test
    public void testBuilder() {
        EmailValidation emailValidation = EmailValidation.builder()
                .email("builder@example.com")
                .build();
        assertEquals("builder@example.com", emailValidation.getEmail());
    }

    @Test
    public void testEqualsAndHashCode() {
        EmailValidation emailValidation1 = new EmailValidation("test@example.com");
        EmailValidation emailValidation2 = new EmailValidation("test@example.com");
        EmailValidation emailValidation3 = new EmailValidation("different@example.com");

        assertEquals(emailValidation1, emailValidation2);
        assertNotEquals(emailValidation1, emailValidation3);
        assertEquals(emailValidation1.hashCode(), emailValidation2.hashCode());
        assertNotEquals(emailValidation1.hashCode(), emailValidation3.hashCode());
    }

    @Test
    public void testToString() {
        EmailValidation emailValidation = new EmailValidation("test@example.com");
        String expectedString = "EmailValidation(email=test@example.com)";
        assertEquals(expectedString, emailValidation.toString());
    }
}
