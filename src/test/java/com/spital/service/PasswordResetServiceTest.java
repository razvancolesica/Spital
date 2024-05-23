package com.spital.service;

import com.spital.entity.Admin;
import com.spital.entity.Pacient;
import com.spital.entity.PasswordReset;
import com.spital.repository.AdminRepository;
import com.spital.repository.PacientRepository;
import com.spital.repository.PasswordResetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PasswordResetServiceTest {

    @Mock
    private EmailService emailService;

    @Mock
    private PacientRepository pacientRepository;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordResetRepository passwordResetRepository;

    @InjectMocks
    private PasswordResetService passwordResetService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerateAndSaveResetCode() {
        String email = "test@example.com";
        ArgumentCaptor<PasswordReset> captor = ArgumentCaptor.forClass(PasswordReset.class);

        passwordResetService.generateAndSaveResetCode(email);

        verify(passwordResetRepository).save(captor.capture());
        PasswordReset savedReset = captor.getValue();
        assertEquals(email, savedReset.getEmail());
        assertNotNull(savedReset.getResetCode());
        verify(emailService).sendResetCodeEmail(eq(email), anyString());
    }

    @Test
    public void testGenerateAndSaveResetCodeValidation() {
        String email = "test@example.com";
        ArgumentCaptor<PasswordReset> captor = ArgumentCaptor.forClass(PasswordReset.class);

        passwordResetService.generateAndSaveResetCodeValidation(email);

        verify(passwordResetRepository).save(captor.capture());
        PasswordReset savedReset = captor.getValue();
        assertEquals(email, savedReset.getEmail());
        assertNotNull(savedReset.getResetCode());
        verify(emailService).sendValidateEmail(eq(email), anyString());
    }

    @Test
    public void testVerifyResetCode() {
        String email = "test@example.com";
        String resetCode = UUID.randomUUID().toString().substring(0, 6);
        PasswordReset passwordReset = PasswordReset.builder()
                .email(email)
                .resetCode(resetCode)
                .build();

        when(passwordResetRepository.findByEmail(email)).thenReturn(Arrays.asList(passwordReset));

        boolean result = passwordResetService.verifyResetCode(email, resetCode);

        assertTrue(result);
    }

    @Test
    public void testVerifyResetCode_InvalidCode() {
        String email = "test@example.com";
        String resetCode = UUID.randomUUID().toString().substring(0, 6);
        PasswordReset passwordReset = PasswordReset.builder()
                .email(email)
                .resetCode(resetCode)
                .build();

        when(passwordResetRepository.findByEmail(email)).thenReturn(Arrays.asList(passwordReset));

        boolean result = passwordResetService.verifyResetCode(email, "wrongcode");

        assertFalse(result);
    }

    @Test
    public void testUpdatePacientPassword() throws Exception {
        String email = "test@example.com";
        String newPassword = "newpassword";
        Pacient pacient = new Pacient();
        pacient.setEmail(email);

        when(pacientRepository.findByEmail(email)).thenReturn(Optional.of(pacient));

        passwordResetService.updatePacientPassword(email, newPassword);

        assertEquals(newPassword, pacient.getPassword());
        verify(pacientRepository).save(pacient);
    }

    @Test
    public void testUpdateAdminPassword() throws Exception {
        String email = "test@example.com";
        String newPassword = "newpassword";
        Admin admin = new Admin();
        admin.setEmail(email);

        when(adminRepository.findByEmail(email)).thenReturn(Optional.of(admin));

        passwordResetService.updateAdminPassword(email, newPassword);

        assertEquals(newPassword, admin.getPassword());
        verify(adminRepository).save(admin);
    }
}
