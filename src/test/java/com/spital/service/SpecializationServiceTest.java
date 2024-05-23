package com.spital.service;

import com.spital.DTO.SpecializationDTO;
import com.spital.entity.Specialization;
import com.spital.repository.SpecializationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

public class SpecializationServiceTest {

    @Mock
    private SpecializationRepository specializationRepository;

    @InjectMocks
    private SpecializationService specializationService;

    private ModelMapper mapper = new ModelMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        specializationService.mapper = mapper; // Injecting the mapper manually since @InjectMocks does not handle it
    }

    @Test
    public void testGetAllSpecializations() {
        Specialization specialization1 = new Specialization();
        specialization1.setSpecializationID(1);
        Specialization specialization2 = new Specialization();
        specialization2.setSpecializationID(2);

        when(specializationRepository.findAll()).thenReturn(Arrays.asList(specialization1, specialization2));

        List<SpecializationDTO> specializations = specializationService.getAllSpecializations();

        assertEquals(2, specializations.size());
    }

    @Test
    public void testAddSpecialization_SpecializationNotExists() {
        SpecializationDTO specializationDTO = new SpecializationDTO();
        specializationDTO.setSpecializationName("Cardiology");
        specializationDTO.setMedic("Dr. Smith");

        Specialization specialization = new Specialization();
        specialization.setSpecializationID(1);
        specialization.setSpecializationName("Cardiology");
        specialization.setMedic("Dr. Smith");

        when(specializationRepository.findAll()).thenReturn(Arrays.asList());

        SpecializationDTO addedSpecialization = specializationService.addSpecialization(specializationDTO);

        assertNotNull(addedSpecialization);
        verify(specializationRepository).save(any(Specialization.class));
    }

    @Test
    public void testAddSpecialization_SpecializationExists() {
        SpecializationDTO specializationDTO = new SpecializationDTO();
        specializationDTO.setSpecializationName("Cardiology");
        specializationDTO.setMedic("Dr. Smith");

        Specialization existingSpecialization = new Specialization();
        existingSpecialization.setSpecializationName("Cardiology");
        existingSpecialization.setMedic("Dr. Smith");

        when(specializationRepository.findAll()).thenReturn(Arrays.asList(existingSpecialization));

        SpecializationDTO addedSpecialization = specializationService.addSpecialization(specializationDTO);

        assertNull(addedSpecialization);
        verify(specializationRepository, never()).save(any(Specialization.class));
    }

    @Test
    public void testEditSpecialization_SpecializationExists() {
        Integer specializationID = 1;
        SpecializationDTO updatedSpecializationDTO = new SpecializationDTO();
        updatedSpecializationDTO.setSpecializationName("Updated Cardiology");
        updatedSpecializationDTO.setMedic("Dr. John");

        Specialization existingSpecialization = new Specialization();
        existingSpecialization.setSpecializationID(specializationID);

        when(specializationRepository.findById(String.valueOf(specializationID))).thenReturn(Optional.of(existingSpecialization));

        SpecializationDTO editedSpecialization = specializationService.editSpecialization(specializationID, updatedSpecializationDTO);

        assertNotNull(editedSpecialization);
        assertEquals(updatedSpecializationDTO.getSpecializationName(), editedSpecialization.getSpecializationName());
        verify(specializationRepository).save(any(Specialization.class));
    }

    @Test
    public void testEditSpecialization_SpecializationNotFound() {
        Integer specializationID = 1;
        SpecializationDTO updatedSpecializationDTO = new SpecializationDTO();

        when(specializationRepository.findById(String.valueOf(specializationID))).thenReturn(Optional.empty());

        SpecializationDTO editedSpecialization = specializationService.editSpecialization(specializationID, updatedSpecializationDTO);

        assertNull(editedSpecialization);
        verify(specializationRepository, never()).save(any(Specialization.class));
    }

    @Test
    public void testGetSpecializationById() {
        Integer specializationID = 1;
        Specialization specialization = new Specialization();
        specialization.setSpecializationID(specializationID);

        when(specializationRepository.findById(String.valueOf(specializationID))).thenReturn(Optional.of(specialization));

        Optional<SpecializationDTO> result = specializationService.getSpecializationById(specializationID);

        assertTrue(result.isPresent());
        assertEquals(specializationID, result.get().getSpecializationID());
    }

    @Test
    public void testDeleteSpecialization() {
        Integer specializationID = 1;

        specializationService.deleteSpecialization(specializationID);

        verify(specializationRepository).deleteById(String.valueOf(specializationID));
    }
}
