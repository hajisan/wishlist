package com.example.wishlist.dto;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.WishList;

import java.util.List;

//Returnerer én ønskeliste med alle dens ønsker i et samlet JSON-respons = Separation of Concerns
public class WishWishListDTO {

    private List<Wish> wishes;
    private WishList wishList;

    public WishWishListDTO(List<Wish> wishes, WishList wishList) {
        this.wishes = wishes;
        this.wishList = wishList;
    }

    public List<Wish> getWishes() {
        return wishes;
    }

    public void setWishes(List<Wish> wishes) {
        this.wishes = wishes;
    }

    public WishList getWishList() {
        return wishList;
    }

    public void setWishList(WishList wishList) {
        this.wishList = wishList;
    }

    @Override
    public String toString() {
        return "Wishlist " + wishList + ". Wishes " + wishes;
    }




}
