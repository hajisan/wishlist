package com.example.wishlist.model;

import java.time.LocalDate;
public class Profile {

    private String name, email, username, password;
    private LocalDate birthday;
    private int id;

    public Profile(int id, String name, LocalDate birthday, String email, String username, String password) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    //MÅ KUN BRUGES TIL CREATE, ELLERS KOMMER ID IKKE MED
    public Profile(String name, LocalDate birthday, String email, String username, String password) {
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    public Profile(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Profile() {
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public static LocalDate getStringAsLocalDate(String date) {
        return LocalDate.of(Integer.parseInt(date.split("-")[0]), Integer.parseInt(date.split("-")[1]), Integer.parseInt(date.split("-")[2]));
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

    @Override
    public String toString() {
        return String.format("""
                Name    : %s
                Email   : %s
                Username: %s
                Password: %s
                Birthday: %s
                ID      : %d""", name, email, username, password, birthday, id);
    }
}

