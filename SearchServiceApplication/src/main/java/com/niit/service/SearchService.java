package com.niit.service;

import com.niit.exception.MovieNotFoundException;
import com.niit.model.Movie;

import java.util.List;

public interface SearchService {
    List<Movie> searchAllMoviesBasedOnKeyword(String keyword) throws MovieNotFoundException;
    List<Movie> searchAllMoviesBasedOnKeywordAndGenre(String keyword, String Genre) throws MovieNotFoundException;
//    List<Movie> searchRecommendedMoviesOnSearch( String key) throws MovieNotFoundException;
}
