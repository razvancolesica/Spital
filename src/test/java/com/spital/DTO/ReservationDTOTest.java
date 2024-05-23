package com.spital.DTO;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class ReservationDTOTest {

    @Test
    public void testNoArgsConstructor() {
        ReservationDTO reservationDTO = new ReservationDTO();
        assertNull(reservationDTO.getId());
        assertNull(reservationDTO.getPacientID());
        assertNull(reservationDTO.getFirstName());
        assertNull(reservationDTO.getLastName());
        assertNull(reservationDTO.getReservationDate());
        assertNull(reservationDTO.getSpecialization());
        assertNull(reservationDTO.getIssue());
    }

    @Test
    public void testAllArgsConstructor() {
        LocalDateTime reservationDate = LocalDateTime.now();
        ReservationDTO reservationDTO = new ReservationDTO(1, 2, "John", "Doe", reservationDate, "Specialization", "Issue");
        assertEquals(1, reservationDTO.getId());
        assertEquals(2, reservationDTO.getPacientID());
        assertEquals("John", reservationDTO.getFirstName());
        assertEquals("Doe", reservationDTO.getLastName());
        assertEquals(reservationDate, reservationDTO.getReservationDate());
        assertEquals("Specialization", reservationDTO.getSpecialization());
        assertEquals("Issue", reservationDTO.getIssue());
    }

    @Test
    public void testSettersAndGetters() {
        LocalDateTime reservationDate = LocalDateTime.now();
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(1);
        reservationDTO.setPacientID(2);
        reservationDTO.setFirstName("John");
        reservationDTO.setLastName("Doe");
        reservationDTO.setReservationDate(reservationDate);
        reservationDTO.setSpecialization("Specialization");
        reservationDTO.setIssue("Issue");

        assertEquals(1, reservationDTO.getId());
        assertEquals(2, reservationDTO.getPacientID());
        assertEquals("John", reservationDTO.getFirstName());
        assertEquals("Doe", reservationDTO.getLastName());
        assertEquals(reservationDate, reservationDTO.getReservationDate());
        assertEquals("Specialization", reservationDTO.getSpecialization());
        assertEquals("Issue", reservationDTO.getIssue());
    }
}
