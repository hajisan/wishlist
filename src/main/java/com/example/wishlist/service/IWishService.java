package com.example.wishlist.service;

import com.example.wishlist.model.Wish;

import java.util.List;

public interface IWishService extends IService<Wish, Integer> {

    //------------------------------------ Standardmetoder fra IService ------------------------------------

    Wish create(Wish wish);

    Wish findById(Integer id);

    List<Wish> findAll();

    void deleteById(Integer id);

    Wish update(Wish wish);
}
