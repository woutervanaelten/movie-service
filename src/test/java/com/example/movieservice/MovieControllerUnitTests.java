package com.example.movieservice;

import com.example.movieservice.model.Movie;
import com.example.movieservice.repository.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieRepository movieRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void whenGetMovies_thenReturnJsonMovies() throws Exception {

        Movie movie1 = new Movie("Movie1", 2000, "Category1", 60, "tt01");
        Movie movie2 = new Movie("Movie2", 2010, "Category2", 60, "tt02");

        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);
        movieList.add(movie2);

        given(movieRepository.findAll()).willReturn(movieList);

        mockMvc.perform(get("/movies/all", "Movie"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Movie1")))
                .andExpect(jsonPath("$[0].year", is(2000)))
                .andExpect(jsonPath("$[0].category", is("Category1")))
                .andExpect(jsonPath("$[0].minutes", is(60)))
                .andExpect(jsonPath("$[0].imdbID", is("tt01")))
                .andExpect(jsonPath("$[1].title", is("Movie2")))
                .andExpect(jsonPath("$[1].year", is(2010)))
                .andExpect(jsonPath("$[1].category", is("Category2")))
                .andExpect(jsonPath("$[1].minutes", is(60)))
                .andExpect(jsonPath("$[1].imdbID", is("tt02")));
    }

    @Test
    public void givenMovies_whenGetMovieById_thenReturnJsonMovies() throws Exception {
        Movie movie1 = new Movie("Movie1", 2000, "Category1", 60, "tt01");

        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);

        given(movieRepository.findMovieById(1)).willReturn(movieList);

        mockMvc.perform(get("/movies/movie/{movieId}", 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("Movie1")))
                .andExpect(jsonPath("$[0].year", is(2000)))
                .andExpect(jsonPath("$[0].category", is("Category1")))
                .andExpect(jsonPath("$[0].minutes", is(60)))
                .andExpect(jsonPath("$[0].imdbID", is("tt01")));
    }

    @Test
    public void givenMovies_whenGetMoviesByTitle_thenReturnJsonMovies() throws Exception {

        Movie movie1 = new Movie("Movie1", 2000, "Category1", 60, "tt01");
        Movie movie2 = new Movie("Movie2", 2010, "Category2", 60, "tt02");

        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);
        movieList.add(movie2);

        given(movieRepository.findMoviesByTitleContaining("Movie")).willReturn(movieList);

        mockMvc.perform(get("/movies/title/{title}", "Movie"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Movie1")))
                .andExpect(jsonPath("$[0].year", is(2000)))
                .andExpect(jsonPath("$[0].category", is("Category1")))
                .andExpect(jsonPath("$[0].minutes", is(60)))
                .andExpect(jsonPath("$[0].imdbID", is("tt01")))
                .andExpect(jsonPath("$[1].title", is("Movie2")))
                .andExpect(jsonPath("$[1].year", is(2010)))
                .andExpect(jsonPath("$[1].category", is("Category2")))
                .andExpect(jsonPath("$[1].minutes", is(60)))
                .andExpect(jsonPath("$[1].imdbID", is("tt02")));
    }

    @Test
    public void givenMovies_whenGetMoviesByCategory_thenReturnJsonMovies() throws Exception {

        Movie movie1 = new Movie("Movie1", 2000, "Category1", 60, "tt01");
        Movie movie2 = new Movie("Movie2", 2010, "Category2", 60, "tt02");

        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);
        movieList.add(movie2);

        given(movieRepository.findMoviesByCategoryContaining("Category")).willReturn(movieList);

        mockMvc.perform(get("/movies/category/{category}", "Category"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Movie1")))
                .andExpect(jsonPath("$[0].year", is(2000)))
                .andExpect(jsonPath("$[0].category", is("Category1")))
                .andExpect(jsonPath("$[0].minutes", is(60)))
                .andExpect(jsonPath("$[0].imdbID", is("tt01")))
                .andExpect(jsonPath("$[1].title", is("Movie2")))
                .andExpect(jsonPath("$[1].year", is(2010)))
                .andExpect(jsonPath("$[1].category", is("Category2")))
                .andExpect(jsonPath("$[1].minutes", is(60)))
                .andExpect(jsonPath("$[1].imdbID", is("tt02")));
    }

    @Test
    public void givenMovies_whenGetMoviesByYear_thenReturnJsonMovies() throws Exception {

        Movie movie1 = new Movie("Movie1", 2000, "Category1", 60, "tt01");
        Movie movie2 = new Movie("Movie2", 2000, "Category2", 60, "tt02");

        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);
        movieList.add(movie2);

        given(movieRepository.findMoviesByYear(2000)).willReturn(movieList);

        mockMvc.perform(get("/movies/year/{year}", 2000))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Movie1")))
                .andExpect(jsonPath("$[0].year", is(2000)))
                .andExpect(jsonPath("$[0].category", is("Category1")))
                .andExpect(jsonPath("$[0].minutes", is(60)))
                .andExpect(jsonPath("$[0].imdbID", is("tt01")));
    }

    @Test
    public void givenMovies_whenGetMovieByImdbID_thenReturnJsonMovies() throws Exception {

        Movie movie1 = new Movie("Movie1", 2000, "Category1", 60, "tt01");

        given(movieRepository.findMovieByImdbID("tt01")).willReturn(movie1);

        mockMvc.perform(get("/movies/imdbID/{imdbID}", "tt01"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Movie1")))
                .andExpect(jsonPath("$.year", is(2000)))
                .andExpect(jsonPath("$.category", is("Category1")))
                .andExpect(jsonPath("$.minutes", is(60)))
                .andExpect(jsonPath("$.imdbID", is("tt01")));
    }

    @Test
    public void givenMovie_whenPutMovie_thenReturnJsonMovie() throws Exception {
        Movie movie1 = new Movie("Movie1", 2000, "Category1", 60, "tt01");

        given(movieRepository.findMovieByImdbID("tt01")).willReturn(movie1);

        Movie updatedMovie = new Movie("Movie1", 2000, "Category1", 120, "tt01");

        mockMvc.perform(put("/movies")
                .content(mapper.writeValueAsString(updatedMovie))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Movie1")))
                .andExpect(jsonPath("$.year", is(2000)))
                .andExpect(jsonPath("$.category", is("Category1")))
                .andExpect(jsonPath("$.minutes", is(120)))
                .andExpect(jsonPath("$.imdbID", is("tt01")));
    }

    @Test
    public void givenMovie_whenDeleteMovie_thenStatusOk() throws Exception {
        Movie movieToBeDeleted = new Movie("MovieDelete", 2020, "CategoryDelete", 60, "tt100");

        given(movieRepository.findMovieByImdbID("tt100")).willReturn(movieToBeDeleted);

        mockMvc.perform(delete("/movies/movie/{imdbID}", "tt100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenMovie_whenDeleteMovie_thenStatusNotFound() throws Exception {
        given(movieRepository.findMovieByImdbID(("tt101"))).willReturn(null);

        mockMvc.perform(delete("/movies/movie/{imdbID}", "tt101")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
