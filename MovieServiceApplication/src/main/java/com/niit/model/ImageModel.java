package com.niit.model;

public class ImageModel {

    public ImageModel() {
        super();
    }

    public ImageModel(String email, String type, byte[] picByte) {
        this.email = email;
        this.type = type;
        this.picByte = picByte;
    }
    private Long id;
    private String email;
    private String type;
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