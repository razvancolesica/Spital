package com.ibmpractica.spital.service;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    PacientRepository pacientRepository;

    ModelMapper mapper = new ModelMapper();

    //Afiseaza toate rezervarile
    public List<ReservationDTO> getAllReservations()
    {
        log.info("ReservationService.getReservations() retrieving all reservations...");
        return reservationRepository.findAll().stream()
                .map(reservation -> mapper.map(reservation,ReservationDTO.class))
                .collect(Collectors.toList());
    }

    // Obține un pacient în funcție de nume pentru adaugarea rezervarii
    public PacientDTO getPacientByName(String firstName, String lastName) {
        log.info("ReservationService.getPacientByName(String firstName, String lastName) retrieving pacient by name...");
        Optional<Pacient> pacientOptional = pacientRepository.findByFirstNameAndLastName(firstName, lastName);
        return pacientOptional.map(p -> mapper.map(p, PacientDTO.class)).orElse(null);
    }

    // Adaugă rezervare
    public ReservationDTO addReservation(ReservationDTO reservationDTO) {
        log.info("ReservationService.addReservation(ReservationDTO reservationDTO) adding reservation...");

        // Căutăm pacientul în funcție de nume
        PacientDTO pacient = getPacientByName(reservationDTO.getFirstName(), reservationDTO.getLastName());

        if (pacient != null) {
            // Dacă pacientul există, setăm id-ul rezervării conform acestuia
            reservationDTO.setPacientID(pacient.getPacientID());
            Reservation reservation = mapper.map(reservationDTO, Reservation.class);
            reservationRepository.save(reservation);
            return mapper.map(reservation, ReservationDTO.class);
        } else {
            // Dacă pacientul nu există, afișăm un mesaj de eroare
            log.error("Pacient does not exist!");
            return null;
        }
    }






    //Sterge rezervare
    public void deleteReservation(Integer reservationID) {
        log.info("ReservationService.deleteReservation(String reservationID) deleting reservation...");
        reservationRepository.deleteById(String.valueOf(reservationID));
    }


    //Editeaza rezervare
    public void editReservation(String reservationID, Reservation r)
    {
        log.info("ReservationService.editReservation(String reservationID, Reservation r) editing reservation...");
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


    //Afiseaza rezervarea dupa ID-ul rezervarii.
    public List<Reservation> getReservation(String reservationID) {
        log.info("ReservationService.getReservation() retrieving all reservations...");
        return reservationRepository.findAll().stream()
                .filter(r -> r.getId().equals(reservationID)).collect(Collectors.toList());
    }


    //Afiseaza rezervarea dupa ID-ul pacientului.
    public List<Reservation> getReservationForPacient(String pacientID) {
        log.info("ReservationService.getReservations() retrieving all reservations...");
        return reservationRepository.findAll().stream()
                .filter(r -> r.getPacientID().equals(pacientID)).collect(Collectors.toList());
    }

}
