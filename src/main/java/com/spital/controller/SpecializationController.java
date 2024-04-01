package com.spital.controller;

import com.spital.DTO.PacientDTO;
import com.spital.DTO.SpecializationDTO;
import com.spital.service.SpecializationService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
public class SpecializationController {

    @Autowired
    private SpecializationService service;

    @GetMapping("/specializations")
    public ModelAndView getAllSpecializations() {
        log.info("SpecializationController.getAllSpecializations() has started...");
        List<SpecializationDTO> specializations = service.getAllSpecializations();
        ModelAndView modelAndView = new ModelAndView("specializations");
        modelAndView.addObject("specializations", specializations);
        log.info("SpecializationController.getAllSpecializations() has finished.");
        return modelAndView;
    }

    @PostMapping("/addSpecialization")
    public String addSpecialization(@Valid @ModelAttribute("specialization") SpecializationDTO specialization, BindingResult bindingResult, Model model){
        if(specialization.getSpecializationName().isEmpty()){
            bindingResult.rejectValue("specializationName", "error.specializationName", "Specialization name cannot be empty");
        }

        if(specialization.getMedic().isEmpty()){
            bindingResult.rejectValue("medic", "error.medic", "Medic cannot be empty");
        }

        if(specialization.getRoom().isEmpty()){
            bindingResult.rejectValue("room", "error.room", "Room cannot be empty");
        }

        if(bindingResult.hasErrors()){
            return "addSpecialization";
        }

        SpecializationDTO addedSpecialization = service.addSpecialization(specialization);
        if (addedSpecialization == null) {
            model.addAttribute("errorMessage", "Specializarea există deja!");
            return "addSpecialization";
        }
        return "redirect:/specializations";
    }

    @GetMapping("/showAddSpecialization")
    public String showAddSpecialization(Model model){
        model.addAttribute("specialization", new SpecializationDTO());
        return "addSpecialization";
    }

    @PostMapping("/editSpecialization")
    public String editSpecialization(@RequestParam Integer id, @ModelAttribute("specialization") SpecializationDTO updatedSpecializationDTO) {
        service.editSpecialization(id, updatedSpecializationDTO);
        return "redirect:/specializations";
    }

    @GetMapping("/showEditSpecialization")
    public String showEditSpecializationPage(@RequestParam Integer id, Model model) {
        Optional<SpecializationDTO> specializationDTOOptional = service.getSpecializationById(id);
        if (specializationDTOOptional.isPresent()) {
            model.addAttribute("specialization", specializationDTOOptional.get());
            return "editSpecialization";
        } else {
            // Tratează cazul în care specializarea nu este găsită
            return "redirect:/specializations";
        }
    }

    @PostMapping("/deleteSpecialization")
    public String deleteSpecialization(@RequestParam Integer specializationID) {
        log.info("SpecializationController.deleteSpecialization() has started...");
        service.deleteSpecialization(specializationID);
        log.info("SpecializationController.deleteSpecialization() has finished.");
        return "redirect:/specializations"; // Redirecționează către pagina de specializari
    }

}
