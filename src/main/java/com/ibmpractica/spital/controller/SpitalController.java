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
    public String deleteReservation(@RequestParam Integer id) {
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



    //Afiseaza toti pacientii.
    @GetMapping("/pacients")
    public ModelAndView getAllPacients() {
        log.info("SpitalController.getAllPacients() has started...");
        List<PacientDTO> pacients = service.getAllPacients();
        ModelAndView modelAndView = new ModelAndView("pacients");
        modelAndView.addObject("pacients", pacients);
        log.info("SpitalController.getAllPacients() has finished.");
        return modelAndView;
    }

    //Adaugare pacient
    @PostMapping("/addPacient")
    public String addPacient(@ModelAttribute("pacient") PacientDTO pacient){
        service.addPacient(pacient);
        return "redirect:/pacients";
    }

    @GetMapping("/showAddPacient")
    public String showAddPacient(Model model){
        model.addAttribute("pacient", new PacientDTO());
        return "addPacient";
    }


    //Sterge pacient
    @PostMapping("/deletePacient")
    public String deletePacient(@RequestParam Integer pacientID) {
        log.info("SpitalController.deletePacient() has started...");
        service.deletePacient(pacientID);
        log.info("SpitalController.deletePacient() has finished.");
        return "redirect:/pacients"; // Redirecționează către pagina de pacienti
    }


    //Editeaza pacient
    @PostMapping("/editPacient")
    public String editPacient(@RequestParam Integer id,@RequestBody PacientDTO pacient) {
        service.editPacient(id,pacient);
        return "redirect:/pacients";
    }

    @GetMapping("/showEditPacient")
    public String showEditPacientPage(@RequestParam Integer id, Model model) {
        // Obțineți pacientul cu id-ul specificat din baza de date
        Optional<PacientDTO> pacientOptional = service.getPacientById(id);

        // Verificați dacă pacientul există în baza de date
        // Dacă pacientul există, adăugați-l ca atribut la model
        // Returnați pagina de editare a pacientului
        pacientOptional.ifPresent(pacientDTO -> model.addAttribute("pacient", pacientDTO));
        return "editPacient";
    }


}
