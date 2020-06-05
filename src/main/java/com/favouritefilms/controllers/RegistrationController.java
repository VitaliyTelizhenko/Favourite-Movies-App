package com.favouritefilms.controllers;


import com.favouritefilms.dto.UserDTO;
import com.favouritefilms.entities.User;
import com.favouritefilms.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {

    private  final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public RegistrationController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registerForm() {
        return "registration";
    }

    @PostMapping("/registration")
    public String processRegistration(@Valid UserDTO userDTO,
                                      BindingResult result,
                                      Model model
                                      ) {
        if (result.hasErrors()) {
            Map<String, String> errors = ControllerUtil.getErrors(result);
            model.mergeAttributes(errors);
            return "registration";
        }

        User newUser = userDTO.toUser(passwordEncoder);
        userService.save(newUser);

        return "redirect:/login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model)
    {
        model.addAttribute("loginError", "Wrong username or password");
        return "login";
    }
}

