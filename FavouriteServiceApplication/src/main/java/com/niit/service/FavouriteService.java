package com.niit.service;


import com.niit.exception.FavouriteAlreadyExistException;
import com.niit.model.Favourite;

import java.util.List;


public interface FavouriteService {

    Favourite addToFavourites(Favourite favourite) throws FavouriteAlreadyExistException;

    List<Favourite> getAllFavouriteByID(String email);

    List<Favourite> getAllFavourites();

    boolean deleteFavourite(String email, int movieID) throws FavouriteAlreadyExistException;

    boolean deleteFavouriteByEmail(String email);

    String getAllFavouriteGenresByID(String email);
}
