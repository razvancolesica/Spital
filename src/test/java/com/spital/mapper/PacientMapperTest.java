package com.spital.mapper;

import com.spital.DTO.PacientDTO;
import com.spital.entity.Pacient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PacientMapperTest {

    @Test
    public void testMapToPacientEntity() {
        PacientMapper pacientMapper = new PacientMapper();

        PacientDTO pacientDTO = new PacientDTO();
        pacientDTO.setPacientID(1);
        pacientDTO.setFirstName("John");
        pacientDTO.setLastName("Doe");
        pacientDTO.setAge(30);
        pacientDTO.setCnp("1234567890123");
        pacientDTO.setEmail("john.doe@example.com");
        pacientDTO.setPassword("password");
        pacientDTO.setPhoneNumber("1234567890");
        pacientDTO.setUserType("user");

        Pacient pacient = pacientMapper.mapToPacientEntity(pacientDTO);

        assertEquals(1, pacient.getPacientID());
        assertEquals("John", pacient.getFirstName());
        assertEquals("Doe", pacient.getLastName());
        assertEquals(30, pacient.getAge());
        assertEquals("1234567890123", pacient.getCnp());
        assertEquals("john.doe@example.com", pacient.getEmail());
        assertEquals("password", pacient.getPassword());
        assertEquals("1234567890", pacient.getPhoneNumber());
        assertEquals("user", pacient.getUserType());
    }
}
