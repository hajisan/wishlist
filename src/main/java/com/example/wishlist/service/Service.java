package com.example.wishlist.service;

import com.example.wishlist.repository.ProfileRepository;

@org.springframework.stereotype.Service

public class Service {

    private final ProfileRepository profileRepository;

    public Service(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }
}
