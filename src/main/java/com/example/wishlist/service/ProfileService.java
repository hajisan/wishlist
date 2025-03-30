package com.example.wishlist.service;

import com.example.wishlist.repository.ProfileRepository;
import org.springframework.stereotype.Service;

@Service

public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }
}
