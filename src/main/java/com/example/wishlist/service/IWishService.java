package com.example.wishlist.service;

import com.example.wishlist.model.Wish;

import java.util.List;

public interface IWishService extends IService<Wish, Integer> {
    void save(Wish t);

    void create(Wish t);

    Wish findById(Integer id);

    List<Wish> findAll();

    void deleteById(Integer id);

    void update(Wish t);
}
