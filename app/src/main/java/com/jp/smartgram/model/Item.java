package com.jp.smartgram.model;

public class Item {
    String id;
    String name;
    String image;
    String button;

    public Item(String id, String name, String image, String button) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.button = button;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }
}
