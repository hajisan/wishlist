package com.example.wishlist.model;

public class Wish {

    private String name, description, link;
    private int id;
    private double price;

    public Wish(String name, String description, String link, int id, double price) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.id = id;
        this.price = price;
    }
    public Wish() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("""
                Name       : %s
                Description: %s
                Link       : %s
                ID         : %d
                price      : %f""", name, description, link, id, price);
    }

}
