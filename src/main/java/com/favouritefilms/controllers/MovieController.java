package com.favouritefilms.controllers;

import com.favouritefilms.dto.MovieDTO;
import com.favouritefilms.entities.Movie;
import com.favouritefilms.entities.User;
import com.favouritefilms.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/addmovie")
    public String addMovie(){
        return "movieform";
    }

    @PostMapping("/savemovie")
    public String saveMovie(@AuthenticationPrincipal User user,
                            @Valid MovieDTO movieDTO,
                            BindingResult result,
                            Model model){
        if (result.hasFieldErrors()) {
            Map<String, String> errors = ControllerUtil.getErrors(result);
            model.mergeAttributes(errors);
            return "movieform";
        }
        Movie movie = movieDTO.toNewMovie();

        movie.setUser(user);

        movieService.saveMovie(movie);

        return "redirect:/home";
    }

    @GetMapping("/edit/{id}")
    public String editMovie(@PathVariable("id") Movie movie, Model model){

        MovieDTO movieDTO = new MovieDTO(movie);

        model.addAttribute("moviemodel", movieDTO);

        return "updatemovie";
    }

    @PostMapping("/update/{id}")
    public String updateMovie(@PathVariable("id") Movie movie,
                              @ModelAttribute("moviemodel") @Valid MovieDTO movieDTO,
                              BindingResult result){
        if (result.hasErrors()) {
            return "updatemovie";
        }

        Movie updatedMovie = movieDTO.updateMovie(movie);

        movieService.saveMovie(updatedMovie);

        return "redirect:/home";
    }


    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable("id") Movie movie){

        movieService.delete(movie);

        return "redirect:/home";
    }
}
