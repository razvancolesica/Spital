package com.spital.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdminTest {

    @Test
    public void testAdminEntity() {
        Admin admin = new Admin();
        admin.setAdminID(1);
        admin.setEmail("admin@example.com");
        admin.setPassword("securepassword");
        admin.setUserType("admin");

        assertNotNull(admin.getAdminID());
        assertEquals(1, admin.getAdminID());
        assertEquals("admin@example.com", admin.getEmail());
        assertEquals("securepassword", admin.getPassword());
        assertEquals("admin", admin.getUserType());
    }
}
