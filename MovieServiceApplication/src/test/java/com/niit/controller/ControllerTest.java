package com.niit.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.exception.MovieAlreadyExistsException;
import com.niit.model.*;
import com.niit.service.MovieService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private MovieService movieService;
    private Movie movie;
    private List<Genre> genres;
    private List<ProductionCompany> production_companies;
    private List<ProductionCountry> production_countries;
    private List<SpokenLanguage> spoken_languages;

    @InjectMocks
    private MovieController movieController;

    @BeforeEach
    public void setUp() {
        genres = Arrays.asList(new Genre(1, "Action"));
        production_companies = Arrays.asList(new ProductionCompany(25, "/qZCc1lty5FzX30aOCVRBLzaVmcp.png", "20th Century Fox", "US"));
        production_countries = Arrays.asList(new ProductionCountry("US", "United States of America"));
        movie = new Movie(526, false, "/s0sedDCn7KDtjExHAFJtUaN3Iw5.jpg", null, 20000000, genres, "", "tt0089457", "en", "Ladyhawke",
                "Captain Etienne Navarre is a man on whose shoulders lies a cruel curse. Punished for loving each other, Navarre must become a wolf by night whilst his lover, Lady Isabeau, takes the form of a hawk by day. Together, with the thief Philippe Gaston, they must try to overthrow the corrupt Bishop and in doing so break the spell.",
                17.524, "/l3nRJ9Cs7C1AZ7das8S76QqwqjI.jpg", production_companies, production_countries, "1985-03-27", 18432000, 121, spoken_languages, "Released",
                "No force in Heaven will release them. No power on Earth can save them.", "Ladyhawke", false, 7, 8);

        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();

    }

    @AfterEach
    public void tearDown() {
        spoken_languages = null;
        production_countries = null;
        production_companies = null;
        genres = null;
        movie = null;
    }

    @Test
    public void givenMovieToSaveReturnSaveMovieSuccess() throws MovieAlreadyExistsException, Exception {
        when(movieService.addMovie(any())).thenReturn(movie);

        mockMvc.perform(post("/api/v1/movie")//making dummy http post request
                        .contentType(MediaType.APPLICATION_JSON)//setting the content type of the post request
                        .content(jsonToString(movie)))//firstly, java object will be converted to json string then will  be passed with the mock http request.
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(movieService, times(1)).addMovie(any());

    }

    @Test
    public void givenMovieToSaveReturnSaveMovieFailure() throws MovieAlreadyExistsException, Exception {
        when(movieService.addMovie(any())).thenReturn(null);

        mockMvc.perform(post("/api/v1/movie")//making dummy http post request
                        .contentType(MediaType.APPLICATION_JSON)//setting the content type of the post request
                        .content(jsonToString(movie)))//firstly, java object will be converted to json string then will  be passed with the mock http request.
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(movieService, times(1)).addMovie(any());

    }

    @Test
    public void returnAllMoviesSuccess() throws Exception {
        when(movieService.getAllMovies()).thenReturn(Arrays.asList(movie));

        mockMvc.perform(get("/api/v1/movies")//making dummy http post request
                        .contentType(MediaType.APPLICATION_JSON)//setting the content type of the post request
                        .content(jsonToString(movie)))//firstly, java object will be converted to json string then will  be passed with the mock http request.
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(movieService, times(1)).getAllMovies();

    }


    private static String jsonToString(final Object ob) throws JsonProcessingException {
        String result;

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(ob);
            System.out.println("Json Content that has been posted:\n" + jsonContent);
            result = jsonContent;
        } catch (JsonProcessingException e) {
            result = "JSON processing error";
        }

        return result;
    }
}
