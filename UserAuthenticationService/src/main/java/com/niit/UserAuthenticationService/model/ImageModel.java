package com.niit.UserAuthenticationService.model;

import javax.persistence.*;

@Entity
//@Table(name = "image_table")
public class ImageModel {

    public ImageModel() {
        super();
    }

    public ImageModel(String email, String type, byte[] picByte) {
        this.email = email;
        this.type = type;
        this.picByte = picByte;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String type;
    //image bytes can have large lengths so we specify a value
    //which is more than the default length for picByte column
    @Column(name = "picByte", length = 1000)
    private byte[] picByte;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getPicByte() {
        return picByte;
    }

    public void setPicByte(byte[] picByte) {
        this.picByte = picByte;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}