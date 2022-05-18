package com.niit.service;

import com.niit.exception.MovieNotFoundException;
import com.niit.model.Genre;
import com.niit.model.Movie;
import com.niit.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    private MovieRepository movieRepository;

    @Autowired
    public SearchServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> searchAllMoviesBasedOnKeyword(String keyword) throws MovieNotFoundException {
//        List<Movie> foundMovies =  movieRepository.findAllMovieByTitleContainingIgnoreCase(keyword);
//        if(foundMovies.size()==0){
//            throw new MovieNotFoundException();
//        }
        return movieRepository.findAllMovieByTitleContainingIgnoreCase(keyword);
    }

    @Override
    public List<Movie> searchAllMoviesBasedOnKeywordAndGenre(String keyword, String genre) throws MovieNotFoundException {
        List<Movie> movies = movieRepository.findAllMovieByTitleContainingIgnoreCase(keyword);
        List<Movie> filteredMovies = new ArrayList<>();
        for (Movie m : movies) {
            List<Genre> genres = m.getGenres();
            for (Genre g : genres) {
                if (g.getName().equals(genre)) {
                    filteredMovies.add(m);
                }
            }
        }
//        if(filteredMovies.size()==0){
//            throw new MovieNotFoundException();
//        }
        return filteredMovies;
    }

    //other similar movies
//    @Override
//    public List<Movie> searchRecommendedMoviesOnSearch(String key) throws MovieNotFoundException {
//        if (movieRepository.findAllMovieByTitleContainingIgnoreCase(key).size() == 0) {
//            throw new MovieNotFoundException();
//        }
//        Movie firstSuggestion = movieRepository.findAllMovieByTitleContainingIgnoreCase(key).get(0);
//        List<Movie> allMovies = movieRepository.findAll();
//        int count = 0;
//        List<Genre> genres = firstSuggestion.getGenres();
//        genres.forEach(s-> System.out.println(s));
//        for (int i = 0; i < allMovies.size(); i++) {
//            for (Genre genre : allMovies.get(i).getGenres()) {
//                if (!genres.contains(genre)) {
//                    allMovies.remove(i);
//                    count++;
//                    break;
//                }
//            }
//        }
//        System.out.println(count+" no of mov "+allMovies.size());
//        return allMovies;
//    }
}


//        if (movieRepository.findAllMovieByTitleContainingIgnoreCase(title).size() == 0) {
//            throw new MovieNotFoundException();
//        }
//        List<Genre> genres = movieRepository.findAllMovieByTitleContainingIgnoreCase(title).get(0).getGenres();
//        List<Movie> recommendedMovies = movieRepository.findAllMovieByGenre(genres.get(0).getName());
//        System.out.println(recommendedMovies.size());
//        for (int i = 0; i < recommendedMovies.size(); i++) {
//            for(int k=0; k<recommendedMovies.get(i).getGenres().size(); k++){
//                if(!genres.contains(recommendedMovies.get(i).getGenres().get(k))){
//                    recommendedMovies.remove(i);
//                }
//            }
//        }
//        return recommendedMovies;