package com.example.wishlist.service;

import com.example.wishlist.model.Profile;

import java.util.List;

public interface IProfileService extends IService<Profile, Integer> {

    void save(Profile profile);

    void create(Profile profile);

    Profile findById(Integer id);

    List<Profile> findAll();

    void deleteById(Integer id);

    void update(Profile profile);
}
