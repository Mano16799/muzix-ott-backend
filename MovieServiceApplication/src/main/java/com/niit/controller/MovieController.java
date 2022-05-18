package com.niit.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.niit.exception.*;
import com.niit.model.Movie;
import com.niit.model.User;
import com.niit.service.MovieService;
import com.niit.service.SecurityTokenGenerator;
import com.rabbitmq.domain.FavouriteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v2")
public class MovieController {

    private MovieService movieService;
    private SecurityTokenGenerator securityTokenGenerator;

    @Autowired
    public MovieController(MovieService movieService, SecurityTokenGenerator securityTokenGenerator) {
        this.securityTokenGenerator = securityTokenGenerator;
        this.movieService = movieService;
    }

    //user-auth-communication - feign client
    @HystrixCommand(fallbackMethod = "fallbackRegLog")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody User user) throws UserAlreadyExistsException {
        Object user1 =movieService.saveUser(user);
        if(user1==null){
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(movieService.saveUser(user), HttpStatus.OK);
    }

    //user-auth-communication- feign client
    @HystrixCommand(fallbackMethod = "fallbackRegLog")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @PutMapping("movie-service/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) throws UserNotFoundException {
        return new ResponseEntity<>(movieService.updateUser(user), HttpStatus.OK);
    }

    //user-auth-communication- feign client
    @HystrixCommand(fallbackMethod = "fallbackDeleteFavourites")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @DeleteMapping("movie-service/user/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable("email") String email){
        return  new ResponseEntity<>(movieService.deleteUserDetails(email),HttpStatus.OK);
    }

    //user-auth-communication- feign client
    @HystrixCommand(fallbackMethod = "fallbackUpload")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @PostMapping("/upload")
    public ResponseEntity.BodyBuilder uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException{
        System.out.println(file);
        movieService.saveImage(file);
        return ResponseEntity.status(HttpStatus.OK);
    }

    //user-auth-communication- feign client
    @HystrixCommand(fallbackMethod = "fallbackDeleteFavourites")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @DeleteMapping("movie-service/deleteImage/{email}")
    public ResponseEntity<?> deleteImage(@PathVariable String email)  {
        return new ResponseEntity<>(movieService.deleteImage(email), HttpStatus.OK);
    }

    //user-auth-communication- feign client
    @HystrixCommand(fallbackMethod = "fallbackRegLog")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @PostMapping("/login")
    public ResponseEntity verifyUser(@RequestBody User user) {
        Map map = new HashMap<>();
        Object userObj = movieService.userLogin(user);
        System.out.println(userObj);
        if (userObj == null) {
            return new ResponseEntity("Check the credentials", HttpStatus.CONFLICT);
        }
        map.put("token",securityTokenGenerator.generateToken(user));
        map.put("user",userObj);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    //fav-service communication - rabbitmq
    @HystrixCommand(fallbackMethod = "fallbackSaveFav")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @PostMapping("movie-service/favourite")
    public ResponseEntity saveFavourite(@RequestBody FavouriteDTO favouriteDTO) {
        return new ResponseEntity(movieService.addToFavourites(favouriteDTO), HttpStatus.OK);
    }

    //fav-service communication - rabbitmq
    @HystrixCommand(fallbackMethod = "fallbackDeleteFavourites")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @DeleteMapping("movie-service/allFavourites/{email}")
    public ResponseEntity<?> deleteFavouriteByEmail( @PathVariable("email") String email)  {
        return new ResponseEntity<>(movieService.deleteFavouriteByEmail(email), HttpStatus.OK);
    }

    //fav-service communication - rabbitmq
    @HystrixCommand(fallbackMethod = "fallbackGetFav")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @GetMapping("movie-service/favourites/{email}")
    public ResponseEntity getFavouritesByEmail(@PathVariable String email)  {
        return new ResponseEntity(movieService.getAllFavouritesForAPerson(email), HttpStatus.OK);
    }

    @GetMapping("movie-service/favourites/genres/{email}")
    public ResponseEntity getRecommendationsByEmailId(@PathVariable String email)  {
        return new ResponseEntity(movieService.getRecommendations(email), HttpStatus.OK);
    }

    //fav-service communication - rabbitmq
    @HystrixCommand(fallbackMethod = "fallbackDeleteFav")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @DeleteMapping("movie-service/favourites/{email}/{id}")
    public ResponseEntity<?> deleteFavourite(@PathVariable("id") int id, @PathVariable("email") String email) throws InterruptedException {
        return new ResponseEntity<>(movieService.deleteFavourite(email, id), HttpStatus.OK);
    }

    //search-service By Keyword communication - rabbitmq
    @HystrixCommand(fallbackMethod = "fallbackDeleteFavourites")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @GetMapping("movies/search/{key}")
    public ResponseEntity getSearchByKeyMovieResult(@PathVariable String key) {
        return new ResponseEntity(movieService.getSearchBykeyMovieResult(key), HttpStatus.OK);
    }

    //search-service By Keyword and genre communication - rabbitmq
    @HystrixCommand(fallbackMethod = "fallbackGetMoviesByKeyAndGenre")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @GetMapping("movies/{key}/{genre}")
    public ResponseEntity getSearchByKeyAndGenreMovieResult(@PathVariable("key") String key, @PathVariable("genre") String genre) {
        return new ResponseEntity(movieService.getSearchBykeyAndGenreMovieResult(key, genre), HttpStatus.OK);
    }


    @HystrixCommand(fallbackMethod = "fallbackStoreMovieDetails")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @PostMapping("movie")
    public ResponseEntity storeMovieDetails(@RequestBody Movie movie) throws MovieAlreadyExistsException {
        return new ResponseEntity<>(movieService.addMovie(movie), HttpStatus.OK);
    }

    @HystrixCommand(fallbackMethod = "fallbackGetMovieDetails")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @GetMapping("movies")
    public ResponseEntity getMovieDetails() {
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

    @HystrixCommand(fallbackMethod = "fallbackGetMovieDetailsByGenre")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @GetMapping("movies/{genre}")
    public ResponseEntity getMovieDetailsByGenre(@PathVariable String genre) {
        return new ResponseEntity<>(movieService.getMoviesByGenre(genre), HttpStatus.OK);
    }

    @HystrixCommand(fallbackMethod = "fallbackGetMovieDetailsById")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @GetMapping("movie/{id}")
    public ResponseEntity getMovieDetailsById(@PathVariable int id) {
        try {
            return new ResponseEntity<>(movieService.getMovieDetailsById(id).get(), HttpStatus.OK);
        } catch (MovieNotFoundException e) {
            return new ResponseEntity<>("Movie Not Found", HttpStatus.OK);
        }
    }

    @GetMapping("top-rated")
    public ResponseEntity getTopRatedMoviesById() {
        return new ResponseEntity<>(movieService.getTopMovies(), HttpStatus.OK);
    }


    //fallback methods
    public ResponseEntity<?> fallbackRegLog(User user) {
        String msg = "Movie service is down!!! Try Again later";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> fallbackSaveFav(FavouriteDTO favouriteDTO) {
        String msg = "Movie service is down!!! Try Again later";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> fallbackGetFav(String email) {
        String msg = "Movie service is down!!! Try Again later";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> fallbackDeleteFav(int id, String email) {
        String msg = "Movie service is down!!! Try Again later";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> fallbackStoreMovieDetails(Movie movie) {
        String msg = "Movie service is down!!! Try Again later";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> fallbackGetMovieDetails() {
        String msg = "Movie service is down!!! Try Again later";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> fallbackGetMovieDetailsByGenre(String genre) {
        String msg = "Movie service is down!!! Try Again later";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity.BodyBuilder fallbackUpload(MultipartFile file) {
        String msg = "Movie service is down!!! Try Again later";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> fallbackGetMovieDetailsById(int id) {
        String msg = "Movie service is down!!! Try Again later";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> fallbackDeleteFavourites(String email) {
        String msg = "Movie service is down!!! Try Again later";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> fallbackGetMoviesByKeyAndGenre(String key, String genre) {
        String msg = "Movie service is down!!! Try Again later";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }


}
