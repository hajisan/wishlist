package com.example.wishlist.service;

import com.example.wishlist.dto.WishWishListDTO;
import com.example.wishlist.exception.ResourceNotFoundException; // Vores egen custom exception
import com.example.wishlist.model.Wish;
import com.example.wishlist.model.WishList;
import com.example.wishlist.repository.IWishListRepository;
import com.example.wishlist.repository.IWishRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WishWishListService { // Samler Wish- og WishList-klasser til DTO (Data Transfer Object)
    private final IWishListRepository iWishListRepository;
    private final IWishRepository iWishRepository;

    public WishWishListService(IWishListRepository iWishListRepository, IWishRepository iWishRepository) {
        this.iWishListRepository = iWishListRepository;
        this.iWishRepository = iWishRepository;
    }

    //------------------------------------ Read Wishes fra en WishList() ------------------------------------

    public WishWishListDTO findWishWithWishList(Integer wishListId) {
        WishList wishList = iWishListRepository.findById(wishListId);

        if (wishList == null) {
            throw new ResourceNotFoundException("Wishlist with ID: " + wishListId + " does not exist");
        }
        List<Wish> wishes = iWishRepository.findWishesByWishListId(wishListId);

        return new WishWishListDTO(wishes, wishList);
    }
}
