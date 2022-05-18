package com.niit.repository;


import com.niit.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class MovieRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;

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
    public void givenMovieToSaveShouldReturnMovie() {
        Movie savedMovie = movieRepository.save(movie);
        assertNotNull(movie);
        assertEquals(movie.get_id(), savedMovie.get_id());
    }

    @Test
    public void givenProductIdShouldReturnCorrectProduct() {
        Movie savedMovie = movieRepository.save(movie);
        assertEquals(savedMovie.getTitle(), "Ladyhawke");
    }

    @Test
    public void givenIncorrectProductIdShouldReturnEmpty(){
        movieRepository.save(movie);
        assertEquals(movieRepository.findById(2), Optional.empty());
    }

    @Test
    public void givenNullObjectShouldReturnNull(){
        movie = null;
        assertThrows(IllegalArgumentException.class,()->movieRepository.save(movie));
    }


    @Test
    public void givenIDtoDeleteShouldDeleteRecord(){
        movieRepository.save(movie);
        movieRepository.deleteById(movie.get_id());
        assertEquals(movieRepository.findById(movie.get_id()), Optional.empty());
    }


    @Test
    public void returnCorrectNumberOfSavedDocuments(){
        movieRepository.save(movie);
        assertEquals(movieRepository.count(),1);
    }
}
