package com.niit.repository;

import com.niit.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<Movie,Integer> {
    List<Movie> findAllMovieByTitleContainingIgnoreCase(String title);

    @Query("{'genres.name' : {$in : [?0]}}")
    List<Movie> findAllMovieByGenre(String genre);
}
