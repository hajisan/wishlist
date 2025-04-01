package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.rowMapper.WishRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishRepository implements IRepository<Wish, Integer> {

    private final JdbcTemplate jdbcTemplate;

    // Konstruktør, som initialiserer JdbcTemplate
    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean save(Wish wish) {
        // Forsøger at finde objektet via dets id
        Wish excistingWish = findById(wish.getId());

        // Hvis objektet findes, opdater det, ellers opret et nyt
        if (excistingWish != null) {
            return update(wish); // Opdater eksisterende objekt
        } else {
            return create(wish); // Opret nyt objekt
        }
    }

    @Override
    public boolean create(Wish wish) {
        // SQL-kommandoen til at indsætte et nyt ønske i databasen
        String sql = "INSERT INTO Wish (name, description, quantity, price) VALUES (?, ?, ?, ?)";

        // Brug jdbcTemplate til at udføre insert kommandoen
        int rowsAffected = jdbcTemplate.update(sql,
                wish.getName(),
                wish.getDescription(),
                wish.getQuantity(),
                wish.getPrice()
        );

        // Returner true, hvis en række blev påvirket (indsat), ellers false
        return rowsAffected > 0;
    }

    @Override
    public Wish findById(Integer id) {
        // SQL-kommandoen til at hente et ønske baseret på id
        String sql = "SELECT id, name, description, quantity, price FROM Wish WHERE id = ?";

        // Brug jdbcTemplate til at finde objektet og returnere det som et Wish-objekt
        Wish wish = jdbcTemplate.queryForObject(sql, new WishRowMapper(), id);
        return wish;
    }

    // Metode til at finde alle ønsker
    @Override
    public List findAll() {
        // SQL-kommandoen til at hente alle ønsker fra databasen
        String sql = "SELECT id, name, description, quantity, price FROM Wish";

        // Brug jdbcTemplate til at udføre query og returnere en liste af Wish-objekter
        List<Wish> wishes = jdbcTemplate.query(sql, new WishRowMapper());
        return wishes;
    }

    @Override
    public boolean deleteById(Integer id) {
        // SQL-kommandoen til at slette et ønske baseret på id
        String sql = "DELETE FROM Wish WHERE id = ?";

        // Brug jdbcTemplate til at udføre delete-kommandoen
        int rowsAffected = jdbcTemplate.update(sql, id);

        // Returner true, hvis en række blev slettet, ellers false
        return rowsAffected > 0;
    }

    @Override
    public boolean update(Wish wish) {
        // SQL-kommandoen til at opdatere et ønske
        String sql = "UPDATE Wish SET name = ?, description = ?, quantity = ?, price = ? WHERE id = ?";

        // Brug jdbcTemplate til at udføre update-kommandoen
        int rowsAffected = jdbcTemplate.update(
                sql,
                wish.getName(),
                wish.getDescription(),
                wish.getQuantity(),
                wish.getPrice(),
                wish.getId()
        );

        // Returner true, hvis en række blev opdateret, ellers false
        return rowsAffected > 0;
    }
}