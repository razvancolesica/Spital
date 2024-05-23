package com.spital.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PacientTest {

    @Test
    public void testPacientEntity() {
        Pacient pacient = new Pacient();
        pacient.setPacientID(1);
        pacient.setFirstName("John");
        pacient.setLastName("Doe");
        pacient.setAge(30);
        pacient.setCnp("1234567890123");
        pacient.setEmail("john.doe@example.com");
        pacient.setPassword("securepassword");
        pacient.setPhoneNumber("1234567890");
        pacient.setUserType("pacient");

        assertNotNull(pacient.getPacientID());
        assertEquals(1, pacient.getPacientID());
        assertEquals("John", pacient.getFirstName());
        assertEquals("Doe", pacient.getLastName());
        assertEquals(30, pacient.getAge());
        assertEquals("1234567890123", pacient.getCnp());
        assertEquals("john.doe@example.com", pacient.getEmail());
        assertEquals("securepassword", pacient.getPassword());
        assertEquals("1234567890", pacient.getPhoneNumber());
        assertEquals("pacient", pacient.getUserType());
    }
}
