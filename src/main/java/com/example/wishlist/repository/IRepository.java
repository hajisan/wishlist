package com.example.wishlist.repository;

import java.util.*;

public interface IRepository<T, Integer> {

    public void save(T t);

    public void create(T t);

    public T findById(Integer id);

    public List<T> findAll();

    public void deleteById(Integer id);

    public void update(T t);

}