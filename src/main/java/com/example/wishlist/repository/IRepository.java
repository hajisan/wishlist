package com.example.wishlist.repository;

import java.util.List;

public interface IRepository<T, Integer> {

    T create(T t);

    T findById(Integer id);

    List<T> findAll();

    void deleteById(Integer id);

    T update(T t);

}