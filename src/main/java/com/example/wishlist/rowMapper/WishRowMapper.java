package com.example.wishlist.rowMapper;

import com.example.wishlist.model.Wish;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

// Denne klasse oversætter en række fra databasen til et Wish-objekt
public class WishRowMapper implements RowMapper<Wish> {

    // mapRow kaldes automatisk for hver række i resultatet fra databasen
    @Override
    public Wish mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Wish(
                rs.getString("name"),        // Henter navnet fra kolonnen "name"
                rs.getString("description"), // Henter beskrivelsen fra kolonnen "description"

                // Da kolonnen "link" ikke findes i databasen, sættes feltet til null midlertidigt
                // På et senere tidspunkt kan man hente det, hvis det bliver tilføjet i databasen
                null,

                rs.getInt("id"),             // Henter id-værdien fra kolonnen "id"
                rs.getInt("quantity"),       // Henter mængden fra kolonnen "quantity"
                rs.getDouble("price")        // Henter prisen fra kolonnen "price"
        );
    }
}