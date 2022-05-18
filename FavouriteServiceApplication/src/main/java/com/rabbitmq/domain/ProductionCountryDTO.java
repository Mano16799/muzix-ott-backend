
package com.rabbitmq.domain;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class ProductionCountryDTO {

    public String iso_3166_1;
    public String name;

}
