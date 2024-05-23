package com.spital.controller;

import com.spital.DTO.AdminDTO;
import com.spital.DTO.PacientDTO;
import com.spital.DTO.PasswordResetDTO;
import com.spital.DTO.UserDetails;
import com.spital.service.AdminService;
import com.spital.service.PacientService;
import com.spital.service.PasswordResetService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class HomePageControllerTest {

    @Mock
    private PacientService pacientService;

    @Mock
    private AdminService adminService;

    @Mock
    private PasswordResetService passwordResetService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private HomePageController homePageController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(homePageController).build();
    }

    @Test
    void startPage_ShouldReturnStartPageView() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("startPage"));
    }

    @Test
    void homePagePacient_ShouldRedirectToStartPage_WhenUserIsNotPacient() throws Exception {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("user@example.com");
        userDetails.setUserType("admin");

        when(session.getAttribute("userDetails")).thenReturn(userDetails);

        mockMvc.perform(get("/homePagePacient").sessionAttr("userDetails", userDetails))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }


    @Test
    void homePageAdmin_ShouldRedirectToStartPage_WhenUserIsNotAdmin() throws Exception {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("user@example.com");
        userDetails.setUserType("pacient");

        when(session.getAttribute("userDetails")).thenReturn(userDetails);

        mockMvc.perform(get("/homePageAdmin").sessionAttr("userDetails", userDetails))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

}
