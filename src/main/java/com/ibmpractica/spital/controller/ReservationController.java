package com.ibmpractica.spital.controller;
import com.ibmpractica.spital.DTO.ReservationDTO;
import com.ibmpractica.spital.entity.Reservation;
import com.ibmpractica.spital.service.PacientService;
import com.ibmpractica.spital.service.ReservationService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

    //Afiseaza rezervarea dupa ID-ul rezervarii.
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

    //Adaugare rezervare
    @PostMapping("/addReservation")
    public String addReservation(@Valid @ModelAttribute("reservation") ReservationDTO reservation, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "addReservation"; // Rămâne pe pagina de adăugare a rezervării
        }

        ReservationDTO addedReservation = service.addReservation(reservation);
        if (addedReservation == null) {
            model.addAttribute("errorMessage", "This pacient does not exist!");
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
    public ResponseEntity<String> editReservation(@RequestParam String reservationID, @RequestBody Reservation reservation) {
        log.info("ReservationController.editReservation() has started...");
        service.editReservation(reservationID, reservation);
        log.info("ReservationController.editReservation() has finished.");
        return new ResponseEntity<>("Reservation edited successfully", HttpStatus.OK);
    }

}
