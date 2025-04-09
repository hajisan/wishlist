package com.example.wishlist.dto;

import com.example.wishlist.model.Profile;
import com.example.wishlist.model.WishList;

import java.util.List;

// Returnerer én profil + alle dens ønskelister i ét samlet JSON-respons = Separation of Concerns

// Record har implicit konstruktør, getters og setters
public record ProfileWishListDTO(Profile profile, List<WishList> wishLists) {
}


