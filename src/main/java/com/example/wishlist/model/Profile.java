package com.example.wishlist.model;



import java.time.LocalDate;

public class Profile {


    private String name, email, userName, password;
    private LocalDate birthday;
    private int id;

    public Profile(String name, String email, String userName, String password, LocalDate birthday, int id) {
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.birthday = birthday;
        this.id = id;
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
