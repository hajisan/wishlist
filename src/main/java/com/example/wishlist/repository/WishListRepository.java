package com.example.wishlist.repository;


import com.example.wishlist.model.WishList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class WishListRepository implements IRepository<WishList, Integer> {

    private final JdbcTemplate jdbcTemplate;

    public WishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(WishList wishList) {

    }

    @Override
    public void create(WishList wishList) {
        String sql = "INSERT INTO wish_list";


    }

    @Override
    public WishList findById(Integer id) {
        return null;
    }

    @Override
    public List<WishList> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public void update(WishList wishList) {

    }
}
