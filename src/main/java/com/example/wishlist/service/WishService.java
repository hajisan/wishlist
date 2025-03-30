package com.example.wishlist.service;

import com.example.wishlist.repository.WishRepository;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

}
