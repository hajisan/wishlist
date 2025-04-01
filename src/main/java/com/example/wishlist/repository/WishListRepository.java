package com.example.wishlist.repository;


import com.example.wishlist.exception.ResourceNotFoundException;
import com.example.wishlist.model.WishList;
import com.example.wishlist.rowMapper.WishListRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository

public class WishListRepository implements IRepository<WishList, Integer> {

    private final JdbcTemplate jdbcTemplate;

    public WishListRepository(JdbcTemplate jdbcTemplate) { //Injector data source
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(WishList wishList) {

        WishList excistingWishes = findById(wishList.getId());

        if (excistingWishes != null) {
            update(wishList);
        } create(wishList);

    }

    //Opretter
    @Override
    public void create(WishList wishList) {
        System.out.println("Creating wishlist with name: " + wishList.getName()); //Logs

        String sql = "INSERT INTO wish_list(name, description, profile_id) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder(); //Interface der autogenererer primary key

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, wishList.getName());
            ps.setString(2, wishList.getDescription());
            ps.setInt(3, wishList.getProfileId()); //Ønskeliste knytter sig til profil
            return ps;
        }, keyHolder);
        wishList.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());

    }

    @Override //EXCEPTION SKAL EKSPEDERES TIL @SERVICE
    public WishList findById(Integer id) {
        String sql = "SELECT id, name, description FROM wish_list WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new WishListRowMapper(), id); //Returnerer wishList-objekt
        }
        catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("WishList with ID " + id + " not found.");
        }

    }

    @Override
    public List<WishList> findAll() {
        String sql = "SELECT id, name, description FROM wish_list";
        return jdbcTemplate.query(sql, new WishListRowMapper());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM wish_list WHERE id = ?";

            jdbcTemplate.update(sql, id);


    }

    @Override
    public void update(WishList wishList) {

        String sql = "UPDATE wish_list SET name = ?, description = ? WHERE id = ?";

        jdbcTemplate.update(
                sql,
                wishList.getName(),
                wishList.getDescription(),
                wishList.getId() //Parameter -> id til WHERE
        );

    }
}
