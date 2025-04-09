package com.example.wishlist.service;

import com.example.wishlist.exception.ResourceNotFoundException; // Vores egen custom exception
import com.example.wishlist.model.Profile;
import com.example.wishlist.repository.IProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService implements IProfileService {

    private final IProfileRepository iProfileRepository;

    // Injection af IProfileRepository-interface i constructor
    public ProfileService(IProfileRepository iProfileRepository) {
        this.iProfileRepository = iProfileRepository;
    }

    //------------------------------------ Create() ------------------------------------

    @Override
    public Profile create(Profile profile) {

        return iProfileRepository.create(profile);
    }

    //------------------------------------ Read() ------------------------------------

    @Override
    public Profile findById(Integer id) {
        Profile profile = iProfileRepository.findById(id);

        if (profile == null) {
            throw new ResourceNotFoundException("Profile with ID: " + id + " not found.");
        }
        return profile;
    }

    @Override
    public List<Profile> findAll() {
        return iProfileRepository.findAll();
    }

    //------------------------------------ Update() ------------------------------------

    @Override
    public Profile update(Profile profile) {

        if (profile == null) {
            throw new ResourceNotFoundException("Profile not found");
        }
        return iProfileRepository.update(profile);
    }

    //------------------------------------ Delete() ------------------------------------


    @Override
    public void deleteById(Integer id) {
        Profile profile = iProfileRepository.findById(id);

        if (profile == null) {
            throw new ResourceNotFoundException("Profile with ID: " + id + " not found.");
        }
        iProfileRepository.deleteById(id);
    }

    //------------------------------------ Profil-tjek() ------------------------------------

    @Override
    public boolean profileAlreadyExists(String username) {
        return iProfileRepository.findProfileByUserName(username) != null;
    }

    @Override
    public Profile login(String username, String password) {
        Profile profile = iProfileRepository.findProfileByUserName(username);

        if (profile.getPassword().equals(password)) {
            return profile;
        }
        return null;
    }
}