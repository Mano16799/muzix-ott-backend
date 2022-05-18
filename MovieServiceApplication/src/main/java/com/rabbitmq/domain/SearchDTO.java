package com.rabbitmq.domain;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchDTO {
    private String key,genre;
}
