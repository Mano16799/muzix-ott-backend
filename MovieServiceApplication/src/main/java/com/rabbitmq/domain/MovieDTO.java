
package com.rabbitmq.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class MovieDTO {
    private int _id;
    private boolean adult;
    private String backdrop_path;
    private Object belongs_to_collection;
    private int budget;
    private List<GenreDTO> genres = null;
    private String homepage;
    private String imdb_id;
    private String original_language;
    private String original_title;
    private String overview;
    private double popularity;
    private String poster_path;
    private List<ProductionCompanyDTO> production_companies = null;
    private List<ProductionCountryDTO> production_countries = null;
    private String release_date;
    private int revenue;
    private int runtime;
    private List<SpokenLanguageDTO> spoken_languages = null;
    private String status;
    private String tagline;
    private String title;
    private boolean video;
    private double vote_average;
    private int vote_count;

}