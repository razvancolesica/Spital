package com.spital.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReservationTest {

    @Test
    public void testReservationEntity() {
        LocalDateTime now = LocalDateTime.now();

        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setPacientID(123);
        reservation.setFirstName("John");
        reservation.setLastName("Doe");
        reservation.setReservationDate(now);
        reservation.setSpecialization("Cardiology");
        reservation.setIssue("Heart Pain");

        assertNotNull(reservation.getId());
        assertEquals(1, reservation.getId());
        assertEquals(123, reservation.getPacientID());
        assertEquals("John", reservation.getFirstName());
        assertEquals("Doe", reservation.getLastName());
        assertEquals(now, reservation.getReservationDate());
        assertEquals("Cardiology", reservation.getSpecialization());
        assertEquals("Heart Pain", reservation.getIssue());
    }

    @Test
    public void testReservationConstructor() {
        LocalDateTime now = LocalDateTime.now();

        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setPacientID(123);
        reservation.setFirstName("John");
        reservation.setLastName("Doe");
        reservation.setReservationDate(now);
        reservation.setSpecialization("Cardiology");
        reservation.setIssue("Heart Pain");

        assertNotNull(reservation);
        assertEquals(1, reservation.getId());
        assertEquals(123, reservation.getPacientID());
        assertEquals("John", reservation.getFirstName());
        assertEquals("Doe", reservation.getLastName());
        assertEquals(now, reservation.getReservationDate());
        assertEquals("Cardiology", reservation.getSpecialization());
        assertEquals("Heart Pain", reservation.getIssue());
    }
}
