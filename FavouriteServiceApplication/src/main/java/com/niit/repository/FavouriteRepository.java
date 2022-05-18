package com.niit.repository;

import com.niit.model.Favourite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteRepository extends MongoRepository<Favourite,Object> {
    @Query(value = "{'favourite._id' : {$in : [?0]}, 'email' : {$in : [?1]}}")
    List<Favourite> findFavouriteByMovieIdAndEmail(int movieID, String email);
    List<Favourite> findFavouritesByEmail(String email);
}
