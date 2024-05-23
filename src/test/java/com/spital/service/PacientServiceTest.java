package com.spital.service;

import com.spital.DTO.PacientDTO;
import com.spital.DTO.ReservationDTO;
import com.spital.entity.Pacient;
import com.spital.repository.AdminRepository;
import com.spital.repository.PacientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PacientServiceTest {

    @Mock
    private PacientRepository pacientRepository;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private PacientService pacientService;

    private ModelMapper mapper = new ModelMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        pacientService.mapper = this.mapper;  // Inject the ModelMapper instance
    }

    @Test
    public void testGetAllPacients() {
        Pacient pacient1 = new Pacient();
        pacient1.setFirstName("John");
        pacient1.setLastName("Doe");
        Pacient pacient2 = new Pacient();
        pacient2.setFirstName("Jane");
        pacient2.setLastName("Doe");

        when(pacientRepository.findAll()).thenReturn(Arrays.asList(pacient1, pacient2));

        List<PacientDTO> result = pacientService.getAllPacients();

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
    }

    @Test
    public void testAddPacient() {
        PacientDTO pacientDTO = new PacientDTO();
        pacientDTO.setFirstName("John");
        pacientDTO.setLastName("Doe");

        Pacient pacient = new Pacient();
        pacient.setFirstName("John");
        pacient.setLastName("Doe");

        when(pacientRepository.save(any(Pacient.class))).thenReturn(pacient);

        PacientDTO result = pacientService.addPacient(pacientDTO);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }

    @Test
    public void testDeletePacient() {
        Integer pacientID = 1;
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setPacientID(pacientID);

        when(reservationService.getAllReservations()).thenReturn(Arrays.asList(reservationDTO));

        pacientService.deletePacient(pacientID);

        verify(pacientRepository).deleteById(String.valueOf(pacientID));
        verify(reservationService).deleteReservation(reservationDTO.getId());
    }

    @Test
    public void testEditPacient() {
        Integer pacientID = 1;
        PacientDTO updatedPacientDTO = new PacientDTO();
        updatedPacientDTO.setFirstName("John");
        updatedPacientDTO.setLastName("Doe");

        Pacient pacient = new Pacient();
        pacient.setFirstName("OldName");
        pacient.setLastName("OldLastName");

        when(pacientRepository.findById(String.valueOf(pacientID))).thenReturn(Optional.of(pacient));
        when(pacientRepository.save(any(Pacient.class))).thenReturn(pacient);

        PacientDTO result = pacientService.editPacient(pacientID, updatedPacientDTO);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }

    @Test
    public void testGetPacientById() {
        Integer pacientID = 1;
        Pacient pacient = new Pacient();
        pacient.setFirstName("John");
        pacient.setLastName("Doe");

        when(pacientRepository.findById(String.valueOf(pacientID))).thenReturn(Optional.of(pacient));

        Optional<PacientDTO> result = pacientService.getPacientById(pacientID);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
    }

    @Test
    public void testGetPacientByEmail() {
        String email = "john.doe@example.com";
        Pacient pacient = new Pacient();
        pacient.setPacientID(1);
        pacient.setFirstName("John");
        pacient.setLastName("Doe");
        pacient.setEmail(email);

        when(pacientRepository.findByEmail(email)).thenReturn(Optional.of(pacient));

        PacientDTO result = pacientService.getPacientByEmail(email);

        assertNotNull(result);
        assertEquals(1, result.getPacientID());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(email, result.getEmail());
    }

    @Test
    public void testEmailExists() {
        String email = "john.doe@example.com";

        when(pacientRepository.findByEmail(email)).thenReturn(Optional.of(new Pacient()));
        when(adminRepository.findByEmail(email)).thenReturn(Optional.empty());

        boolean result = pacientService.emailExists(email);

        assertTrue(result);
    }
}
