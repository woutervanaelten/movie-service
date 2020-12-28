package com.example.movieservice.controller;

import com.example.movieservice.model.Movie;
import com.example.movieservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @PostConstruct
    public void fillDB(){
        if(movieRepository.count()==0){
            movieRepository.save(new Movie("The Shawshank Redemption", 1994, "Drama", 142));
            movieRepository.save(new Movie("The Godfather", 1972, "Crime, Drama", 175));
            movieRepository.save(new Movie("The Dark Night", 2008, "Action, Crime, Drama", 152));
            movieRepository.save(new Movie("The Godfather: Part II", 1974, "Crime, Drama", 202));
            movieRepository.save(new Movie("12 Angry Men", 1957, "Crime, Drama", 96));
            movieRepository.save(new Movie("The Lord of the Rings: The return of the King", 2003, "Action, Adventure, Drama", 201));
            movieRepository.save(new Movie("Pulp Fiction", 1994, "Crime, Drama", 154));
            movieRepository.save(new Movie("Schindler's List", 1993, "Biography, Drama, History", 195));
            movieRepository.save(new Movie("Inception", 2010, "Action, Adventure, Sci-Fi", 148));
            movieRepository.save(new Movie("Fight Club", 1999, "Drama", 139));
            movieRepository.save(new Movie("The Lord of the Rings: The Fellowship of the Ring", 2001, "Action, Adventure, Drama", 178));
        }
    }

    @GetMapping("/movies/title/{title}")
    public List<Movie> getMoviesByTitle(@PathVariable String title){
        return movieRepository.findMoviesByTitleContaining(title);
    }

    @GetMapping("/movies/category/{category}")
    public List<Movie> getMoviesByCategory(@PathVariable String category){
        return movieRepository.findMoviesByCategoryContaining(category);
    }

    @GetMapping("/movies/year/{year}")
    public List<Movie> getMoviesByYear(@PathVariable int year){
        return movieRepository.findMoviesByYear(year);
    }
}
