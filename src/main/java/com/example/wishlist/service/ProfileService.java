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
    public void save(Profile profile) {
        iProfileRepository.save(profile);
    }

    @Override
    public void create(Profile profile) {

        iProfileRepository.create(profile);
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
        Profile profile = iProfileRepository.findById(id); //Finder id

        if (profile == null) {
            throw new ResourceNotFoundException("Profile with ID: " + id + " not found.");
        }
        iProfileRepository.deleteById(id); //Sletter id

    }

    @Override
    public void update(Profile profile) {

        if (profile == null) {
            throw new ResourceNotFoundException("Profile not found");
        }
        iProfileRepository.update(profile);
    }

    @Override
    public Profile findProfileByUserName(String username) {
        if (iProfileRepository.findProfileByUserName(username) == null) {
            throw new ResourceNotFoundException("Profile not found");
        }
        return iProfileRepository.findProfileByUserName(username);
    }

    @Override
    public void editProfile(Profile uneditedProfile, Profile editedProfile) {
        iProfileRepository.editProfile(uneditedProfile, editedProfile);
    }

    @Override
    public void createProfile(Profile profile) {
        iProfileRepository.create(profile);
    }

    @Override
    public boolean profileAlreadyExists(String username) {
        return iProfileRepository.findProfileByUserName(username) != null;
    }

    @Override
    public boolean login(String username, String password) {
        Profile profile = iProfileRepository.findProfileByUserName(username);
        return profile.getPassword().equals(password);
    }
}
