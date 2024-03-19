package com.ibmpractica.spital.controller;
import com.ibmpractica.spital.DTO.PacientDTO;
import com.ibmpractica.spital.DTO.ReservationDTO;
import com.ibmpractica.spital.entity.Pacient;
import com.ibmpractica.spital.entity.Reservation;
import com.ibmpractica.spital.service.SpitalService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Log4j2
@Controller
public class SpitalController {

    @Autowired
    private SpitalService service;

    //Afiseaza toate rezervarile.
    @GetMapping("/reservations")
    public ModelAndView getAllReservations() {
        log.info("SpitalController.getAllReservations() has started...");
        List<ReservationDTO> result = service.getAllReservations();
        ModelAndView modelAndView = new ModelAndView("reservations");
        modelAndView.addObject("reservations", result);
        log.info("SpitalController.getAllReservations() has finished.");
        return modelAndView;
    }

    //Afiseaza rezervarea dupa ID-ul rezervarii.
    @GetMapping("/getReservation")
    public List<Reservation> getReservation(@RequestParam String reservationID) {
        log.info("SpitalController.getReservation() has started...");
        return service.getReservation(reservationID);
    }

    //Afiseaza rezervarea dupa ID-ul pacientului.
    @GetMapping("/getReservationForPacient")
    public List<Reservation> getReservationForPacient(@RequestParam String pacientID) {
        log.info("SpitalController.getReservationForPacient() has started...");
        return service.getReservationForPacient(pacientID);
    }

    //Adaugare rezervare
    @PostMapping("/addReservation")
    public String addReservation(@ModelAttribute("reservation") ReservationDTO reservation){
        service.addReservation(reservation);
        return "redirect:/reservations";
    }

    @GetMapping("/showAddReservation")
    public String showAddReservation(Model model){
        model.addAttribute("reservation", new ReservationDTO());
        return "addReservation";
    }


    // Sterge rezervare și reîncarcă pagina
    @PostMapping("/deleteReservation")
    public String deleteReservation(@RequestParam String id) {
        log.info("SpitalController.deleteReservation() has started...");
        service.deleteReservation(id);
        log.info("SpitalController.deleteReservation() has finished.");
        return "redirect:/reservations"; // Redirecționează către pagina de rezervări
    }



    //Editeaza rezervare
    @PostMapping("/editReservation")
    public ResponseEntity<String> editReservation(@RequestParam String reservationID, @RequestBody Reservation reservation) {
        log.info("SpitalController.editReservation() has started...");
        service.editReservation(reservationID, reservation);
        log.info("SpitalController.editReservation() has finished.");
        return new ResponseEntity<>("Reservation edited successfully", HttpStatus.OK);
    }



    @GetMapping("/about")
    public String getAbout(){
        return "about";
    }



    //Afiseaza toti pacientii.
    @GetMapping("/getAllPacients")
    public ModelAndView getAllPacients() {
        log.info("SpitalController.getAllPacients() has started...");
        List<PacientDTO> pacients = service.getAllPacients();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("pacients", pacients);
        log.info("SpitalController.getAllPacients() has finished.");
        return modelAndView;
    }

    //Adaugare pacient
    @PostMapping("/addPacient")
    public Pacient addPacient(@RequestBody Pacient pacient){
        return service.addPacient(pacient);
    }

    //Sterge pacient
    @DeleteMapping("/deletePacient")
    public void deletePacient(@RequestParam String pacientID) {
        service.deletePacient(pacientID);
    }


    //Editeaza pacient
    @PostMapping("/editPacient")
    public void editPacient(@RequestParam String id,@RequestBody Pacient pacient) {
        service.editPacient(id,pacient);
    }

}
