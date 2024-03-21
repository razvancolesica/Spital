package com.ibmpractica.spital.controller;
import com.ibmpractica.spital.DTO.PacientDTO;
import com.ibmpractica.spital.service.PacientService;
import com.ibmpractica.spital.service.ReservationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Log4j2
@Controller
public class PacientController {

    @Autowired
    private PacientService service;
    @Autowired
    private ReservationService reservationService;


    //Afiseaza toti pacientii.
    @GetMapping("/pacients")
    public ModelAndView getAllPacients() {
        log.info("ReservationController.getAllPacients() has started...");
        List<PacientDTO> pacients = service.getAllPacients();
        ModelAndView modelAndView = new ModelAndView("pacients");
        modelAndView.addObject("pacients", pacients);
        log.info("ReservationController.getAllPacients() has finished.");
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
        log.info("ReservationController.deletePacient() has started...");
        service.deletePacient(pacientID);
        log.info("ReservationController.deletePacient() has finished.");
        return "redirect:/pacients"; // Redirecționează către pagina de pacienti
    }


    // Editează pacient
    @PostMapping("/editPacient")
    public String editPacient(@RequestParam Integer id, @ModelAttribute("pacient") PacientDTO updatedPacientDTO) {
        service.editPacient(id, updatedPacientDTO);
        return "redirect:/pacients";
    }


    @GetMapping("/showEditPacient")
    public String showEditPacientPage(@RequestParam Integer id, Model model) {
        Optional<PacientDTO> pacientDTOOptional = service.getPacientById(id);
        if (pacientDTOOptional.isPresent()) {
            model.addAttribute("pacient", pacientDTOOptional.get());
            return "editPacient";
        } else {
            // Tratează cazul în care pacientul nu este găsit
            return "redirect:/pacients";
        }
    }



}
