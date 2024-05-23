package com.spital.controller;

import com.spital.DTO.EmailValidation;
import com.spital.DTO.PacientDTO;
import com.spital.DTO.UserDetails;
import com.spital.entity.Pacient;
import com.spital.mapper.PacientMapper;
import com.spital.service.PacientService;
import com.spital.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class PacientControllerTest {

    @Mock
    private PacientService service;

    @Mock
    private ReservationService reservationService;

    @Mock
    private PacientMapper pacientMapper;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private PacientController pacientController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pacientController).build();
    }

    @Test
    void getAllPacients_ShouldReturnPacientsView_WhenUserIsAdmin() throws Exception {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("admin@example.com");
        userDetails.setUserType("admin");

        when(session.getAttribute("userDetails")).thenReturn(userDetails);
        when(service.getAllPacients()).thenReturn(List.of(new PacientDTO()));

        mockMvc.perform(get("/pacients").sessionAttr("userDetails", userDetails))
                .andExpect(status().isOk())
                .andExpect(view().name("pacients"));
    }

    @Test
    void getAllPacients_ShouldRedirectToStartPage_WhenUserIsNotAdmin() throws Exception {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("user@example.com");
        userDetails.setUserType("pacient");

        when(session.getAttribute("userDetails")).thenReturn(userDetails);

        mockMvc.perform(get("/pacients").sessionAttr("userDetails", userDetails))
                .andExpect(status().isOk())
                .andExpect(view().name("startPage"));
    }

    @Test
    void addPacient_ShouldReturnAddPacientView_WhenBindingResultHasErrors() throws Exception {
        PacientDTO pacient = new PacientDTO();
        pacient.setFirstName("");
        pacient.setLastName("");
        pacient.setAge(0);
        pacient.setCnp("");
        pacient.setPhoneNumber("");

        when(bindingResult.hasErrors()).thenReturn(true);

        String result = pacientController.addPacient(pacient, bindingResult, model);

        verify(bindingResult).rejectValue("firstName", "error.firstName", "First name cannot be empty");
        verify(bindingResult).rejectValue("lastName", "error.lastName", "Last name cannot be empty");
        verify(bindingResult).rejectValue("age", "error.age", "Age cannot be empty");
        verify(bindingResult).rejectValue("cnp", "error.cnp", "CNP cannot be empty");
        verify(bindingResult).rejectValue("phoneNumber", "error.phoneNumber", "Phone number cannot be empty");

        assertEquals("addPacient", result);
    }

    @Test
    void showAddPacient_ShouldReturnAddPacientView_WhenUserIsAdmin() throws Exception {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("admin@example.com");
        userDetails.setUserType("admin");

        when(session.getAttribute("userDetails")).thenReturn(userDetails);

        mockMvc.perform(get("/showAddPacient").sessionAttr("userDetails", userDetails))
                .andExpect(status().isOk())
                .andExpect(view().name("addPacient"));
    }

    @Test
    void showAddPacient_ShouldRedirectToStartPage_WhenUserIsNotAdmin() throws Exception {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("user@example.com");
        userDetails.setUserType("pacient");

        when(session.getAttribute("userDetails")).thenReturn(userDetails);

        mockMvc.perform(get("/showAddPacient").sessionAttr("userDetails", userDetails))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void deletePacient_ShouldRedirectToPacientsView() throws Exception {
        mockMvc.perform(post("/deletePacient").param("pacientID", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/pacients"));

        verify(service).deletePacient(1);
    }

    @Test
    void editPacient_ShouldRedirectToPacientsView() throws Exception {
        PacientDTO pacientDTO = new PacientDTO();

        mockMvc.perform(post("/editPacient").param("id", "1").flashAttr("pacient", pacientDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/pacients"));

        verify(service).editPacient(1, pacientDTO);
    }

    @Test
    void showEditPacientPage_ShouldReturnEditPacientView_WhenPacientExistsAndUserIsAdmin() throws Exception {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("admin@example.com");
        userDetails.setUserType("admin");

        PacientDTO pacientDTO = new PacientDTO();

        when(session.getAttribute("userDetails")).thenReturn(userDetails);
        when(service.getPacientById(1)).thenReturn(Optional.of(pacientDTO));

        mockMvc.perform(get("/showEditPacient").sessionAttr("userDetails", userDetails).param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editPacient"));
    }

    @Test
    void showEditPacientPage_ShouldRedirectToPacientsView_WhenPacientDoesNotExist() throws Exception {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("admin@example.com");
        userDetails.setUserType("admin");

        when(session.getAttribute("userDetails")).thenReturn(userDetails);
        when(service.getPacientById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/showEditPacient").sessionAttr("userDetails", userDetails).param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/pacients"));
    }

    @Test
    void submitPacient_ShouldReturnPacientRegistrationFormView_WhenEmailAlreadyExists() throws Exception {
        PacientDTO pacientDTO = new PacientDTO();
        pacientDTO.setEmail("existing@example.com");

        when(service.emailExists("existing@example.com")).thenReturn(true);

        String result = pacientController.submitPacient(pacientDTO, model, session);

        verify(model).addAttribute("emailError2", "Email already exists.");
        assertEquals("pacientRegistrationForm", result);
    }

    @Test
    void pacientRegistrationForm_ShouldReturnStartPageView() throws Exception {
        mockMvc.perform(get("/pacientRegistrationForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("startPage"));
    }
}
