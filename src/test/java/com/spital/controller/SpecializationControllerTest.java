package com.spital.controller;

import com.spital.DTO.SpecializationDTO;
import com.spital.DTO.UserDetails;
import com.spital.service.SpecializationService;
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

class SpecializationControllerTest {

    @Mock
    private SpecializationService service;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private SpecializationController specializationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(specializationController).build();
    }

    @Test
    void getAllSpecializations_ShouldReturnSpecializationsView_WhenUserIsAdmin() throws Exception {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("admin@example.com");
        userDetails.setUserType("admin");

        when(session.getAttribute("userDetails")).thenReturn(userDetails);
        when(service.getAllSpecializations()).thenReturn(List.of(new SpecializationDTO()));

        mockMvc.perform(get("/specializations").sessionAttr("userDetails", userDetails))
                .andExpect(status().isOk())
                .andExpect(view().name("specializations"));
    }

    @Test
    void getAllSpecializations_ShouldRedirectToStartPage_WhenUserIsNotAdmin() throws Exception {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("user@example.com");
        userDetails.setUserType("pacient");

        when(session.getAttribute("userDetails")).thenReturn(userDetails);

        mockMvc.perform(get("/specializations").sessionAttr("userDetails", userDetails))
                .andExpect(status().isOk())
                .andExpect(view().name("startPage"));
    }

    @Test
    void addSpecialization_ShouldReturnAddSpecializationView_WhenBindingResultHasErrors() {
        SpecializationDTO specialization = new SpecializationDTO();
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = specializationController.addSpecialization(specialization, bindingResult, model);

        assertEquals("addSpecialization", result);
    }

    @Test
    void addSpecialization_ShouldReturnAddSpecializationView_WhenFieldsAreEmpty() {
        SpecializationDTO specialization = new SpecializationDTO();
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = specializationController.addSpecialization(specialization, bindingResult, model);

        assertEquals("addSpecialization", result);
    }

    @Test
    void addSpecialization_ShouldRedirectToSpecializationsView_WhenSpecializationIsValid() {
        SpecializationDTO specialization = new SpecializationDTO();
        specialization.setSpecializationName("Cardiology");
        specialization.setMedic("Dr. Smith");
        specialization.setRoom("101");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(service.addSpecialization(specialization)).thenReturn(specialization);

        String result = specializationController.addSpecialization(specialization, bindingResult, model);

        assertEquals("redirect:/specializations", result);
    }

    @Test
    void showAddSpecialization_ShouldReturnAddSpecializationView_WhenUserIsAdmin() throws Exception {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("admin@example.com");
        userDetails.setUserType("admin");

        when(session.getAttribute("userDetails")).thenReturn(userDetails);

        mockMvc.perform(get("/showAddSpecialization").sessionAttr("userDetails", userDetails))
                .andExpect(status().isOk())
                .andExpect(view().name("addSpecialization"));
    }

    @Test
    void showAddSpecialization_ShouldRedirectToStartPage_WhenUserIsNotAdmin() throws Exception {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("user@example.com");
        userDetails.setUserType("pacient");

        when(session.getAttribute("userDetails")).thenReturn(userDetails);

        mockMvc.perform(get("/showAddSpecialization").sessionAttr("userDetails", userDetails))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void deleteSpecialization_ShouldRedirectToSpecializationsView() throws Exception {
        mockMvc.perform(post("/deleteSpecialization").param("specializationID", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/specializations"));

        verify(service).deleteSpecialization(1);
    }

    @Test
    void editSpecialization_ShouldRedirectToSpecializationsView() throws Exception {
        SpecializationDTO specializationDTO = new SpecializationDTO();

        mockMvc.perform(post("/editSpecialization").param("id", "1").flashAttr("specialization", specializationDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/specializations"));

        verify(service).editSpecialization(1, specializationDTO);
    }

    @Test
    void showEditSpecializationPage_ShouldReturnEditSpecializationView_WhenSpecializationExistsAndUserIsAdmin() throws Exception {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("admin@example.com");
        userDetails.setUserType("admin");

        SpecializationDTO specializationDTO = new SpecializationDTO();

        when(session.getAttribute("userDetails")).thenReturn(userDetails);
        when(service.getSpecializationById(1)).thenReturn(Optional.of(specializationDTO));

        mockMvc.perform(get("/showEditSpecialization").sessionAttr("userDetails", userDetails).param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editSpecialization"));
    }

    @Test
    void showEditSpecializationPage_ShouldRedirectToSpecializationsView_WhenSpecializationDoesNotExist() throws Exception {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("admin@example.com");
        userDetails.setUserType("admin");

        when(session.getAttribute("userDetails")).thenReturn(userDetails);
        when(service.getSpecializationById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/showEditSpecialization").sessionAttr("userDetails", userDetails).param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/specializations"));
    }
}
