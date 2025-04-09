package com.example.wishlist.service;

import com.example.wishlist.exception.ResourceNotFoundException; // Vores egen custom exception
import com.example.wishlist.model.Wish;
import com.example.wishlist.repository.IWishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class WishService implements IWishService {

    // Injection af IWishRepository-interface i constructor
    private final IWishRepository iWishRepository;

    public WishService(IWishRepository iWishRepository) {
        this.iWishRepository = iWishRepository;
    }

    //------------------------------------ Create() ------------------------------------

    @Override
    public Wish create(Wish wish) {

        return iWishRepository.create(wish);
    }

    //------------------------------------ Read() ------------------------------------

    @Override
    public List<Wish> findAll() {
        return iWishRepository.findAll();
    }

    @Override
    public Wish findById(Integer id) {

        Wish wish = iWishRepository.findById(id);

        if (wish == null) {
            throw new ResourceNotFoundException("Wish with ID " + id + " not found.");
        }
        return wish;
    }

    //------------------------------------ Update() ------------------------------------


    @Override
    public Wish update(Wish wish) {

        if (wish == null) {
            throw new ResourceNotFoundException("Wish does not exist");
        }

        return iWishRepository.update(wish);
    }

    //------------------------------------ Delete() ------------------------------------

    @Override
    public void deleteById(Integer id) {

        Wish wish = iWishRepository.findById(id); //Finder id

        if (wish == null) {
            throw new ResourceNotFoundException("You cannot delete a non-existing wish with ID: " + id);
        }
        iWishRepository.deleteById(id); //Sletter hvis det findes
    }
}