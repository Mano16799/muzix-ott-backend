package com.niit.service;

import com.niit.config.*;
import com.niit.exception.MovieAlreadyExistsException;
import com.niit.exception.MovieNotFoundException;
import com.niit.model.Genre;
import com.niit.model.Movie;
import com.niit.model.User;
import com.niit.proxy.UserProxy;
import com.niit.repository.MovieRepository;
import com.rabbitmq.domain.FavouriteDTO;
import com.rabbitmq.domain.MovieToBeDeletedDTO;
import com.rabbitmq.domain.SearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class MovieServiceImpl implements MovieService {
    private MovieRepository movieRepository;
    private UserProxy userProxy;
    @Autowired
    PostFavouriteClient postFavouriteClient;
    @Autowired
    GetAllFavouritesClient getAllFavouritesClient;
    @Autowired
    DeleteFavouriteClient deleteFavouriteClient;
    @Autowired
    DeleteAllFavouriteClient deleteAllFavouriteClient;
    @Autowired
    GetFavGenresClient getFavGenresClient;
    @Autowired
    SearchByKeyClient searchByKeyClient;
    @Autowired
    SearchByKeyAndGenreClient searchByKeyAndGenreClient;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, UserProxy userProxy) {
        this.movieRepository = movieRepository;
        this.userProxy = userProxy;
    }

    MovieServiceImpl() {
    }

    /************** Movie service methods ****************/
    @Override
    public Movie addMovie(Movie movie) throws MovieAlreadyExistsException {
        if (movieRepository.findById(movie.get_id()).isPresent()) {
            throw new MovieAlreadyExistsException();
        }
        return movieRepository.save(movie);
    }

    /*************** new ****************/
    public List<Movie> getRecommendations(String email) {
        List<Movie> recommendations = new ArrayList<>();
        String genres = getFavGenresClient.getFavouritesGenresByMailRabbit(email).toString();
        if (genres == null || genres == "") {
            int random = (int) Math.floor(Math.random() * (600 - 0 + 1) + 0);
            for (int i = random; i < random + 6; i++) {
                List<Movie> allMovies = movieRepository.findAll();
                recommendations.add(allMovies.get(i));
            }
        }
        else {
            String[] favGenres = genres.split(" ");
            for(int i=0;i<favGenres.length;i++){
                List<Movie> moviesByGenre = movieRepository.findAllMovieByGenre(favGenres[i]);
                int random = (int) Math.floor(Math.random() * (moviesByGenre.size() - 0+1) + 0);
                System.out.println(random);
                for(int j=random;j<random+2;j++){
                    if(!recommendations.contains(moviesByGenre.get(j))){
                        recommendations.add(moviesByGenre.get(j));
                    }
                }
            }
        }
        return recommendations;
    }

    @Override
    public Optional<Movie> getMovieDetailsById(int id) throws MovieNotFoundException {
        if (movieRepository.findById(id).isEmpty()) {
            throw new MovieNotFoundException();
        }

        return movieRepository.findById(id);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.findAllMovieByGenre(genre);
    }

    @Override
    public List<Movie> getTopMovies() {
        List<Movie> allMovies = movieRepository.findAll();
        Collections.sort(allMovies);
        List<Movie> topMovies = Arrays.asList(allMovies.get(0), allMovies.get(1), allMovies.get(2), allMovies.get(3));
        return topMovies;
    }

    /************** User service methods ****************/

    @Override
    public Object saveUser(User user) {
        return userProxy.saveUser(user);
    }

    @Override
    public Object saveImage(MultipartFile file) throws IOException {
        return userProxy.uploadImage(file);
    }

    @Override
    public Object deleteImage(String email) {
        return userProxy.deleteImage(email);
    }

    @Override
    public Object deleteUserDetails(String email) {
        return userProxy.deleteUser(email);
    }

    @Override
    public Object userLogin(User user) {
        Object o = userProxy.verifyUser(user);
        System.out.println(o + "feign");
        return o;
    }

    @Override
    public Object updateUser(User user) {
        return userProxy.updateUser(user);
    }

    /************** Favourite service methods ****************/

    @Override
    public Object addToFavourites(FavouriteDTO favouriteDTO) {
        return postFavouriteClient.saveFavouriteRabbit(favouriteDTO);
    }

    @Override
    public Object getAllFavouritesForAPerson(String email) {

        return getAllFavouritesClient.getFavouritesByMailRabbit(email);
    }

    @Override
    public Object deleteFavourite(String email, int id) {
        MovieToBeDeletedDTO movie = new MovieToBeDeletedDTO(email, id);
        return deleteFavouriteClient.deleteFavourite(movie);
    }

    @Override
    public Object deleteFavouriteByEmail(String email) {

        return deleteAllFavouriteClient.deleteFavouriteByEmail(email);
    }

    /************** Search service methods ****************/

    @Override
    public Object getSearchBykeyMovieResult(String keyword) {
        return searchByKeyClient.searchMoviesByKeyRabbit(keyword);
    }

    @Override
    public Object getSearchBykeyAndGenreMovieResult(String keyword, String genre) {
        SearchDTO searchDTO = new SearchDTO(keyword, genre);
        return searchByKeyAndGenreClient.getSearchByKeyAndGenreResultRabbit(searchDTO);
    }

}
