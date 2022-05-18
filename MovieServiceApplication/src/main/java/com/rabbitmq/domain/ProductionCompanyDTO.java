package com.rabbitmq.domain;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class ProductionCompanyDTO {

    public int id;
    public String logo_path;
    public String name;
    public String origin_country;

}
