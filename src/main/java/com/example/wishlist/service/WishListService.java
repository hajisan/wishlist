package com.example.wishlist.service;

import com.example.wishlist.exception.ResourceNotFoundException; // Vores egen custom exception
import com.example.wishlist.model.WishList;
import com.example.wishlist.repository.IWishListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService implements IWishListService {

    private final IWishListRepository iWishListRepository;

    // Injection af IWishListRepository-interface i constructor
    public WishListService(IWishListRepository iWishListRepository) {
        this.iWishListRepository = iWishListRepository;
    }

    //------------------------------------ Create() ------------------------------------

    @Override
    public WishList create(WishList wishList) {
        return iWishListRepository.create(wishList);
    }

    //------------------------------------ Read() ------------------------------------

    @Override
    public List<WishList> findAll() {
        return iWishListRepository.findAll();
    }

    @Override
    public WishList findById(Integer id) {
        WishList wishList = iWishListRepository.findById(id);

        if (wishList == null) {
            throw new ResourceNotFoundException(("Wish List " + id + " not found."));
        }
        return wishList;
    }

    //------------------------------------ Update() ------------------------------------


    @Override
    public WishList update(WishList wishList) {
        if (wishList == null) {
            throw new ResourceNotFoundException("Wish list does not exist");
        }
        return iWishListRepository.update(wishList);
    }

    //------------------------------------ Delete() ------------------------------------

    @Override
    public void deleteById(Integer id) {
        WishList wishList = iWishListRepository.findById(id); // Finder id

        if (wishList == null) {
            throw new ResourceNotFoundException("You cannot delete a non-existing wish list with ID: " + id);
        }
        iWishListRepository.deleteById(id); // Sletter hvis det findes
    }
}
