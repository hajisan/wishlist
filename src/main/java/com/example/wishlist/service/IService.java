package com.example.wishlist.service;

import java.util.List;

public interface IService<T, Integer> {
    void save(T t);

    T create(T t);

    T findById(Integer id);

    List<T> findAll();

    void deleteById(Integer id);

    void update(T t);

}

