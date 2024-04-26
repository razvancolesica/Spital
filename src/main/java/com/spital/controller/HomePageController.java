package com.spital.controller;

import com.spital.DTO.*;
import com.spital.service.AdminService;
import com.spital.service.PacientService;
import com.spital.service.PasswordResetService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.NoSuchAlgorithmException;

@Controller

public class HomePageController {

    @Autowired
    PacientService pacientService;
    @Autowired
    AdminService adminService;
    @Autowired
    private PasswordResetService passwordResetService;

    PasswordResetDTO passwordResetDTO = new PasswordResetDTO();

    @GetMapping("/")
    public String startPage(){
        return "startPage";
    }

    @GetMapping("/homePagePacient")
    public String homePagePacient(Model model, HttpSession session) {
        UserDetails userDetails = (UserDetails) session.getAttribute("userDetails");
        PacientDTO pacient = pacientService.getPacientByEmail(userDetails.getEmail());
        if(!userDetails.getUserType().equals("pacient"))
        {
            return "redirect:/";
        }
        model.addAttribute("pacient", pacient);
        return "homePagePacient";
    }

    @GetMapping("/homePageAdmin")
    public String homePageAdmin(Model model, HttpSession session){
        UserDetails userDetails = (UserDetails) session.getAttribute("userDetails");
        AdminDTO admin = adminService.getAdminByEmail(userDetails.getEmail());
        if(!userDetails.getUserType().equals("admin"))
        {
            return "redirect:/";
        }
        model.addAttribute("admin", admin);
        return "homePageAdmin";
    }

    @GetMapping("/validateAccount")
    public String validateAccount(HttpSession session, Model model) {
        EmailValidation emailValidation = (EmailValidation) session.getAttribute("emailValidation");
        model.addAttribute("emailValidation", emailValidation.getEmail());
        return "validateAccount";
    }
    @PostMapping("/validateAccountForm")
    public String validateAccountForm(@RequestParam("email") String email, String code) {
        if (passwordResetService.verifyResetCode(email, code)) {
            return "redirect:/";
        } else {
            return "redirect:/validateAccount";
        }
    }
    @PostMapping("/requestValidateAccount")
    public String requestValidateAccount(@RequestParam("emailValidation")String email) {
        passwordResetService.generateAndSaveResetCodeValidation(email);
        return "redirect:/validateAccount";
    }

    @GetMapping("/showResetPasswordForm")
    public String showResetPasswordForm(Model model, HttpSession session) {
        PasswordResetDTO passwordResetDTO = (PasswordResetDTO) session.getAttribute("forgotPasswordSentEmail");
        model.addAttribute("forgotPasswordSentEmail", passwordResetDTO.getEmail());
        return "showResetPasswordForm";
    }
    @PostMapping("/requestResetPassword")
    public String requestResetPassword(@RequestParam("emailReset") String email, HttpSession session) {
        passwordResetService.generateAndSaveResetCode(email);
        passwordResetDTO.setEmail(email);
        session.setAttribute("forgotPasswordSentEmail", passwordResetDTO);
        return "redirect:/showResetPasswordForm";
    }
    @PostMapping("/resetPassword")
    public String resetPassword(String email, String resetCode, String newPassword) throws NoSuchAlgorithmException {
        if (passwordResetService.verifyResetCode(email, resetCode)) {
            passwordResetService.updatePacientPassword(email, newPassword);
            return "redirect:/";
        } else {
            return "redirect:/showResetPasswordForm";
        }
    }

}
