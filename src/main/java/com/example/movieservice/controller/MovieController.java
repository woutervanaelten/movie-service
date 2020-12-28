package com.example.movieservice.controller;

import com.example.movieservice.model.Movie;
import com.example.movieservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

//    @PostConstruct
//    public void fillDB() {
//        if (movieRepository.count() == 0) {
//            movieRepository.save(new Movie("The Shawshank Redemption", 1994, "Drama", 142, "tt0111161"));
//            movieRepository.save(new Movie("The Godfather", 1972, "Crime, Drama", 175, "tt0068646"));
//            movieRepository.save(new Movie("The Dark Night", 2008, "Action, Crime, Drama", 152, "tt0468569"));
//            movieRepository.save(new Movie("The Godfather: Part II", 1974, "Crime, Drama", 202, "tt0071562"));
//            movieRepository.save(new Movie("12 Angry Men", 1957, "Crime, Drama", 96, "tt0050083"));
//            movieRepository.save(new Movie("The Lord of the Rings: The return of the King", 2003, "Action, Adventure, Drama", 201, "tt0167260"));
//            movieRepository.save(new Movie("Pulp Fiction", 1994, "Crime, Drama", 154, "tt0110912"));
//            movieRepository.save(new Movie("Schindler's List", 1993, "Biography, Drama, History", 195, "tt0108052"));
//            movieRepository.save(new Movie("Inception", 2010, "Action, Adventure, Sci-Fi", 148, "tt1375666"));
//            movieRepository.save(new Movie("Fight Club", 1999, "Drama", 139, "tt0137523"));
//            movieRepository.save(new Movie("The Lord of the Rings: The Fellowship of the Ring", 2001, "Action, Adventure, Drama", 178, "tt0120737"));
//        }
//    }

    @GetMapping("/movies/title/{title}")
    public List<Movie> getMoviesByTitle(@PathVariable String title) {
        return movieRepository.findMoviesByTitleContaining(title);
    }

    @GetMapping("/movies/category/{category}")
    public List<Movie> getMoviesByCategory(@PathVariable String category) {
        return movieRepository.findMoviesByCategoryContaining(category);
    }

    @GetMapping("/movies/year/{year}")
    public List<Movie> getMoviesByYear(@PathVariable int year) {
        return movieRepository.findMoviesByYear(year);
    }

    @GetMapping("movies/imdbID/{imdbID}")
    public Movie getMovieByImdbID(@PathVariable String imdbID) {
        return movieRepository.findMovieByImdbID(imdbID);
    }

    @PostMapping("/movies")
    public Movie addMovie(@RequestBody Movie movie) {
        movieRepository.save(movie);
        return movie;
    }

    @PutMapping("/movies")
    public Movie updateMovie(@RequestBody Movie updatedMovie) {
        Movie retrievedMovie = movieRepository.findMovieByImdbID(updatedMovie.getImdbID());

        retrievedMovie.setMinutes(updatedMovie.getMinutes());
        retrievedMovie.setCategory(updatedMovie.getCategory());
        retrievedMovie.setYear(updatedMovie.getYear());
        retrievedMovie.setTitle(updatedMovie.getTitle());

        movieRepository.save(retrievedMovie);

        return retrievedMovie;
    }

    @DeleteMapping("/movies/movie/{imdbID}")
    public ResponseEntity deleteReview(@PathVariable String imdbID) {
        Movie movie = movieRepository.findMovieByImdbID(imdbID);
        if (movie != null) {
            movieRepository.delete(movie);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
