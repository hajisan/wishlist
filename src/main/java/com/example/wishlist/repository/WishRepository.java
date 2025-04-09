package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;

import com.example.wishlist.rowMapper.WishRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class WishRepository implements IWishRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //------------------------------------ Create() ------------------------------------

    @Override
    public Wish create(Wish wish) {

        String sql = "INSERT INTO Wish (name, description, link, quantity, price, wish_list_id) VALUES (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        // Bruger PreparedStatement sammen med vores GeneratedKeyHolder til at kunne autogenerere et nyt id
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, wish.getName());
            ps.setString(2, wish.getDescription());
            ps.setString(3, wish.getLink());
            ps.setInt(4, wish.getQuantity());
            ps.setDouble(5, wish.getPrice());
            ps.setInt(6, wish.getWishListId());
            return ps;
        }, keyHolder);
        wish.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());

        return wish;
    }

    //------------------------------------- Read() -------------------------------------

    @Override
    public Wish findById(Integer id) {

        try {
            String sql = "SELECT id, name, description, link, quantity, price, wish_list_id FROM Wish WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new WishRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Wish> findAll() {

        String sql = "SELECT id, name, description, link, quantity, price, wish_list_id FROM Wish";

        return jdbcTemplate.query(sql, new WishRowMapper());
    }

    @Override
    public List<Wish> findWishesByWishListId(Integer wishListId) {

        String sql = "SELECT id, name, description, link, quantity, price, wish_list_id FROM wish WHERE wish_list_id = ?";

        return jdbcTemplate.query(sql, new WishRowMapper(), wishListId);
    }

    //------------------------------------ Update() ------------------------------------

    @Override
    public Wish update(Wish wish) {

        String sql = "UPDATE Wish SET name = ?, description = ?, link = ?, quantity = ?, price = ? WHERE id = ?";

        jdbcTemplate.update(
                sql,
                wish.getName(),
                wish.getDescription(),
                wish.getLink(),
                wish.getQuantity(),
                wish.getPrice(),
                wish.getId()
        );
        return wish;
    }

    //------------------------------------ Delete() ------------------------------------

    @Override
    public void deleteById(Integer id) {

        String sql = "DELETE FROM Wish WHERE id = ?";


        jdbcTemplate.update(sql, id);
    }
}