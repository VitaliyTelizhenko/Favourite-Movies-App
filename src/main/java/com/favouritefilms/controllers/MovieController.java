package com.favouritefilms.controllers;

import com.favouritefilms.dao.MovieRepo;
import com.favouritefilms.entities.Movie;
import com.favouritefilms.entities.User;
import com.favouritefilms.models.MovieModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class MovieController {
    MovieRepo movieRepo;

    public MovieController(MovieRepo movieRepo) {
        this.movieRepo = movieRepo;
    }

    @GetMapping("/addmovie")
    public String addMovie(Model model){
        model.addAttribute("moviemodel", new MovieModel());
        return "movieform";
    }

    @GetMapping("/edit/{id}")
    public String editMovie(@PathVariable("id") Long id, Model model){
        Movie mov =  movieRepo.findById(id).get();
        MovieModel movie = new MovieModel(mov);
        model.addAttribute("moviemodel", movie);
        return "updatemovie";
    }

    @PostMapping("/update/{id}")
    public String updateMovie(@PathVariable("id") Long id, @ModelAttribute("moviemodel") @Valid MovieModel model, Errors errors){
        if (errors.hasErrors()) {
            return "updatemovie";
        }
        Movie mov = movieRepo.findById(id).get();
        mov.setTitle(model.getTitle());
        mov.setDirector(model.getDirector());
        mov.setYear(model.getYear());
        mov.setPersonalRating(model.getPersonalRating());
        movieRepo.save(mov);
        return "redirect:/home";
    }

    @PostMapping("/savemovie")
    public String saveMovie(@ModelAttribute("moviemodel") @Valid MovieModel movieModel, BindingResult result, @AuthenticationPrincipal User user){
        if (result.hasFieldErrors()) {
            return "movieform";
        }
        Movie movie = movieModel.toMovie();
        movie.setUser(user);
        movieRepo.save(movie);
        return "redirect:/home";
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable("id") Long id){
        Movie movie =  movieRepo.findById(id).get();
        movieRepo.delete(movie);
        return "redirect:/home";
    }
}
