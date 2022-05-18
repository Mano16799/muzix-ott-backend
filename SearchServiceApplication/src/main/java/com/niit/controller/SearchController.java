package com.niit.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.niit.exception.MovieNotFoundException;
import com.niit.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v3")
public class SearchController {

    private SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @HystrixCommand(fallbackMethod = "fallbackAllSearchtoget")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "700")
    @GetMapping("movies/{key}")
    public ResponseEntity getMoviesBasedOnKeyWord(@PathVariable String key) {
        ResponseEntity r;
        try {
            r = new ResponseEntity<>(searchService.searchAllMoviesBasedOnKeyword(key), HttpStatus.OK);
        } catch (MovieNotFoundException e) {
            r = new ResponseEntity<>(new MovieNotFoundException().getMessage(), HttpStatus.NOT_FOUND);
        }
        return r;
    }

    @HystrixCommand(fallbackMethod = "fallbackAllSearchGenrebased")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "700")
    @GetMapping("movies/{genre}/{key}")
    public ResponseEntity getMoviesBasedOnKeyWordAndTitle(@PathVariable("key") String key, @PathVariable("genre") String genre) {

        ResponseEntity responseEntity;
        try{
            responseEntity= new ResponseEntity<>(searchService.searchAllMoviesBasedOnKeywordAndGenre(key, genre),HttpStatus.OK);
        } catch (MovieNotFoundException e) {
            responseEntity= new ResponseEntity<>(new MovieNotFoundException().getMessage(),HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    public ResponseEntity<?> fallbackAllSearchtoget(String key) {
        String msg = "Search service is down!!! please try again later";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> fallbackAllSearchGenrebased(String key,String genre) {
        String msg = "Search service is down!!! please try again later";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

}
