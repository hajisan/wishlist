package com.example.wishlist.model;

import java.util.List;

import java.time.LocalDate;

public class Profile {


    private String name, email, userName, password;
    private LocalDate birthday;
    private int id;
    private List<WishList> wishLists;

    public Profile(int id, String name, LocalDate birthday, String email, String userName, String password, List<WishList> wishLists) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.wishLists = wishLists;
    }

    public Profile(String name, LocalDate birthday, String email, String userName, String password, List<WishList> wishLists) {
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.wishLists = wishLists;
    }

    public Profile(String name, LocalDate birthday, String email, String userName, String password) {
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public Profile() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<WishList> getWishLists() {
        return wishLists;
    }

    public void setWishLists(List<WishList> wishLists) {
        this.wishLists = wishLists;
    }

    @Override
    public String toString() {
        return String.format("""
                Name    : %s
                Email   : %s
                Username: %s
                Password: %s
                Birthday: %s
                ID      : %d""", name, email, userName, password, birthday, id);
    }


}
