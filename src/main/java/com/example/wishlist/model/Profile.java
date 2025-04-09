package com.example.wishlist.model;

import java.time.LocalDate;

public class Profile {

    private String name, email, userName, password;
    private LocalDate birthday;
    private int id;

//---------------------------------------------------------------------------------------------------
//--------------------------------------     Constructors     ---------------------------------------
//---------------------------------------------------------------------------------------------------

    public Profile(int id, String name, LocalDate birthday, String email, String userName, String password) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    // Bruges kun til create() - ellers kommer id ikke med
    public Profile(String name, LocalDate birthday, String email, String userName, String password) {
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public Profile(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Profile() {
    }

//---------------------------------------------------------------------------------------------------
//-----------------------------------     Getters og Setters     ------------------------------------
//---------------------------------------------------------------------------------------------------

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

//---------------------------------------------------------------------------------------------------
//-----------------------------     Statiske metoder og toString()     ------------------------------
//---------------------------------------------------------------------------------------------------

    // Statisk metode, da vi vil kunne bruge den uanset om vi har en instans af Profile eller ej
    public static LocalDate getLocalDateFromString(String date) {
        return LocalDate.of(
                Integer.parseInt(date.split("-")[0]),
                Integer.parseInt(date.split("-")[1]),
                Integer.parseInt(date.split("-")[2])
        );
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