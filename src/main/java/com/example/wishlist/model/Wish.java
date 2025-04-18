package com.example.wishlist.model;

public class Wish {

    private String name, description, link;
    private int id, quantity, wishListId;
    private double price;

//---------------------------------------------------------------------------------------------------
//--------------------------------------     Constructors     ---------------------------------------
//---------------------------------------------------------------------------------------------------

    public Wish(String name, String description, String link, int id, int quantity, double price, int wishListId) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.wishListId = wishListId;
    }

    // Konstruktør uden id, da id'et bliver autogenereret af databasen. Bruges kun til create()
    public Wish(String name, String description, String link, int quantity, double price, int wishListId) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.quantity = quantity;
        this.price = price;
        this.wishListId = wishListId;
    }

    public Wish() {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getWishListId() {
        return wishListId;
    }

    public void setWishListId(int wishListId) {
        this.wishListId = wishListId;
    }

//---------------------------------------------------------------------------------------------------
//-----------------------------     Statiske metoder og toString()     ------------------------------
//---------------------------------------------------------------------------------------------------

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