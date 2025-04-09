package com.example.wishlist.rowMapper;

import org.springframework.jdbc.core.RowMapper;
import com.example.wishlist.model.Profile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ProfileRowMapper implements RowMapper<Profile> {

    @Override
    public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
        Profile profile = new Profile();
        profile.setId(rs.getInt("id"));
        profile.setName(rs.getString("name"));
        profile.setBirthday(LocalDate.of(                                  // Opretter nyt LocalDate-objekt
                Integer.parseInt(rs.getString("birthday").split("-")[0]), // Splitter result settet og tager årstallet
                Integer.parseInt(rs.getString("birthday").split("-")[1]), // Splitter result settet og tager måneden
                Integer.parseInt(rs.getString("birthday").split("-")[2])) // Splitter result settet og tager dagen
        );
        profile.setEmail(rs.getString("email"));
        profile.setUserName(rs.getString("username")); // Midlertidig indtil vi får sat vores environment variables op
        profile.setPassword(rs.getString("password")); // Midlertidig indtil vi får sat vores environment variables op
        return profile;
    }
}