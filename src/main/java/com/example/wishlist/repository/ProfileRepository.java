package com.example.wishlist.repository;

import com.example.wishlist.model.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class ProfileRepository implements IRepository{

    private final JdbcTemplate jdbcTemplate;

    public ProfileRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean save(Profile profile) {

    }

    public boolean create(Profile profile) {

    }

    public Profile findById(int id) {

    }

    public List<Profile> findAll() {

    }

    public boolean deleteById(Integer id) {

    }

    public boolean update(T t) {

    }
}
