package com.example.wishlist.service;


import com.example.wishlist.dto.WishWishListDTO;
import com.example.wishlist.exception.ResourceNotFoundException;
import com.example.wishlist.model.Wish;
import com.example.wishlist.model.WishList;
import com.example.wishlist.repository.IWishListRepository;
import com.example.wishlist.repository.IWishRepository;
import com.example.wishlist.repository.WishRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class WishWishListService {
    private final IWishListRepository iWishListRepository;
    private final IWishRepository iWishRepository;
    private final WishRepository wishRepository;

    public WishWishListService(IWishListRepository iWishListRepository, IWishRepository iWishRepository, WishRepository wishRepository) {
        this.iWishListRepository = iWishListRepository;
        this.iWishRepository = iWishRepository;
        this.wishRepository = wishRepository;
    }

    public WishWishListDTO findWishWithWishList(Integer wishListId) {
        WishList wishList = iWishListRepository.findById(wishListId);

        if (wishList == null) {
            throw new ResourceNotFoundException("Wishlist with ID: " + wishListId + " does not exist");
        }
        List<Wish> wishes = iWishRepository.findByWishListId(wishListId);

        return new WishWishListDTO(wishes, wishList);
    }


    public int findWishListIdByNameAndProfile(String wishlistName, int profileId) {
        WishList wishlist = iWishListRepository.findByNameAndProfile(wishlistName, profileId);

        if (wishlist == null) {
            throw new ResourceNotFoundException("Wishlist with name '" + wishlistName + "' for profile ID " + profileId + " not found.");
        }

        return wishlist.getId();
    }


    public WishWishListDTO getWishListWithWishes(String wishlistName, int profileId) {
        WishList wishList = iWishListRepository.findByNameAndProfile(wishlistName, profileId);

        List<Wish> wishes = wishRepository.findByWishListId(wishList.getId());

        return new WishWishListDTO(wishes, wishList);
    }


}
