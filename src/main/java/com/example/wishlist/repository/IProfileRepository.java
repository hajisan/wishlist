package com.example.wishlist.repository;

import com.example.wishlist.model.Profile;

import java.util.List;

public interface IProfileRepository extends IRepository<Profile, Integer> {

    Profile create(Profile t);

    List<Profile> findAll();

    Profile findById(Integer id);

    Profile update(Profile t);

    void deleteById(Integer id);

    Profile findProfileByUserName(String username);
}
