package com.example.wishlist.service;

import com.example.wishlist.model.Profile;

import java.util.List;

public interface IProfileService extends IService<Profile, Integer> {

    //------------------------------------ Standardmetoder fra IService ------------------------------------

    Profile create(Profile profile);

    Profile findById(Integer id);

    List<Profile> findAll();

    void deleteById(Integer id);

    Profile update(Profile profile);

    //------------------------------- Specifikke metoder til IProfileService -------------------------------

    boolean profileAlreadyExists(String username);

    Profile login(String username, String password);
}
