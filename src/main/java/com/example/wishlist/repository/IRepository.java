package com.example.wishlist.repository;

import java.util.*;

public interface IRepository<T, Integer> {

    public boolean save(T t);

    public boolean create(T t);

    public T findById(Integer id);

    public List<T> findAll();

    public boolean deleteById(Integer id);

    public boolean update(T t);

}