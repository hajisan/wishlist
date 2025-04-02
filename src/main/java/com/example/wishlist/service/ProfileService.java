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
            throw new ResourceNotFoundException("Profilen findes ikke:");
        }
        iProfileRepository.update(profile);
    }



}
