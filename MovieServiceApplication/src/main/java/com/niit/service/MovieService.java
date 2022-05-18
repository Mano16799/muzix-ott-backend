package com.niit.service;

import com.niit.exception.*;
import com.niit.model.Movie;
import com.niit.model.User;
import com.rabbitmq.domain.FavouriteDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MovieService {
    //movie service methods
    Movie addMovie(Movie movie) throws MovieAlreadyExistsException;
    List<Movie> getAllMovies();
    List<Movie> getMoviesByGenre(String genre);
    List<Movie> getTopMovies();
    Optional<Movie> getMovieDetailsById(int id) throws MovieNotFoundException;

    //user-service methods
    Object saveUser(User user) throws UserAlreadyExistsException;
    Object userLogin(User user);
    Object deleteImage(String email);
    Object deleteUserDetails(String email);
    Object saveImage(MultipartFile file) throws IOException;
    Object updateUser(User user) throws UserNotFoundException;

    //favourite service methods
    Object addToFavourites(FavouriteDTO favouriteDTO);
    Object getAllFavouritesForAPerson(String email);
    Object deleteFavourite(String email, int id);
    Object deleteFavouriteByEmail(String email);
    List<Movie> getRecommendations(String email);

    //search service methods
    Object getSearchBykeyMovieResult(String keyword);
    Object getSearchBykeyAndGenreMovieResult(String keyword, String genre);
}
