package com.example.wishlist.repository;


import com.example.wishlist.model.Profile;

import java.util.List;

public interface IProfileRepository extends IRepository<Profile, Integer> {
    void save(Profile t);

    Profile create(Profile t);

    Profile findById(Integer id);

    List<Profile> findAll();

    void deleteById(Integer id);

    void update(Profile t);

    Profile findProfileByUserName(String username);

    void editProfile(Profile uneditedProfile, Profile editedProfile);
}
