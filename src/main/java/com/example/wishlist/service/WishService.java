package com.example.wishlist.service;

import com.example.wishlist.exception.ResourceNotFoundException;
import com.example.wishlist.model.Wish;
import com.example.wishlist.repository.IWishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class WishService implements IWishService {

    private IWishRepository iWishRepository;

    public WishService(IWishRepository iWishRepository) {
        this.iWishRepository = iWishRepository;
    }


    @Override
    public void save(Wish wish) {

        iWishRepository.save(wish);

    }

    @Override
    public void create(Wish wish) {

        iWishRepository.create(wish);

    }

    @Override
    public Wish findById(Integer id) {

        Wish wish = iWishRepository.findById(id);

        if (wish == null) {
            throw new ResourceNotFoundException("Wish with ID " + id + " not found.");
        }
        return wish;
    }

    @Override
    public List<Wish> findAll() {
        return iWishRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) {

        Wish wish = iWishRepository.findById(id); //Finder id

        if (wish == null) {
            throw new ResourceNotFoundException("You cannot delete a non-existing wish with ID: " + id);
        }
        iWishRepository.deleteById(id); //Sletter hvis det findes

   }

    @Override
    public void update(Wish wish) {

        if (wish == null) {
            throw new ResourceNotFoundException("Wish does not exist");
        }

        iWishRepository.update(wish);

    }
}
