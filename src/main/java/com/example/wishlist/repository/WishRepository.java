package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.WishList;
import com.example.wishlist.rowMapper.WishRowMapper;
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

    // Konstruktør, som initialiserer JdbcTemplate
    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Wish wish) {
        // Forsøger at finde objektet via dets id
        Wish excistingWish = findById(wish.getId());

        // Hvis objektet findes, opdater det, ellers opret et nyt
        if (excistingWish != null) {
            update(wish); // Opdater eksisterende objekt
        } else {
            create(wish); // Opret nyt objekt
        }
    }

    @Override
    public void create(Wish wish) {
        // SQL-kommandoen til at indsætte et nyt ønske i databasen
        String sql = "INSERT INTO Wish (name, description, link, quantity, price, wish_list_id) VALUES (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        // Brug jdbcTemplate til at udføre insert kommandoen
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

    }

    @Override
    public Wish findById(Integer id) {
        // SQL-kommandoen til at hente et ønske baseret på id
        String sql = "SELECT id, name, description, link, quantity, price FROM Wish WHERE id = ?";

        // Brug jdbcTemplate til at finde objektet og returnere det som et Wish-objekt
        Wish wish = jdbcTemplate.queryForObject(sql, new WishRowMapper(), id);
        return wish;
    }

    // Metode til at finde alle ønsker
    @Override
    public List<Wish> findAll() {
        // SQL-kommandoen til at hente alle ønsker fra databasen
        String sql = "SELECT id, name, description, link, quantity, price FROM Wish";

        // Brug jdbcTemplate til at udføre query og returnere en liste af Wish-objekter
        List<Wish> wishes = jdbcTemplate.query(sql, new WishRowMapper());
        return wishes;
    }

    @Override
    public void deleteById(Integer id) {
        // SQL-kommandoen til at slette et ønske baseret på id
        String sql = "DELETE FROM Wish WHERE id = ?";

        // Brug jdbcTemplate til at udføre delete-kommandoen
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void update(Wish wish) {
        // SQL-kommandoen til at opdatere et ønske
        String sql = "UPDATE Wish SET name = ?, description = ?, link = ?, quantity = ?, price = ? WHERE id = ?";

        // Brug jdbcTemplate til at udføre update-kommandoen
        jdbcTemplate.update(
                sql,
                wish.getName(),
                wish.getDescription(),
                wish.getLink(),
                wish.getQuantity(),
                wish.getPrice(),
                wish.getId()
        );

    }

    @Override
    public List<Wish> findByWishListId(Integer wishListId) {

        String sql = "SELECT id, name, description, link, quantity, price, wish_list_id WHERE wish_List_id = ?";

        return jdbcTemplate.query(sql, new WishRowMapper(), wishListId);
    }
}