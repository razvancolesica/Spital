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
    public List<Reservation> getReservation(String reservationID) {
        log.info("SpitalController.getReservation() has started...");
        return service.getReservation(reservationID);
    }


    //Afiseaza rezervarea dupa ID-ul pacientului.
    @GetMapping("/getReservationForPacient")
    public List<Reservation> getReservationForPacient(String pacientID) {
        log.info("SpitalController.getReservationForPacient() has started...");
        return service.getReservationForPacient(pacientID);
    }


    //Adaugare pacient
    @PostMapping("/addPacient")
    public Pacient addPacient(Pacient pacient){
        return service.addPacient(pacient);
    }


    //Adaugare rezervare
   @PostMapping("/addReservation")
    public Reservation addReservation(Reservation reservation){
       return service.addReservation(reservation);
    }


    //Sterge rezervare
    @DeleteMapping("/deleteReservation")
    public void deleteReservation(String reservationID) {
        service.deleteReservation(reservationID);
    }


    //Sterge pacient
    @DeleteMapping("/deletePacient")
    public void deletePacient(String pacientID) {
        service.deletePacient(pacientID);
    }


    //Editeaza pacient
    @PostMapping("/editPacient")
    public void editPacient(String pacientID,Pacient pacient) {
        service.editPacient(pacientID,pacient);
    }


    //Editeaza rezervare
    @PostMapping("/editReservation")
    public void editReservation(String reservationID,Reservation reservation) {
        service.editReservation(reservationID,reservation);
    }
}
