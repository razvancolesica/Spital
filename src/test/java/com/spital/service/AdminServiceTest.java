package com.spital.service;

import com.spital.DTO.AdminDTO;
import com.spital.entity.Admin;
import com.spital.repository.AdminRepository;
import com.spital.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class AdminServiceTest {
    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAdminByEmail_Found() {
        String email = "admin@example.com";
        Admin admin = new Admin();
        admin.setAdminID(1);
        admin.setEmail(email);

        when(adminRepository.findByEmail(email)).thenReturn(Optional.of(admin));

        AdminDTO adminDTO = adminService.getAdminByEmail(email);

        assertEquals(1, adminDTO.getAdminID());
        assertEquals(email, adminDTO.getEmail());
    }

    @Test
    public void testGetAdminByEmail_NotFound() {
        String email = "admin@example.com";
        when(adminRepository.findByEmail(email)).thenReturn(Optional.empty());

        AdminDTO adminDTO = adminService.getAdminByEmail(email);

        assertNull(adminDTO);
    }
}
