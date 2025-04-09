package com.example.wishlist.repository;

import com.example.wishlist.model.WishList;

import java.util.List;

public interface IWishListRepository extends IRepository<WishList, Integer> {

    WishList create(WishList wishList);

    List<WishList> findAll();

    WishList findById(Integer id);

    WishList update(WishList wishList);

    void deleteById(Integer id);

    List<WishList> findWishListsByProfileId(Integer profileId);
    WishList findWishListByWishListNameAndProfileId(String name, Integer id);

}
