package com.favouritefilms.dao;

import com.favouritefilms.entities.Movie;
import com.favouritefilms.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieRepo extends CrudRepository<Movie, Long> {
    List<Movie> findAllByUserOrderByTitle(User user);
}
