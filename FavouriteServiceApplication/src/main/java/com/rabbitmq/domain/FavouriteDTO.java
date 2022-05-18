package com.rabbitmq.domain;
import com.niit.model.Movie;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class FavouriteDTO {
    private String id;
    private String email;
    MovieDTO favourite;

    public FavouriteDTO(String email, MovieDTO favourite) {
        this.email = email;
        this.favourite = favourite;
    }
}
