package com.example.wishlist.service;

import com.example.wishlist.dto.ProfileWishListDTO;
import com.example.wishlist.exception.ResourceNotFoundException;
import com.example.wishlist.model.Profile;
import com.example.wishlist.model.WishList;
import com.example.wishlist.repository.IProfileRepository;
import com.example.wishlist.repository.IWishListRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProfileWishListService {

    private final IProfileRepository iProfileRepository;
    private final IWishListRepository iWishListRepository;

    public ProfileWishListService(IProfileRepository iProfileRepository, IWishListRepository iWishListRepository) {
        this.iProfileRepository = iProfileRepository;
        this.iWishListRepository = iWishListRepository;
    }

    public ProfileWishListDTO findProfileWithWishLists(Integer profileId) {
        Profile profile = iProfileRepository.findById(profileId);

        if (profile == null) {
            throw new ResourceNotFoundException("Profile with ID: " + profileId + " not found.");

        }
        List<WishList> wishLists = iWishListRepository.findByProfileId(profileId);

        return new ProfileWishListDTO(profile, wishLists);
    }

}
