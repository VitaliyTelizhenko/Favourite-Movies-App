package com.favouritefilms.controllers;

import com.favouritefilms.dao.MovieRepo;
import com.favouritefilms.entities.Movie;
import com.favouritefilms.entities.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class UserController {

    private MovieRepo movieRepo;

    public UserController(MovieRepo movieRepo) {
        this.movieRepo = movieRepo;
    }

    @GetMapping
    public String showDesignForm(Model model, @AuthenticationPrincipal User user) {
        List<Movie> movies = movieRepo.findAllByUserOrderByTitle(user);
        model.addAttribute("movies", movies);
        return "home";
    }
}

