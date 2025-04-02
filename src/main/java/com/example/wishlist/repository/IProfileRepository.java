package com.example.wishlist.repository;


import java.util.List;

public interface IProfileRepository<Profile, Integer> extends IRepository<Profile, Integer> {
    void save(Profile t);
    void create(Profile t);
    Profile findById(Integer id);
    List<Profile> findAll();
    void deleteById(Integer id);
    void update(Profile t);

}
