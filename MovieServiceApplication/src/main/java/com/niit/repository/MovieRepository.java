package com.niit.repository;

import com.niit.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie,Integer> {
    @Query("{'genres.name' : {$in : [?0]}}")
    List<Movie> findAllMovieByGenre(String genre);
}
