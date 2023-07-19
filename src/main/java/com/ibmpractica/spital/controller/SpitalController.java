package com.ibmpractica.spital.controller;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/spital")
public class SpitalController {
    @GetMapping("/all")
    public String getAllPacients()
    {
        log.info("getAllPacients has started...");
        return "Test";
    }

    @GetMapping("/reservations")
    public String getAllReservations()
    {
        return "Reservations";
    }

    @GetMapping("/reservation")
    public String getReservationForPacient()
    {
        return "Reservation for pacient";
    }

    @PostMapping("/addReservation")
    public String addReservation()
    {
        return "Reservation added";
    }

    @DeleteMapping("deleteReservation")
    public String deleteReservation()
    {
        return "Delete reservation";
    }

    @PostMapping("/addPacient")
    public String addPacient()
    {
        return "Pacient added";
    }

    @PostMapping("/editPacient")
    public String editPacient()
    {
        return "Pacient edited";
    }

}
