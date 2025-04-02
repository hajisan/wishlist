package com.example.wishlist.repository;

import java.util.List;

public interface IWishRepository<Wish, Integer> extends IRepository<Wish, Integer> {
    void save(Wish t);
    void create(Wish t);
    Wish findById(Integer id);
    List<Wish> findAll();
    void deleteById(Integer id);
    void update(Wish t);
}
