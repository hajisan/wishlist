package com.example.wishlist.service;

import com.example.wishlist.model.Wish;
import com.example.wishlist.repository.ProfileRepository;
import com.example.wishlist.repository.WishListRepository;
import com.example.wishlist.repository.WishRepository;

import java.util.List;

@org.springframework.stereotype.Service

public class ApplicationService {

    private final ProfileRepository profileRepository;
    private final WishListRepository wishListRepository;
    private final WishRepository wishRepository;

    public ApplicationService(ProfileRepository profileRepository, WishListRepository wishListRepository, WishRepository wishRepository) {
        this.profileRepository = profileRepository;
        this.wishListRepository = wishListRepository;
        this.wishRepository = wishRepository;
    }

    // ------------------------
    // Profile-relateret logik
    // ------------------------

            // Metoder

    // ------------------------
    // WishList-relateret logik
    // ------------------------

            // Metoder

    // ------------------------
    // Wish-relateret logik
    // ------------------------

    public Wish createWish(Wish wish) {
        wishRepository.save(wish);
        return wishRepository.findById(wish.getId());
    }

    public List<Wish> getAllWishes() {
        
    }




}
