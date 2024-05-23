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

    @Test
    public void testEqualsAndHashCode() {
        UserDetails userDetails1 = new UserDetails("test@example.com", "password123", "USER");
        UserDetails userDetails2 = new UserDetails("test@example.com", "password123", "USER");
        UserDetails userDetails3 = new UserDetails("different@example.com", "password456", "ADMIN");

        assertEquals(userDetails1, userDetails2);
        assertNotEquals(userDetails1, userDetails3);
        assertEquals(userDetails1.hashCode(), userDetails2.hashCode());
        assertNotEquals(userDetails1.hashCode(), userDetails3.hashCode());
    }

    @Test
    public void testToString() {
        UserDetails userDetails = new UserDetails("test@example.com", "password123", "USER");
        String expectedString = "UserDetails(email=test@example.com, password=password123, userType=USER)";
        assertEquals(expectedString, userDetails.toString());
    }
}
