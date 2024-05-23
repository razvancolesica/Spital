package com.spital.service;

import com.spital.DTO.PacientDTO;
import com.spital.DTO.ReservationDTO;
import com.spital.DTO.SpecializationDTO;
import com.spital.entity.Pacient;
import com.spital.entity.Reservation;
import com.spital.entity.Specialization;
import com.spital.repository.PacientRepository;
import com.spital.repository.ReservationRepository;
import com.spital.repository.SpecializationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private PacientRepository pacientRepository;

    @Mock
    private SpecializationRepository specializationRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ReservationService reservationService;

    private ModelMapper mapper = new ModelMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllReservations() {
        Reservation reservation1 = new Reservation();
        reservation1.setId(1);
        Reservation reservation2 = new Reservation();
        reservation2.setId(2);

        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation1, reservation2));

        List<ReservationDTO> reservations = reservationService.getAllReservations();

        assertEquals(2, reservations.size());
    }

    @Test
    public void testAddReservation_SpecializationExists() {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setFirstName("John");
        reservationDTO.setLastName("Doe");
        reservationDTO.setSpecialization("Cardiology");
        reservationDTO.setReservationDate(LocalDateTime.now());

        Specialization specialization = new Specialization();
        specialization.setSpecializationName("Cardiology");

        Pacient pacient = new Pacient();
        pacient.setFirstName("John");
        pacient.setLastName("Doe");
        pacient.setEmail("john.doe@example.com");

        when(specializationRepository.findBySpecializationName("Cardiology")).thenReturn(Optional.of(specialization));
        when(pacientRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.of(pacient));

        ReservationDTO addedReservation = reservationService.addReservation(reservationDTO);

        assertNotNull(addedReservation);
        verify(reservationRepository).save(any(Reservation.class));
        verify(emailService).sendReservationDoneEmail(anyString(), anyString(), anyString(), any(LocalDateTime.class), anyString());
    }

    @Test
    public void testAddReservation_SpecializationNotExists() {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setFirstName("John");
        reservationDTO.setLastName("Doe");
        reservationDTO.setSpecialization("UnknownSpecialization");

        when(specializationRepository.findBySpecializationName("UnknownSpecialization")).thenReturn(Optional.empty());

        ReservationDTO addedReservation = reservationService.addReservation(reservationDTO);

        assertNull(addedReservation);
        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    @Test
    public void testDeleteReservation() {
        Integer reservationId = 1;

        reservationService.deleteReservation(reservationId);

        verify(reservationRepository).deleteById(String.valueOf(reservationId));
    }

    @Test
    public void testEditReservation() {
        Integer reservationId = 1;
        ReservationDTO updatedReservationDTO = new ReservationDTO();
        updatedReservationDTO.setFirstName("John");
        updatedReservationDTO.setLastName("Doe");
        updatedReservationDTO.setSpecialization("Cardiology");
        updatedReservationDTO.setReservationDate(LocalDateTime.now());

        Reservation reservation = new Reservation();
        reservation.setId(Integer.valueOf(reservationId.toString()));

        when(reservationRepository.findById(String.valueOf(reservationId))).thenReturn(Optional.of(reservation));

        ReservationDTO editedReservation = reservationService.editReservation(reservationId, updatedReservationDTO);

        assertNotNull(editedReservation);
        assertEquals(updatedReservationDTO.getFirstName(), editedReservation.getFirstName());
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    public void testEditReservation_NotFound() {
        Integer reservationId = 1;
        ReservationDTO updatedReservationDTO = new ReservationDTO();

        when(reservationRepository.findById(String.valueOf(reservationId))).thenReturn(Optional.empty());

        ReservationDTO editedReservation = reservationService.editReservation(reservationId, updatedReservationDTO);

        assertNull(editedReservation);
        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    @Test
    public void testGetReservationById() {
        Integer reservationId = 1;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);

        when(reservationRepository.findById(String.valueOf(reservationId))).thenReturn(Optional.of(reservation));

        Optional<ReservationDTO> result = reservationService.getReservationById(reservationId);

        assertTrue(result.isPresent());
        assertEquals(reservationId, result.get().getId());
    }
}
