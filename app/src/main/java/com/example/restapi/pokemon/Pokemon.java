package com.example.restapi.pokemon;

public class Pokemon {

    private int id;
    private String name, img, height, weight;

    public Pokemon() {

    }

    public Pokemon(String img) {
        this.img = img;
    }


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


}
