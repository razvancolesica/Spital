package com.spital.controller;
import com.spital.DTO.PacientDTO;
import com.spital.service.PacientService;
import com.spital.service.ReservationService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String addPacient(@Valid @ModelAttribute("pacient") PacientDTO pacient, BindingResult bindingResult, Model model){
        if(pacient.getFirstName().isEmpty()){
            bindingResult.rejectValue("firstName", "error.firstName", "First name cannot be empty");
        }

        if(pacient.getLastName().isEmpty()){
            bindingResult.rejectValue("lastName", "error.lastName", "Last name cannot be empty");
        }

        if(pacient.getAge() == 0){
            bindingResult.rejectValue("age", "error.age", "Age cannot be empty");
        }

        if(pacient.getCnp().isEmpty())
        {
            bindingResult.rejectValue("cnp", "error.cnp", "CNP cannot be empty");
        }

        if(pacient.getIssue().isEmpty())
        {
            bindingResult.rejectValue("issue", "error.issue", "Issue cannot be empty");
        }

        if(bindingResult.hasErrors()){
            return "addPacient";
        }

        PacientDTO addedPacient = service.addPacient(pacient);
        if (addedPacient == null) {
            model.addAttribute("errorMessage", "Pacientul există deja!");
            return "addPacient";
        }
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