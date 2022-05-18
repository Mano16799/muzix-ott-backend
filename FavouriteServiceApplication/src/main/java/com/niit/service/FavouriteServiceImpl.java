package com.niit.service;

import com.niit.exception.FavouriteAlreadyExistException;
import com.niit.model.Favourite;
import com.niit.model.Genre;
import com.niit.model.Movie;
import com.niit.repository.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavouriteServiceImpl implements FavouriteService {
    private FavouriteRepository favouriteRepository;

    @Autowired
    public FavouriteServiceImpl(FavouriteRepository favouriteRepository) {
        this.favouriteRepository = favouriteRepository;
    }


    @Override
    public Favourite addToFavourites(Favourite favourite) {
        return favouriteRepository.save(favourite);
    }


    @Override
    public List<Favourite> getAllFavouriteByID(String email) {

        return favouriteRepository.findFavouritesByEmail(email);
    }

    @Override
    public String getAllFavouriteGenresByID(String email) {
        List<Favourite> favourites = favouriteRepository.findFavouritesByEmail(email);
        String genres = "";
        if (favourites.size() > 0) {
            for (int i = 0; i < favourites.size(); i++) {
                if (!genres.contains(favourites.get(i).getFavourite().getGenres().get(0).getName()))
                    genres = genres + favourites.get(i).getFavourite().getGenres().get(0).getName() + " ";
            }
        }
        System.out.println(genres);
        return genres;
    }

    @Override
    public List<Favourite> getAllFavourites() {
        return favouriteRepository.findAll();
    }

    @Override
    public boolean deleteFavourite(String email, int movieID) {
        if (favouriteRepository.findFavouriteByMovieIdAndEmail(movieID, email).size() == 0) {
            return false;
        }
        System.out.println("movie id " + movieID + "email " + email);
        List<Favourite> favourites = favouriteRepository.findFavouriteByMovieIdAndEmail(movieID, email);
        favourites.forEach(f -> System.out.println(f));

        if (favourites.size() > 0) {
            favouriteRepository.delete(favourites.get(0));
            System.out.println("deleted");
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteFavouriteByEmail(String email) {
        if (favouriteRepository.findFavouritesByEmail(email).size() == 0) {
            return false;
        }
        System.out.println("email " + email);
        List<Favourite> favourites = favouriteRepository.findFavouritesByEmail(email);
        favourites.forEach(f -> System.out.println(f));

        if (favourites.size() > 0) {
            favouriteRepository.deleteAll(favourites);
            System.out.println("deleted");
            return true;
        } else {
            return false;

        }
    }
}
