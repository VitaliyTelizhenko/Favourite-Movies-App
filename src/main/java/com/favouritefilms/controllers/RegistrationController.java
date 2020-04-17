package com.favouritefilms.controllers;


import com.favouritefilms.dao.UserRepo;
import com.favouritefilms.models.UserModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.Binding;
import javax.validation.Valid;

@Controller
public class RegistrationController {
    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registerform")
    public String registerForm(Model model) {
        model.addAttribute("usermodel", new UserModel());
        return "registration";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model)
    {
        model.addAttribute("loginError", true);
        return "login";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("usermodel") @Valid UserModel userModel, BindingResult result) {
        if (result.hasFieldErrors()) {
            return "registration";
        }
        userRepo.save(userModel.toUser(passwordEncoder));
        return "redirect:/login";
    }
}

