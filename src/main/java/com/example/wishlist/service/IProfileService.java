package com.example.wishlist.service;

import com.example.wishlist.model.Profile;

import java.util.List;

public interface IProfileService extends IService<Profile, Integer> {

    void save(Profile profile);

    void create(Profile profile);

    Profile findById(Integer id);

    Profile findByUserName(String userName);

    List<Profile> findAll();

    void deleteById(Integer id);

    void update(Profile profile);

    Profile findProfileByUserName(String username);

    void editProfile(Profile uneditedProfile, Profile editedProfile);

    void createProfile(Profile profile);

    boolean profileAlreadyExists(String username);

    boolean login(String username, String password);
}
