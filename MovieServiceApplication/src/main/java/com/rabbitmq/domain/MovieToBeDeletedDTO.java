package com.rabbitmq.domain;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieToBeDeletedDTO {
    private String email;
    private int _id;
}
