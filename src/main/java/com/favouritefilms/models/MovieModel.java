package com.favouritefilms.models;

import com.favouritefilms.entities.Movie;
import com.favouritefilms.entities.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MovieModel {

    private Long id;
    @NotBlank(message = "Title is required")
    private String title;
    private String director;
    private Integer year;
    private Integer personalRating;
    private User user;

    public MovieModel() {
    }

    public MovieModel(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.director = movie.getDirector();
        this.year = movie.getYear();
        this.personalRating = movie.getPersonalRating();
        this.user = movie.getUser();
    }
    public Movie toMovie(){
        return new Movie(title, director, year, personalRating);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPersonalRating() {
        return personalRating;
    }

    public void setPersonalRating(Integer personalRating) {
        this.personalRating = personalRating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
