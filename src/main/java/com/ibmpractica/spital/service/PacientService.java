package com.ibmpractica.spital.service;
import com.ibmpractica.spital.DTO.PacientDTO;
import com.ibmpractica.spital.DTO.ReservationDTO;
import com.ibmpractica.spital.entity.Pacient;
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
public class PacientService {

    @Autowired
    PacientRepository pacientRepository;
    @Autowired
    ReservationService reservationService;


    ModelMapper mapper = new ModelMapper();



    //Afiseaza toti pacientii.
    public List<PacientDTO> getAllPacients()
    {
        log.info("ReservationService.getAllPacients() retrieving all pacients...");
        return pacientRepository.findAll().stream()
                .map(pacient -> mapper.map(pacient,PacientDTO.class))
                .collect(Collectors.toList());
    }


    //Adauga pacient
    public PacientDTO addPacient(PacientDTO pacientDTO)
    {
        log.info("ReservationService.addPacient(Pacient pacient) adding pacient...");
        Pacient pacient = mapper.map(pacientDTO, Pacient.class);
        pacientRepository.save(pacient);
        return mapper.map(pacient, PacientDTO.class);
    }


    //Sterge pacient
    public void deletePacient(Integer pacientID)
    {
        log.info("ReservationService.deletePacient(String pacientID) deleting pacient...");
        pacientRepository.deleteById(String.valueOf(pacientID));
        List<ReservationDTO> reservationDTO = reservationService.getAllReservations();
        for(ReservationDTO reservation : reservationDTO)
        {
            if(reservation.getPacientID().equals(pacientID))
            {
                reservationService.deleteReservation(reservation.getId());
            }
        }
    }


    //Editeaza pacient
    public PacientDTO editPacient(Integer pacientID, PacientDTO updatedPacientDTO) {
        log.info("ReservationService.editPacient(Integer pacientID, PacientDTO updatedPacientDTO) editing pacient...");

        Optional<Pacient> pacientOptional = pacientRepository.findById(String.valueOf(pacientID));
        if (pacientOptional.isPresent()) {
            Pacient pacient = pacientOptional.get();

            // Actualizăm valorile pacientului cu cele primite
            pacient.setFirstName(updatedPacientDTO.getFirstName());
            pacient.setLastName(updatedPacientDTO.getLastName());
            pacient.setCnp(updatedPacientDTO.getCnp());
            pacient.setAge(updatedPacientDTO.getAge());
            pacient.setIssue(updatedPacientDTO.getIssue());

            // Salvăm modificările în baza de date
            pacientRepository.save(pacient);

            return mapper.map(pacient, PacientDTO.class);
        } else {
            // În cazul în care pacientul nu a fost găsit, putem arunca o excepție sau puteți decide cum doriți să tratați această situație.
            log.error("Pacient with ID " + pacientID + " not found.");
            return null; // Sau aruncăm o excepție sau returnăm null sau un obiect special care indică lipsa pacientului.
        }
    }


    public Optional<PacientDTO> getPacientById(Integer pacientID) {
        log.info("ReservationService.getPacientById(Integer pacientID) retrieving pacient by ID...");
        Optional<Pacient> pacientOptional = pacientRepository.findById(String.valueOf(pacientID));
        return pacientOptional.map(p -> mapper.map(p, PacientDTO.class));
    }

}
