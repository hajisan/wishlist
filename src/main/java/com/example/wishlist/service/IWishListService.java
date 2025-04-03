package com.example.wishlist.service;

import com.example.wishlist.model.WishList;

import java.util.List;

public interface IWishListService extends IService<WishList, Integer> {
    void save(WishList wishList);

    void create(WishList wishList);

    WishList findById(Integer id);

    List<WishList> findAll();

    void deleteById(Integer id);

    void update(WishList wishList);

}
