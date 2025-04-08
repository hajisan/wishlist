package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;

import java.util.List;

public interface IWishRepository extends IRepository<Wish, Integer> {

    Wish create(Wish t);

    List<Wish> findAll();

    Wish findById(Integer id);


    Wish update(Wish t);

    void deleteById(Integer id);

    List<Wish> findByWishListId(Integer wishListId);
}
