package com.example.wishlist.repository;

import com.example.wishlist.model.WishList;

import java.util.List;

public interface IWishListRepository extends IRepository<WishList, Integer> {
    void save(WishList wishList);
    void create(WishList wishList);
    WishList findById(Integer id);
    List<WishList> findAll();
    void deleteById(Integer id);
    void update(WishList wishList);
    List<WishList> findByProfileId(Integer profileId);
    WishList findByNameAndProfile(String name, Integer id);

}
