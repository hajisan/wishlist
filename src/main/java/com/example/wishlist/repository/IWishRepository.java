package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;

import java.util.List;

public interface IWishRepository extends IRepository<Wish, Integer> {
    void save(Wish t);
    Wish create(Wish t);
    Wish findById(Integer id);
    List<Wish> findAll();
    void deleteById(Integer id);
    Wish update(Wish t);
    List<Wish> findByWishListId(Integer wishListId);
}
