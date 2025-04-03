package com.example.wishlist.repository;

import com.example.wishlist.model.WishList;

import java.util.List;

public interface IWishListRepository extends IRepository<WishList, Integer> {
    void save(WishList t);
    void create(WishList t);
    WishList findById(Integer id);
    List<WishList> findAll();
    void deleteById(Integer id);
    void update(WishList t);

}
