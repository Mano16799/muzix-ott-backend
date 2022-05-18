package com.niit.service;


import com.niit.exception.MovieAlreadyExistsException;
import com.niit.model.*;
import com.niit.repository.MovieRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    private Movie movie;
    private List<Genre> genres;
    private List<ProductionCompany> production_companies;
    private List<ProductionCountry> production_countries;
    private List<SpokenLanguage> spoken_languages;

    @BeforeEach
    public void setUp() {
        genres = Arrays.asList(new Genre(1, "Action"));
        production_companies = Arrays.asList(new ProductionCompany(25, "/qZCc1lty5FzX30aOCVRBLzaVmcp.png", "20th Century Fox", "US"));
        production_countries = Arrays.asList(new ProductionCountry("US", "United States of America"));
        movie = new Movie(526, false, "/s0sedDCn7KDtjExHAFJtUaN3Iw5.jpg", null, 20000000, genres, "", "tt0089457", "en", "Ladyhawke",
                "Captain Etienne Navarre is a man on whose shoulders lies a cruel curse. Punished for loving each other, Navarre must become a wolf by night whilst his lover, Lady Isabeau, takes the form of a hawk by day. Together, with the thief Philippe Gaston, they must try to overthrow the corrupt Bishop and in doing so break the spell.",
                17.524, "/l3nRJ9Cs7C1AZ7das8S76QqwqjI.jpg", production_companies, production_countries, "1985-03-27", 18432000, 121, spoken_languages, "Released",
                "No force in Heaven will release them. No power on Earth can save them.", "Ladyhawke", false, 7, 8);
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
    public void givenProductToSaveReturnSavedMovieSuccess() throws MovieAlreadyExistsException {
        when(movieRepository.save(any())).thenReturn(movie);
        assertEquals(movie, movieService.addMovie(movie));
        verify(movieRepository, times(1)).save(any());
    }

    @Test
    public void givenNullObjectToSaveReturnSavedMovieFailure() throws MovieAlreadyExistsException {
        movie=null;
        when(movieRepository.save(any())).thenReturn(movie);
        assertEquals(movie, movieService.addMovie(movie));
        verify(movieRepository, times(1)).save(any());
    }


    @Test
    public void returnCorrectNumberOfDocuments(){
        when(movieRepository.findAll()).thenReturn(Arrays.asList(movie));
        assertEquals(1,movieService.getAllMovies().size());
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    public void returnZeroForSizeOfEmptyCollection(){
        when(movieRepository.findAll()).thenReturn(new ArrayList<>());
        assertEquals(0,movieService.getAllMovies().size());
        verify(movieRepository, times(1)).findAll();
    }


    @Test
    public void returnCorrectNumberOfMoviesForGivenGenre(){
        when(movieRepository.findAllMovieByGenre(any())).thenReturn(new ArrayList<>());
        assertEquals(0,movieService.getMoviesByGenre("Action").size());
        verify(movieRepository, times(1)).findAllMovieByGenre("Action");
    }
}
