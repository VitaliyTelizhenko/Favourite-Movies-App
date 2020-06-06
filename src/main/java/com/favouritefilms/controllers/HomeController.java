package com.favouritefilms.controllers;

import com.favouritefilms.entities.Movie;
import com.favouritefilms.entities.User;
import com.favouritefilms.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final MovieService movieService;

    @Autowired
    public HomeController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public String showDesignForm(Model model, @AuthenticationPrincipal User user) {
        List<Movie> movies = movieService.findUserMovies(user);

        model.addAttribute("movies", movies);
        return "home";
    }
}

