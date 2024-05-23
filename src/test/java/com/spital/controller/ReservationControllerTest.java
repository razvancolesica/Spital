package com.spital.controller;

import com.spital.DTO.ReservationDTO;
import com.spital.DTO.UserDetails;
import com.spital.service.PacientService;
import com.spital.service.ReservationService;
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

class ReservationControllerTest {

    @Mock
    private ReservationService service;

    @Mock
    private PacientService pacientService;

    @Mock
    private SpecializationService specializationService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ReservationController reservationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
    }

    @Test
    void getAllReservations_ShouldRedirectToStartPage_WhenUserIsNotAdmin() throws Exception {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("user@example.com");
        userDetails.setUserType("pacient");

        when(session.getAttribute("userDetails")).thenReturn(userDetails);

        mockMvc.perform(get("/reservations").sessionAttr("userDetails", userDetails))
                .andExpect(status().isOk())
                .andExpect(view().name("startPage"));
    }

    @Test
    void addReservation_ShouldReturnAddReservationView_WhenBindingResultHasErrors() throws Exception {
        ReservationDTO reservation = new ReservationDTO();

        when(bindingResult.hasErrors()).thenReturn(true);

        String result = reservationController.addReservation(reservation, bindingResult, model);

        assertEquals("addReservation", result);
    }

    @Test
    void showAddReservation_ShouldReturnAddReservationView_WhenUserIsAdmin() throws Exception {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("admin@example.com");
        userDetails.setUserType("admin");

        when(session.getAttribute("userDetails")).thenReturn(userDetails);

        mockMvc.perform(get("/showAddReservation").sessionAttr("userDetails", userDetails))
                .andExpect(status().isOk())
                .andExpect(view().name("addReservation"));
    }

    @Test
    void showAddReservation_ShouldRedirectToStartPage_WhenUserIsNotAdmin() throws Exception {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("user@example.com");
        userDetails.setUserType("pacient");

        when(session.getAttribute("userDetails")).thenReturn(userDetails);

        mockMvc.perform(get("/showAddReservation").sessionAttr("userDetails", userDetails))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void deleteReservation_ShouldRedirectToReservationsView() throws Exception {
        mockMvc.perform(post("/deleteReservation").param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/reservations"));

        verify(service).deleteReservation(1);
    }

    @Test
    void editReservation_ShouldRedirectToReservationsView() throws Exception {
        ReservationDTO reservationDTO = new ReservationDTO();

        mockMvc.perform(post("/editReservation").param("reservationID", "1").flashAttr("reservation", reservationDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/reservations"));

        verify(service).editReservation(1, reservationDTO);
    }

    @Test
    void showEditReservationPage_ShouldReturnEditReservationView_WhenReservationExistsAndUserIsAdmin() throws Exception {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("admin@example.com");
        userDetails.setUserType("admin");

        ReservationDTO reservationDTO = new ReservationDTO();

        when(session.getAttribute("userDetails")).thenReturn(userDetails);
        when(service.getReservationById(1)).thenReturn(Optional.of(reservationDTO));

        mockMvc.perform(get("/showEditReservation").sessionAttr("userDetails", userDetails).param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editReservation"));
    }

    @Test
    void showEditReservationPage_ShouldRedirectToReservationsView_WhenReservationDoesNotExist() throws Exception {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("admin@example.com");
        userDetails.setUserType("admin");

        when(session.getAttribute("userDetails")).thenReturn(userDetails);
        when(service.getReservationById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/showEditReservation").sessionAttr("userDetails", userDetails).param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/reservations"));
    }
}
