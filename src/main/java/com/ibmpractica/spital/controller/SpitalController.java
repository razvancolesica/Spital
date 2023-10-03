package com.ibmpractica.spital.controller;

//import com.ibmpractica.spital.DTO.AddReservationDTO;

import com.ibmpractica.spital.DTO.AddPacientDTO;
import com.ibmpractica.spital.DTO.PacientDTO;
import com.ibmpractica.spital.DTO.ReservationDTO;
import com.ibmpractica.spital.entity.Pacient;
import com.ibmpractica.spital.entity.Reservation;
import com.ibmpractica.spital.service.SpitalService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/spital")
public class SpitalController {

    @Autowired
    private SpitalService service;

    //Afiseaza toti pacientii.
    @GetMapping("/getAllPacients")
    public List<PacientDTO> getAllPacients() {
        log.info("SpitalController.getAllPacients() has started...");
        List<PacientDTO> result = service.getAllPacients();
        log.info("SpitalController.getAllPacients() has finished.");
        return result;
    }


    //Afiseaza toate rezervarile.
    @GetMapping("/getAllReservations")
    public List<ReservationDTO> getAllReservations() {
        log.info("SpitalController.getAllReservations() has started...");
        List<ReservationDTO> result = service.getAllReservations();
        log.info("SpitalController.getAllReservations() has finished.");
        return result;
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


    //Adaugare pacient
    @PostMapping("/addPacient")
    public Pacient addPacient(@RequestBody Pacient pacient){
        return service.addPacient(pacient);
    }


    //Adaugare rezervare
   @PostMapping("/addReservation")
    public Reservation addReservation(@RequestBody Reservation reservation){
       return service.addReservation(reservation);
    }


    //Sterge rezervare
    @DeleteMapping("/deleteReservation")
    public void deleteReservation(@RequestParam String reservationID) {
        service.deleteReservation(reservationID);
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


    //Editeaza rezervare
    @PostMapping("/editReservation")
    public void editReservation(@RequestParam String reservationID,@RequestBody Reservation reservation) {
        service.editReservation(reservationID,reservation);
    }
}
