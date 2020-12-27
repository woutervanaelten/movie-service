package com.example.movieservice.repository;

import com.example.movieservice.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Movie findMovieByTitle(String title);
    List<Movie> findMoviesByCategoryContaining(String category);
    List<Movie> findMoviesByTitleContaining(String title);
    List<Movie> findMoviesByYear(int year);
}
