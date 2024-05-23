package com.spital.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SpecializationTest {

    @Test
    public void testSpecializationEntity() {
        Specialization specialization = new Specialization();
        specialization.setSpecializationID(1);
        specialization.setSpecializationName("Cardiology");
        specialization.setMedic("Dr. Smith");
        specialization.setRoom("101");

        assertNotNull(specialization.getSpecializationID());
        assertEquals(1, specialization.getSpecializationID());
        assertEquals("Cardiology", specialization.getSpecializationName());
        assertEquals("Dr. Smith", specialization.getMedic());
        assertEquals("101", specialization.getRoom());
    }

    @Test
    public void testSpecializationConstructor() {
        Specialization specialization = new Specialization();
        specialization.setSpecializationID(2);
        specialization.setSpecializationName("Neurology");
        specialization.setMedic("Dr. Johnson");
        specialization.setRoom("102");

        assertNotNull(specialization);
        assertEquals(2, specialization.getSpecializationID());
        assertEquals("Neurology", specialization.getSpecializationName());
        assertEquals("Dr. Johnson", specialization.getMedic());
        assertEquals("102", specialization.getRoom());
    }
}
