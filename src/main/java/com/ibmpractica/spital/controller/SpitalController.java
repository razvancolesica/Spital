package com.ibmpractica.spital.controller;

//import com.ibmpractica.spital.DTO.AddReservation;

import com.ibmpractica.spital.DTO.Pacient;
import com.ibmpractica.spital.DTO.Reservation;
import com.ibmpractica.spital.service.SpitalService;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Array;
import java.util.ArrayList;
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
    public List<Pacient> getAllPacients() {
        log.info("SpitalController.getAllPacients() has started...");
        List<Pacient> result = service.getAllPacients();
        log.info("SpitalController.getAllPacients() has finished.");
        return result;
    }


    //Afiseaza toate rezervarile.
    @GetMapping("/getAllReservations")
    public List<Reservation> getAllReservations() {
        return service.getReservations();
    }


    //Afiseaza rezervarea dupa ID-ul rezervarii.
    @GetMapping("/getReservation")
    public ResponseEntity<List<Reservation>> getReservation(String reservationID) {

        if (service.getReservation(reservationID).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.of(Optional.ofNullable(service.getReservation(reservationID)));
    }


    //Afiseaza rezervarea dupa ID-ul pacientului.
    @GetMapping("/getReservationForPacient")
    public ResponseEntity<List<Reservation>> getReservationForPacient(String pacientID) {

        if (service.getReservationForPacient(pacientID).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.of(Optional.ofNullable(service.getReservationForPacient(pacientID)));
    }




  /*  @PostMapping("/addReservation")
    public ResponseEntity addReservation(AddReservation reservation){

        return service.addReservation(reservation) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

   */

    @PostMapping("/addPacient")
    public String addPacient() {
        return "Pacient added";
    }

    @DeleteMapping("/deleteReservation")
    public ResponseEntity deleteReservation(String reservationID) {

        if (service.deleteReservation(reservationID)) {
            ResponseEntity.ok().build();
        } else ResponseEntity.notFound().build();

        return service.deleteReservation(reservationID) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deletePacient")
    public String deletePacient() {
        return "Pacient deleted";
    }

    @PostMapping("/editPacient")
    public String editPacient() {
        return "Pacient edited";
    }
}
