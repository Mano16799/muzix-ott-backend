package com.niit.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class User {

    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String mobileNo;

}