
package com.rabbitmq.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class SpokenLanguageDTO {

    public String english_name;
    public String iso_639_1;
    public String name;

}
