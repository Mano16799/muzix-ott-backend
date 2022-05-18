package com.niit.UserAuthenticationService.model;


import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class User {

    @Id
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String mobileNo;



}