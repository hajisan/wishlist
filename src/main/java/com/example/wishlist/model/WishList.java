package com.example.wishlist.model;


public class WishList {

    private String name, description;
    private int id, profileId;

    public WishList(int id, String name, String description, int profileId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.profileId = profileId;
    }

    public WishList(String name, String description) {
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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    @Override
    public String toString() {
        return "Name " + name + " ID " + id;
    }


}
