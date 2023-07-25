package com.ibmpractica.spital.service;

import com.ibmpractica.spital.DTO.AddPacientDTO;
import com.ibmpractica.spital.DTO.PacientDTO;
import com.ibmpractica.spital.DTO.ReservationDTO;
import com.ibmpractica.spital.entity.Pacient;
import com.ibmpractica.spital.entity.Reservation;
import com.ibmpractica.spital.repository.PacientRepository;
import com.ibmpractica.spital.repository.ReservationRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class SpitalService {

    @Autowired
    PacientRepository pacientRepository;
    @Autowired
    ReservationRepository reservationRepository;

    ModelMapper mapper = new ModelMapper();

    //Afiseaza toti pacientii.
    public List<PacientDTO> getAllPacients()
    {
        log.info("SpitalService.getAllPacients() retrieving all pacients...");
        return pacientRepository.findAll().stream()
                .map(pacient -> mapper.map(pacient,PacientDTO.class))
                .collect(Collectors.toList());
    }

    //Afiseaza toate rezervarile
    public List<ReservationDTO> getAllReservations()
    {
        log.info("SpitalService.getReservations() retrieving all reservations...");
        return reservationRepository.findAll().stream()
                .map(reservation -> mapper.map(reservation,ReservationDTO.class))
                .collect(Collectors.toList());
    }


    //Adauga rezervare.
    public Reservation addReservation(Reservation reservation)
    {
        reservationRepository.save(reservation);
        return reservation;
    }


    //Sterge rezervare
    public void deleteReservation(String reservationID) {
        reservationRepository.deleteById(reservationID);
    }


    //Editeaza rezervare
    public void editReservation(String reservationID, Reservation r)
    {
        List<Reservation> list = reservationRepository.findAll();
        for(Reservation reservation : list)
        {
            if(reservation.getId().equals(reservationID))
            {
                reservation.setReservationDate(r.getReservationDate());
                reservation.setSpecialization(r.getSpecialization());
                reservation.setMedic(r.getMedic());
            }
            reservationRepository.save(reservation);
        }
    }


    //Adauga pacient
    public Pacient addPacient(Pacient pacient)
    {
        pacientRepository.save(pacient);
        return pacient;
    }


    //Sterge pacient
    public void deletePacient(String pacientID)
    {
        pacientRepository.deleteById(pacientID);
    }


    //Editeaza pacient
    public void editPacient(String pacientID, Pacient p)
    {
        List<Pacient> list = pacientRepository.findAll();
        for(Pacient pacient : list)
        {
            if(pacient.getPacientID().equals(pacientID))
            {
                pacient.setFirstName(p.getFirstName());
                pacient.setLastName(p.getLastName());
                pacient.setAge(p.getAge());
                pacient.setCnp(p.getCnp());
                pacient.setIssue(p.getIssue());
            }
            pacientRepository.save(pacient);
        }
    }



    //Afiseaza rezervarea dupa ID-ul rezervarii.
    public List<Reservation> getReservation(String reservationID) {
        log.info("SpitalService.getReservation() retrieving all reservations...");
        return reservationRepository.findAll().stream()
                .filter(r -> r.getId().equals(reservationID)).collect(Collectors.toList());
    }


    //Afiseaza rezervarea dupa ID-ul pacientului.
    public List<Reservation> getReservationForPacient(String pacientID) {
        log.info("SpitalService.getReservations() retrieving all reservations...");
        return reservationRepository.findAll().stream()
                .filter(r -> r.getPacientID().equals(pacientID)).collect(Collectors.toList());
    }

}
