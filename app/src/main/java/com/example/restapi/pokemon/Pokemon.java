package com.example.restapi.pokemon;

public class Pokemon {

    private int id;
    private String name, img, height, weight;

    public Pokemon() {

    }

    public Pokemon(int id, String name, String img, String height, String weight) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.height = height;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
