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

//    public int findWishListIdByNameAndProfile(String wishlistName, int profileId) {
//        WishList wishlist = iWishListRepository.findByNameAndProfile(wishlistName, profileId);
//
//        if (wishlist == null) {
//            throw new ResourceNotFoundException("Wishlist with name '" + wishlistName + "' for profile ID " + profileId + " not found.");
//        }
//
//        return wishlist.getId();
//    }


//    public WishList findSpecificWishListByProfileId(int wishlistId, int profileId) {
//        for (WishList wishList : findProfileWithWishLists(profileId).wishLists()) {
//            if (wishlistId == wishList.getId()) {
//                return wishList;
//            }
//        }
//
//        throw new ResourceNotFoundException("No wish list found by that name.");
//    }

}


