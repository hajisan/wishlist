package com.example.wishlist.dto;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.WishList;
import java.util.List;

//Returnerer én ønskeliste med alle dens ønsker i et samlet JSON-respons = Separation of Concerns
public record WishWishListDTO(List<Wish> wishes, WishList wishList) {
}

// Record har implicit konstruktør, getters og setters
