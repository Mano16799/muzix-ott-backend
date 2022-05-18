package com.niit.model;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class ProductionCompany {

    private int id;
    private String logo_path;
    private String name;
    private String origin_country;

}
