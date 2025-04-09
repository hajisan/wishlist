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

public class WishListRepository implements IWishListRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishListRepository(JdbcTemplate jdbcTemplate) { //Injector data source
        this.jdbcTemplate = jdbcTemplate;
    }

    //------------------------------------ Create() ------------------------------------

    @Override
    public WishList create(WishList wishList) {
        System.out.println("Creating wishlist with name: " + wishList.getName()); //Logs

        String sql = "INSERT INTO wish_list(name, description, profile_id) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder(); //Interface der autogenererer primary key

        // Bruger PreparedStatement sammen med vores GeneratedKeyHolder til at kunne autogenerere et nyt id
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, wishList.getName());
            ps.setString(2, wishList.getDescription());
            ps.setInt(3, wishList.getProfileId()); //Ønskeliste knytter sig til profil
            return ps;
        }, keyHolder);
        wishList.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());

        return wishList;
    }

    //------------------------------------- Read() -------------------------------------

    @Override
    public WishList findById(Integer id) {
        String sql = "SELECT id, name, description, profile_id FROM wish_list WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, new WishListRowMapper(), id); //Returnerer wishList-objekt

    }

    @Override
    public List<WishList> findAll() {
        String sql = "SELECT id, name, description, profile_id FROM wish_list";
        return jdbcTemplate.query(sql, new WishListRowMapper());
    }

    @Override //Finder ønskelister til specifik profil
    public List<WishList> findByProfileId(Integer profileId) {

        String sql = "SELECT id, name, description, profile_id FROM wish_list WHERE profile_id = ?";
        return jdbcTemplate.query(sql, new WishListRowMapper(), profileId);
    }

    @Override // Finder ønskeliste til specifikt ønskeliste navn og profil
    public WishList findByNameAndProfile(String name, Integer profileId) {
        String sql = "SELECT id, name, description, profile_id FROM wish_list WHERE name = ? AND profile_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new WishListRowMapper(), name, profileId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Wishlist with name " + name + " for profile ID " + profileId + " not found.");
        }
    }

    //------------------------------------ Update() ------------------------------------

    @Override
    public WishList update(WishList wishList) {

        String sql = "UPDATE wish_list SET name = ?, description = ? WHERE id = ?";

        jdbcTemplate.update(
                sql,
                wishList.getName(),
                wishList.getDescription(),
                wishList.getId() //Parameter -> id til WHERE
        );
        return wishList;
    }

    //------------------------------------ Delete() ------------------------------------

    @Override
    public void deleteById(Integer id) {
        try {
            String checkSql = "SELECT COUNT(*) FROM wish_list WHERE id = ?";
            Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, id);

            if (count == null || count == 0) {
                throw new ResourceNotFoundException("Ønskeliste med ID " + id + " blev ikke fundet og kan ikke slettes.");
            }
            // Hvis den findes, slet den – alle wishes bliver automatisk slettet pga. ON DELETE CASCADE
            String deleteSql = "DELETE FROM wish_list WHERE id = ?";
            jdbcTemplate.update(deleteSql, id);
        } catch (Exception e) {
            throw new RuntimeException("Noget gik galt under sletning af ønskelisten med ID " + id, e);
        }
    }
}