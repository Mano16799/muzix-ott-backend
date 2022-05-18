package com.niit.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.niit.exception.FavouriteAlreadyExistException;
import com.niit.exception.FavouriteNotFoundException;
import com.niit.model.Favourite;
import com.niit.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v4")
public class FavouritesController {

    private FavouriteService favouriteService;
    private ResponseEntity responseEntity;

    @Autowired
    public FavouritesController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

    @HystrixCommand(fallbackMethod = "fallbackgetEmailFavs")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @GetMapping("favourites/{email}")
    public ResponseEntity getAllFavouritesByID(@PathVariable String email) {
        return new ResponseEntity(favouriteService.getAllFavouriteByID(email), HttpStatus.OK);
    }

    @GetMapping("favourites/genres/{email}")
    public ResponseEntity getAllFavouriteGenres(@PathVariable String email) {
        return new ResponseEntity(favouriteService.getAllFavouriteGenresByID(email), HttpStatus.OK);
    }

    @HystrixCommand(fallbackMethod = "fallbackdeleteFavouritesByEmail")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @DeleteMapping("favourites/{email}")
    public ResponseEntity<?> deleteFavouriteByEmail(@PathVariable("email") String email) {
        if(favouriteService.getAllFavouriteByID(email).size()==0){
            responseEntity = new ResponseEntity<>("No favourites to delete",HttpStatus.CONFLICT);
        }
        responseEntity = new ResponseEntity(favouriteService.deleteFavouriteByEmail(email), HttpStatus.OK);

        return responseEntity;
    }

    @HystrixCommand(fallbackMethod = "fallbackgetAllFavs")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @GetMapping("favourites")
    public ResponseEntity getAllFavourites() {
        responseEntity = new ResponseEntity<>(favouriteService.getAllFavourites(), HttpStatus.OK);
        return responseEntity;
    }

    @HystrixCommand(fallbackMethod = "fallbackpostFavourite")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @PostMapping("favourite")
    public ResponseEntity<?> addToFavourites(@RequestBody Favourite favourite) {
        try {
            responseEntity = new ResponseEntity(favouriteService.addToFavourites(favourite), HttpStatus.OK);
        } catch (FavouriteAlreadyExistException e) {
            responseEntity = new ResponseEntity(new FavouriteAlreadyExistException().getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @HystrixCommand(fallbackMethod = "fallbackdeleteFavourite")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    @DeleteMapping("favourites/{email}/{id}")
    public ResponseEntity<?> deleteFavourite(@PathVariable("id") int id, @PathVariable("email") String email) {
        try {
            responseEntity = new ResponseEntity(favouriteService.deleteFavourite(email, id), HttpStatus.OK);
        } catch (FavouriteAlreadyExistException e) {
            responseEntity = new ResponseEntity(new FavouriteNotFoundException().getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }


    public ResponseEntity<?> fallbackgetEmailFavs(String email) {
        String msg = "Favourites service is down!!! Try Again later";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> fallbackgetAllFavs() {
        String msg = "Favourites service is down!!! Try Again later";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> fallbackpostFavourite(Favourite favourite) {
        String msg = "Favourites service is down!!! Try Again later";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> fallbackdeleteFavourite(int id, String email) {
        String msg = "Favourites service is down!!! Try Again later";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> fallbackdeleteFavouritesByEmail(String email) {
        String msg = "Favourites service is down!!! Try Again later";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }
}
