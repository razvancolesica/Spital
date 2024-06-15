package com.spital.service;

import com.spital.DTO.AdminDTO;
import com.spital.entity.Admin;
import com.spital.repository.AdminRepository;
import com.spital.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import java.time.Duration;
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

    @Nested
    class AdminServicePerformanceTest {

        @Mock
        private AdminRepository adminRepository;

        @InjectMocks
        private AdminService adminService;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testGetAdminByEmail_Performance() {
            String email = "admin@example.com";
            Admin admin = new Admin();
            admin.setAdminID(1);
            admin.setEmail(email);

            when(adminRepository.findByEmail(email)).thenReturn(Optional.of(admin));

            // Setam un timp maxim de executie pentru metoda testata
            assertTimeout(Duration.ofMillis(100), () -> {
                long startTime = System.nanoTime();

                AdminDTO adminDTO = adminService.getAdminByEmail(email);

                long endTime = System.nanoTime();
                long durationInMillis = (endTime - startTime) / 1_000_000;

                assertEquals(1, adminDTO.getAdminID());
                assertEquals(email, adminDTO.getEmail());

                // Mesaj în consolă cu timpul de execuție
                System.out.println("Metoda getAdminByEmail a fost executată în " + durationInMillis + " milisecunde.");
            });
        }
    }

}
