package com.example.wishlist.service;

import com.example.wishlist.exception.ResourceNotFoundException;
import com.example.wishlist.model.WishList;
import com.example.wishlist.repository.IWishListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService implements IWishListService {

    private final IWishListRepository iWishListRepository;

    public WishListService(IWishListRepository iWishListRepository) {
        this.iWishListRepository = iWishListRepository;
    }

    @Override
    public void save(WishList wishList) {
        iWishListRepository.save(wishList);
    }

    @Override
    public void create(WishList wishList) {
        iWishListRepository.create(wishList);
    }

    @Override
    public WishList findById(Integer id) {
        WishList wishList = iWishListRepository.findById(id);

        if (wishList == null ) {
            throw new ResourceNotFoundException(("Wish List " + id + " not found."));
        }
        return wishList;
    }

    @Override
    public List<WishList> findAll() {
        return iWishListRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        WishList wishList = iWishListRepository.findById(id); // Finder id

        if (wishList == null) {
            throw new ResourceNotFoundException("You cannot delete a non-existing wish list with ID: " + id);
        }
        iWishListRepository.deleteById(id); // Sletter hvis det findes

    }

    @Override
    public void update(WishList wishList) {
        if (wishList == null) {
            throw new ResourceNotFoundException("Wish list does not exist");
        }
        iWishListRepository.update(wishList);
    }
}
