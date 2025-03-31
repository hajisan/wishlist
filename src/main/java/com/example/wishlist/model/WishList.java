package com.example.wishlist.model;
import java.util.List;

public class WishList {

    private String name, description;
    private int id;
    private List<Wish> wishes;


    public WishList(List<Wish> wishes, int id, String name, String description) {
        this.wishes = wishes;
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Wish> getWishes() {
        return wishes;
    }

    public void setWishes(List<Wish> wishes) {
        this.wishes = wishes;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Name " + name + " ID " + id + " Wishes " + wishes;
    }


}
