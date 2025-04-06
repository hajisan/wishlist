package com.example.wishlist.service;

import com.example.wishlist.exception.ResourceNotFoundException;
import com.example.wishlist.model.Profile;
import com.example.wishlist.repository.IProfileRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService implements IProfileService {

    private final IProfileRepository iProfileRepository;

    public ProfileService(IProfileRepository iProfileRepository) {
        this.iProfileRepository = iProfileRepository;
    }

    @Override
    public Profile create(Profile profile) {

        return iProfileRepository.create(profile);
    }

    @Override
    public Profile findById(Integer id) {
        Profile profile = iProfileRepository.findById(id);

        if (profile == null) {
            throw new ResourceNotFoundException("Profile with ID: " + id + " not found.");
        }
        return profile;
    }

    @Override
    public Profile findByUserName(String userName) {
        Profile profile = iProfileRepository.findProfileByUserName(userName);

        if (profile == null) {
            throw new ResourceNotFoundException("Profile with ID: " + userName + " not found.");
        }
        return profile;
    }

    @Override
    public List<Profile> findAll() {
        return iProfileRepository.findAll();
    }


    @Override
    public void deleteById(Integer id) {
        Profile profile = iProfileRepository.findById(id);

        if (profile == null) {
            throw new ResourceNotFoundException("Profile with ID: " + id + " not found.");
        }
        iProfileRepository.deleteById(id);

    }

    @Override
    public Profile update(Profile profile) {

        if (profile == null) {
            throw new ResourceNotFoundException("Profile not found");
        }
        return iProfileRepository.update(profile);
    }

    @Override
    public Profile findProfileByUserName(String username) {

        return iProfileRepository.findProfileByUserName(username);
    }


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
