package com.example.wishlist.dto;

import com.example.wishlist.model.Profile;
import com.example.wishlist.model.WishList;

import java.util.*;

//For at returnere én profil + alle dens ønskelister i ét samlet JSON-respons = Separation of Concerns

public class ProfileWishListDTO {

    private Profile profile;
    private List<WishList> wishLists;

    public ProfileWishListDTO(Profile profile, List<WishList> wishLists) {
        this.profile = profile;
        this.wishLists = wishLists;
    }
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<WishList> getWishLists() {
        return wishLists;
    }

    public void setWish(List<WishList> wishLists) {
        this.wishLists = wishLists;
    }

    @Override
    public String toString() {
        return "Profile: " + profile + ". Wishlists " + wishLists;
    }


}
