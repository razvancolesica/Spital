package com.spital.controller;
import com.spital.DTO.ReservationDTO;
import com.spital.service.PacientService;
import com.spital.service.ReservationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Log4j2
@Controller
public class ReservationController {

    @Autowired
    private ReservationService service;
    @Autowired
    private PacientService pacientService;

    //Afiseaza toate rezervarile.
    @GetMapping("/reservations")
    public ModelAndView getAllReservations() {
        log.info("ReservationController.getAllReservations() has started...");
        List<ReservationDTO> result = service.getAllReservations();
        ModelAndView modelAndView = new ModelAndView("reservations");
        modelAndView.addObject("reservations", result);
        log.info("ReservationController.getAllReservations() has finished.");
        return modelAndView;
    }

   /* //Afiseaza rezervarea dupa ID-ul rezervarii.
    @GetMapping("/getReservation")
    public List<Reservation> getReservation(@RequestParam String reservationID) {
        log.info("ReservationController.getReservation() has started...");
        return service.getReservation(reservationID);
    }

    //Afiseaza rezervarea dupa ID-ul pacientului.
    @GetMapping("/getReservationForPacient")
    public List<Reservation> getReservationForPacient(@RequestParam String pacientID) {
        log.info("ReservationController.getReservationForPacient() has started...");
        return service.getReservationForPacient(pacientID);
    }

    */

    //Adaugare rezervare
    @PostMapping("/addReservation")
    public String addReservation(@ModelAttribute("reservation") ReservationDTO reservation, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "addReservation"; // Rămâne pe pagina de adăugare a rezervării
        }

        if (reservation.getFirstName().isEmpty()) {
            bindingResult.rejectValue("firstName", "error.firstName", "First name cannot be empty");
        }

        if (reservation.getLastName().isEmpty()) {
            bindingResult.rejectValue("lastName", "error.lastName", "Last name cannot be empty");
        }

        if (reservation.getSpecialization().isEmpty()) {
            bindingResult.rejectValue("specialization", "error.specialization", "Specialization cannot be empty");
        }

        if(reservation.getReservationDate() == null){
            bindingResult.rejectValue("reservationDate", "error.reservationDate", "Reservation date cannot be empty");
        }

        if (reservation.getMedic().isEmpty()) {
            bindingResult.rejectValue("medic", "error.medic", "Medic cannot be empty");
        }

        ReservationDTO addedReservation = service.addReservation(reservation);
        if (addedReservation == null) {
            model.addAttribute("errorMessage", "This patient does not exist!");
            return "addReservation"; // Rămâne pe pagina de adăugare a rezervării
        }
        return "redirect:/reservations";
    }



    @GetMapping("/showAddReservation")
    public String showAddReservation(Model model){
        model.addAttribute("reservation", new ReservationDTO());
        return "addReservation";
    }


    // Sterge rezervare și reîncarcă pagina
    @PostMapping("/deleteReservation")
    public String deleteReservation(@RequestParam Integer id) {
        log.info("ReservationController.deleteReservation() has started...");
        service.deleteReservation(id);
        log.info("ReservationController.deleteReservation() has finished.");
        return "redirect:/reservations"; // Redirecționează către pagina de rezervări
    }



    //Editeaza rezervare
    @PostMapping("/editReservation")
    public String editReservation(@RequestParam Integer reservationID, @ModelAttribute("reservation") ReservationDTO updatedReservationDTO) {
        service.editReservation(reservationID, updatedReservationDTO);
        return "redirect:/reservations";
    }


    @GetMapping("/showEditReservation")
    public String showEditReservationPage(@RequestParam Integer id, Model model) {
        Optional<ReservationDTO> reservationDTOOptional = service.getReservationById(id);
        if (reservationDTOOptional.isPresent()) {
            model.addAttribute("reservation", reservationDTOOptional.get());
            return "editReservation";
        } else {
            return "redirect:/reservations";
        }
    }

}
