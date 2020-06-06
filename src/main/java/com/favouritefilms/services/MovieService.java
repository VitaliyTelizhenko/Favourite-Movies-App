package com.favouritefilms.services;

import com.favouritefilms.repositories.MovieRepo;
import com.favouritefilms.entities.Movie;
import com.favouritefilms.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepo movieRepo;

    @Autowired
    public MovieService(MovieRepo movieRepo) {
        this.movieRepo = movieRepo;
    }

    public List<Movie> findUserMovies(User user) {
        return movieRepo.findAllByUserOrderByTitle(user);
    }

    public void saveMovie(Movie movie) {

        movieRepo.save(movie);
    }

    public void delete(Movie movie) {
        movieRepo.delete(movie);
    }
}
