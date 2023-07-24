package com.ibmpractica.spital.service;

import com.ibmpractica.spital.DTO.Pacient;
import com.ibmpractica.spital.DTO.Reservation;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class SpitalService {

    //Afiseaza toti pacientii.
    public List<Pacient> getAllPacients() {
        log.info("SpitalService.getAllPacients() retrieving all pacients...");
        Pacient p = new Pacient();
        p.setPacientID("0001");
        p.setFirstName("Razvan");
        p.setLastName("Colesica");
        p.setAge(21);
        p.setIssue("Fractura de glezna");
        Pacient p1 = new Pacient();
        p1.setPacientID("0002");
        p1.setFirstName("Paula");
        p1.setLastName("Preda");
        p1.setAge(20);
        p1.setIssue("Pneumonie");
        return List.of(p, p1);
    }


    //Afiseaza toate rezervarile.
    public List<Reservation> getReservations() {
        log.info("SpitalService.getReservations() retrieving all reservations...");
        Reservation p = new Reservation();
        p.setId("01");
        p.setPacientID("0001");
        p.setReservationDate(LocalDateTime.of(2023, 8, 12, 10, 30));
        p.setSpecialization("Ortopedie");
        Reservation p1 = new Reservation();
        p1.setId("02");
        p1.setPacientID("0002");
        p1.setReservationDate(LocalDateTime.of(2023, 8, 12, 12, 45));
        p1.setSpecialization("Pneumologie");
        return List.of(p, p1);
    }


    //Afiseaza rezervarea dupa ID-ul rezervarii.
    public List<Reservation> getReservation(String reservationID) {
        log.info("SpitalService.getReservation() retrieving all reservations...");
        Reservation p = new Reservation();
        p.setId("01");
        p.setPacientID("0001");
        p.setReservationDate(LocalDateTime.of(2023, 8, 12, 10, 30));
        p.setSpecialization("Ortopedie");
        Reservation p1 = new Reservation();
        p1.setId("02");
        p1.setPacientID("0002");
        p1.setReservationDate(LocalDateTime.of(2023, 8, 12, 12, 45));
        p1.setSpecialization("Pneumologie");
        List<Reservation> list = new ArrayList<>();
        list.add(p);
        list.add(p1);
        return list.stream().filter(r -> r.getId().equals(reservationID)).collect(Collectors.toList());
    }


    //Afiseaza rezervarea dupa ID-ul pacientului.
    public List<Reservation> getReservationForPacient(String pacientID) {
        log.info("SpitalService.getReservations() retrieving all reservations...");
        Reservation p1 = new Reservation();
        p1.setId("01");
        p1.setPacientID("0001");
        p1.setReservationDate(LocalDateTime.of(2023, 8, 12, 10, 30));
        p1.setSpecialization("Ortopedie");
        Reservation p2 = new Reservation();
        p2.setId("03");
        p2.setPacientID("0001");
        p2.setReservationDate(LocalDateTime.of(2023, 6, 27, 15, 00));
        p2.setSpecialization("Cardiologie");
        Reservation p3 = new Reservation();
        p3.setId("02");
        p3.setPacientID("0002");
        p3.setReservationDate(LocalDateTime.of(2023, 8, 12, 12, 45));
        p3.setSpecialization("Pneumologie");
        List<Reservation> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);

        return list.stream().filter(r -> r.getPacientID().equals(pacientID)).collect(Collectors.toList());
    }

   /* public boolean addReservation(AddReservation reservation){
        return true;
    }
    */

    public boolean deleteReservation(String reservationID) {
        return false;
    }
}
