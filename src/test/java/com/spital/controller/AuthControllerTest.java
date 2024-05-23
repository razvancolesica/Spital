package com.spital.controller;

import com.spital.DTO.UserDetails;
import com.spital.entity.Admin;
import com.spital.entity.Pacient;
import com.spital.repository.AdminRepository;
import com.spital.repository.PacientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PacientRepository pacientRepository;

    @MockBean
    private AdminRepository adminRepository;

    @MockBean
    private HttpSession session;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testLoginWithValidPacientCredentials() throws Exception {
        Pacient pacient = new Pacient();
        pacient.setEmail("test@pacient.com");
        pacient.setPassword("Password1");

        Mockito.when(pacientRepository.findByEmail("test@pacient.com")).thenReturn(Optional.of(pacient));
        Mockito.when(adminRepository.findByEmail("test@pacient.com")).thenReturn(Optional.empty());

        mockMvc.perform(post("/login")
                        .param("email", "test@pacient.com")
                        .param("password", "Password1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/homePagePacient"));
    }

    @Test
    public void testLoginWithInvalidPacientCredentials() throws Exception {
        Pacient pacient = new Pacient();
        pacient.setEmail("test@pacient.com");
        pacient.setPassword("Password1");

        Mockito.when(pacientRepository.findByEmail("test@pacient.com")).thenReturn(Optional.of(pacient));

        mockMvc.perform(post("/login")
                        .param("email", "test@pacient.com")
                        .param("password", "WrongPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attribute("errorMessage", "Invalid pacient credentials."));
    }

    @Test
    public void testLoginWithValidAdminCredentials() throws Exception {
        Admin admin = new Admin();
        admin.setEmail("test@admin.com");
        admin.setPassword("Password1");

        Mockito.when(adminRepository.findByEmail("test@admin.com")).thenReturn(Optional.of(admin));
        Mockito.when(pacientRepository.findByEmail("test@admin.com")).thenReturn(Optional.empty());

        mockMvc.perform(post("/login")
                        .param("email", "test@admin.com")
                        .param("password", "Password1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/homePageAdmin"));
    }

    @Test
    public void testLoginWithInvalidAdminCredentials() throws Exception {
        Admin admin = new Admin();
        admin.setEmail("test@admin.com");
        admin.setPassword("Password1");

        Mockito.when(adminRepository.findByEmail("test@admin.com")).thenReturn(Optional.of(admin));

        mockMvc.perform(post("/login")
                        .param("email", "test@admin.com")
                        .param("password", "WrongPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attribute("errorMessage", "Invalid admin credentials."));
    }

    @Test
    public void testLoginWithInvalidEmailFormat() throws Exception {
        mockMvc.perform(post("/login")
                        .param("email", "invalid-email-format")
                        .param("password", "Password1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attribute("errorMessage", "Invalid email or password!"));
    }

    @Test
    public void testLoginWithEmptyCredentials() throws Exception {
        mockMvc.perform(post("/login")
                        .param("email", "")
                        .param("password", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attribute("errorMessage", "Email and password must not be empty."));
    }
}
