package com.spital.DTO;

import com.spital.DTO.UserDetails;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserDetailsTest {

    @Test
    public void testNoArgsConstructor() {
        UserDetails userDetails = new UserDetails();
        assertNull(userDetails.getEmail());
        assertNull(userDetails.getPassword());
        assertNull(userDetails.getUserType());
    }

    @Test
    public void testAllArgsConstructor() {
        UserDetails userDetails = new UserDetails("test@example.com", "password123", "USER");
        assertEquals("test@example.com", userDetails.getEmail());
        assertEquals("password123", userDetails.getPassword());
        assertEquals("USER", userDetails.getUserType());
    }

    @Test
    public void testSettersAndGetters() {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("test@example.com");
        userDetails.setPassword("password123");
        userDetails.setUserType("USER");

        assertEquals("test@example.com", userDetails.getEmail());
        assertEquals("password123", userDetails.getPassword());
        assertEquals("USER", userDetails.getUserType());
    }

    @Test
    public void testBuilder() {
        UserDetails userDetails = UserDetails.builder()
                .email("builder@example.com")
                .password("password123")
                .userType("USER")
                .build();
        assertEquals("builder@example.com", userDetails.getEmail());
        assertEquals("password123", userDetails.getPassword());
        assertEquals("USER", userDetails.getUserType());
    }

}
