package com.example.wishlist.repository;


import com.example.wishlist.model.WishList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository

public class WishListRepository implements IRepository<WishList, Integer> {

    private final JdbcTemplate jdbcTemplate;

    public WishListRepository(JdbcTemplate jdbcTemplate) { //Injector data source
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(WishList wishList) {

    }

    //Opretter
    @Override
    public void create(WishList wishList) {
        String sql = "INSERT INTO wish_list(name, description, profile_id) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder(); //Interface der autogenererer primary key

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, wishList.getName());
            ps.setString(2, wishList.getDescription());
            ps.setInt(3, wishList.);
        })



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
