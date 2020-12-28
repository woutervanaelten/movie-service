package com.example.movieservice;

import com.example.movieservice.model.Movie;
import com.example.movieservice.repository.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    private Movie movie1 = new Movie("Movie1", 2000, "Category1", 60, "tt01");
    private Movie movie2 = new Movie("Movie2", 2010, "Category2", 60, "tt02");
    private Movie movie3 = new Movie("Movie3", 2020, "Category3", 60, "tt03");
    private Movie movieToBeDeleted = new Movie("MovieDelete", 2020, "CategoryDelete", 60, "tt100");

    @BeforeEach
    public void beforeAllTests() {
        movieRepository.deleteAll();
        movieRepository.save(movie1);
        movieRepository.save(movie2);
        movieRepository.save(movie3);
        movieRepository.save(movieToBeDeleted);
    }

    @AfterEach
    public void afterAllTests() {
        movieRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenMovies_whenGetMoviesByTitle_thenReturnJsonMovies() throws Exception {

        mockMvc.perform(get("/movies/title/{title}", "Movie"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].title", is("Movie1")))
                .andExpect(jsonPath("$[0].year", is(2000)))
                .andExpect(jsonPath("$[0].category", is("Category1")))
                .andExpect(jsonPath("$[0].minutes", is(60)))
                .andExpect(jsonPath("$[0].imdbID", is("tt01")))
                .andExpect(jsonPath("$[1].title", is("Movie2")))
                .andExpect(jsonPath("$[1].year", is(2010)))
                .andExpect(jsonPath("$[1].category", is("Category2")))
                .andExpect(jsonPath("$[1].minutes", is(60)))
                .andExpect(jsonPath("$[1].imdbID", is("tt02")))
                .andExpect(jsonPath("$[2].title", is("Movie3")))
                .andExpect(jsonPath("$[2].year", is(2020)))
                .andExpect(jsonPath("$[2].category", is("Category3")))
                .andExpect(jsonPath("$[2].minutes", is(60)))
                .andExpect(jsonPath("$[2].imdbID", is("tt03")));
    }

    @Test
    public void givenMovies_whenGetMoviesByCategory_thenReturnJsonMovies() throws Exception {

        mockMvc.perform(get("/movies/category/{category}", "Category"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].title", is("Movie1")))
                .andExpect(jsonPath("$[0].year", is(2000)))
                .andExpect(jsonPath("$[0].category", is("Category1")))
                .andExpect(jsonPath("$[0].minutes", is(60)))
                .andExpect(jsonPath("$[0].imdbID", is("tt01")))
                .andExpect(jsonPath("$[1].title", is("Movie2")))
                .andExpect(jsonPath("$[1].year", is(2010)))
                .andExpect(jsonPath("$[1].category", is("Category2")))
                .andExpect(jsonPath("$[1].minutes", is(60)))
                .andExpect(jsonPath("$[1].imdbID", is("tt02")))
                .andExpect(jsonPath("$[2].title", is("Movie3")))
                .andExpect(jsonPath("$[2].year", is(2020)))
                .andExpect(jsonPath("$[2].category", is("Category3")))
                .andExpect(jsonPath("$[2].minutes", is(60)))
                .andExpect(jsonPath("$[2].imdbID", is("tt03")));
    }

    @Test
    public void givenMovies_whenGetMoviesByYear_thenReturnJsonMovies() throws Exception {

        mockMvc.perform(get("/movies/year/{year}", 2000))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Movie1")))
                .andExpect(jsonPath("$[0].year", is(2000)))
                .andExpect(jsonPath("$[0].category", is("Category1")))
                .andExpect(jsonPath("$[0].minutes", is(60)))
                .andExpect(jsonPath("$[0].imdbID", is("tt01")));
    }

    @Test
    public void givenMovies_whenGetMovieByImdbID_thenReturnJsonMovie() throws Exception {

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
    public void whenPostMovie_thenReturnJsonMovie() throws Exception{
        Movie newMovie = new Movie("newMovie", 2021, "New", 60, "tt99");

        mockMvc.perform(post("/movies")
                .content(mapper.writeValueAsString(newMovie))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("newMovie")))
                .andExpect(jsonPath("$.year", is(2021)))
                .andExpect(jsonPath("$.category", is("New")))
                .andExpect(jsonPath("$.minutes", is(60)))
                .andExpect(jsonPath("$.imdbID", is("tt99")));
    }

    @Test
    public void givenMovie_whenPutMovie_thenReturnJsonMovie() throws Exception {

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

        mockMvc.perform(delete("/movies/movie/{imdbID}", "tt100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenMovie_whenDeleteMovie_thenStatusNotFound() throws Exception {

        mockMvc.perform(delete("/movies/movie/{imdbID}", "tt101")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
