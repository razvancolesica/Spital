package com.spital.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class EmailServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendResetCodeEmail() {
        String to = "test@example.com";
        String code = "123456";
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        emailService.sendResetCodeEmail(to, code);

        verify(emailSender).send(captor.capture());
        SimpleMailMessage sentMessage = captor.getValue();
        assertEquals(to, sentMessage.getTo()[0]);
        assertEquals("Reset your password", sentMessage.getSubject());
        assertEquals("Your reset code is: " + code, sentMessage.getText());
    }

    @Test
    public void testSendValidateEmail() {
        String to = "test@example.com";
        String code = "123456";
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        emailService.sendValidateEmail(to, code);

        verify(emailSender).send(captor.capture());
        SimpleMailMessage sentMessage = captor.getValue();
        assertEquals(to, sentMessage.getTo()[0]);
        assertEquals("Validate your account", sentMessage.getSubject());
        assertEquals("Your validation code is: " + code, sentMessage.getText());
    }

    @Test
    public void testSendReservationDoneEmail() {
        String to = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        LocalDateTime date = LocalDateTime.of(2023, 5, 23, 10, 0);
        String specialization = "Dermatology";
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        emailService.sendReservationDoneEmail(to, firstName, lastName, date, specialization);

        verify(emailSender).send(captor.capture());
        SimpleMailMessage sentMessage = captor.getValue();
        assertEquals(to, sentMessage.getTo()[0]);
        assertEquals("Reservation submitted successfully", sentMessage.getSubject());
        assertEquals("Dear " + firstName + " " + lastName + ", your reservation for " + date + " at " + specialization + " has been done.", sentMessage.getText());
    }
}
