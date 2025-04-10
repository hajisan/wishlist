package com.example.wishlist.service;

import java.util.List;

public interface IService<T, Integer> { // Vi bruger wrapper-klasse til int, da vi ikke kan bruge den rå datatype

    T create(T t);

    T findById(Integer id);

    List<T> findAll();

    void deleteById(Integer id);

    T update(T t);
}

