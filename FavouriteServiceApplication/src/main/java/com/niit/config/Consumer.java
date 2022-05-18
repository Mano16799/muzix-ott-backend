package com.niit.config;

import com.niit.exception.FavouriteAlreadyExistException;
import com.niit.model.*;
import com.niit.service.FavouriteServiceImpl;
import com.rabbitmq.domain.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Consumer {
    @Autowired
    private FavouriteServiceImpl favouriteService;

    @RabbitListener(queues = "fav_queue")
    public Object getFavDtoFromRabbitMq(FavouriteDTO favouriteDTO) {
        Favourite favourite = new Favourite();

        favourite.setEmail(favouriteDTO.getEmail());
        Movie movie = new Movie();
        movie.set_id(favouriteDTO.getFavourite().get_id());
        movie.setAdult(favouriteDTO.getFavourite().isAdult());
        movie.setBackdrop_path(favouriteDTO.getFavourite().getBackdrop_path());
        movie.setBelongs_to_collection(favouriteDTO.getFavourite().getBelongs_to_collection());
        movie.setBudget(favouriteDTO.getFavourite().getBudget());
        List<Genre> genres = new ArrayList<>();
        for (GenreDTO genre : favouriteDTO.getFavourite().getGenres()) {
            Genre genre1 = new Genre(genre.getId(), genre.getName());
            genres.add(genre1);
        }
        movie.setGenres(genres);
        movie.setHomepage(favouriteDTO.getFavourite().getHomepage());
        movie.setImdb_id(favouriteDTO.getFavourite().getImdb_id());
        movie.setOriginal_language(favouriteDTO.getFavourite().getOriginal_language());
        movie.setOriginal_title(favouriteDTO.getFavourite().getOriginal_title());
        movie.setOverview(favouriteDTO.getFavourite().getOverview());
        movie.setPopularity(favouriteDTO.getFavourite().getPopularity());
        movie.setPoster_path(favouriteDTO.getFavourite().getPoster_path());

        List<ProductionCompany> productionCompanies = new ArrayList<>();
        for (ProductionCompanyDTO productionCompanyDTO : favouriteDTO.getFavourite().getProduction_companies()) {
            ProductionCompany productionCompany = new ProductionCompany(productionCompanyDTO.getId(), productionCompanyDTO.getLogo_path(), productionCompanyDTO.getName(), productionCompanyDTO.origin_country);
            productionCompanies.add(productionCompany);
        }

        movie.setGenres(genres);
        movie.setProduction_companies(productionCompanies);

        List<ProductionCountry> productionCountries = new ArrayList<>();
        for (ProductionCountryDTO productionCountryDTO : favouriteDTO.getFavourite().getProduction_countries()) {
            ProductionCountry productionCountry = new ProductionCountry(productionCountryDTO.getName(), productionCountryDTO.getIso_3166_1());
            productionCountries.add(productionCountry);
        }

        movie.setProduction_countries(productionCountries);
        movie.setRelease_date(favouriteDTO.getFavourite().getRelease_date());
        movie.setRevenue(favouriteDTO.getFavourite().getRevenue());
        movie.setRuntime(favouriteDTO.getFavourite().getRuntime());

        List<SpokenLanguage> spokenLanguages = new ArrayList<>();
        for (SpokenLanguageDTO spokenLanguageDTO : favouriteDTO.getFavourite().getSpoken_languages()) {
            SpokenLanguage spokenLanguage = new SpokenLanguage(spokenLanguageDTO.getEnglish_name(), spokenLanguageDTO.getIso_639_1(), spokenLanguageDTO.getName());
            spokenLanguages.add(spokenLanguage);
        }

        movie.setSpoken_languages(spokenLanguages);
        movie.setStatus(favouriteDTO.getFavourite().getStatus());
        movie.setTagline(favouriteDTO.getFavourite().getTagline());
        movie.setTitle(favouriteDTO.getFavourite().getTitle());
        movie.setVideo(favouriteDTO.getFavourite().isVideo());
        movie.setVote_average(favouriteDTO.getFavourite().getVote_average());
        movie.setVote_count(favouriteDTO.getFavourite().getVote_count());

        System.out.println("received from client " + favouriteDTO);
        favourite.setFavourite(movie);
        Favourite favourite1 = favouriteService.addToFavourites(favourite);
        System.out.println("sent back to client " + favourite1);
        return favourite1;
    }

    @RabbitListener(queues = "get_fav_queue")
    public Object getAllFavouritesByEmail(String email) {
        System.out.println("inside consumer " + email);
        return favouriteService.getAllFavouriteByID(email);
    }


    @RabbitListener(queues = "del_fav_queue")
    public Object deleteFavourite(MovieToBeDeletedDTO movie) {
        System.out.println("hi");
        String email = movie.getEmail();
        int id = movie.get_id();
        boolean result = favouriteService.deleteFavourite(email, id);
        System.out.println("Deletion done -> " + result);
        return result;
    }

    @RabbitListener(queues = "del_all_fav_queue")
    public Object deleteAllFavourite(String email) {
        boolean result = favouriteService.deleteFavouriteByEmail(email);
        System.out.println("Deletion done -> " + result);
        return result;
    }

    @RabbitListener(queues = "get_fav_genres_queue")
    public Object getAllFavGenres(String email){
        return favouriteService.getAllFavouriteGenresByID(email);
    }
}








