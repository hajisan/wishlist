package com.example.wishlist.repository;

import java.util.List;

public interface IRepository<T, Integer> {

    T create(T t);

    List<T> findAll();

    T findById(Integer id);

    T update(T t);

    void deleteById(Integer id);


}