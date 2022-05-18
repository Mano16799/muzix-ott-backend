package com.niit.model;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Document
public class Favourite {
    @Id
    private String id;
    private String email;
    private Movie favourite;

    public Favourite(String email, Movie favourite) {
        this.email = email;
        this.favourite = favourite;
    }
}
