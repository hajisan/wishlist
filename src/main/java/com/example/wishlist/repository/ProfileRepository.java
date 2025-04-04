package com.example.wishlist.repository;

import com.example.wishlist.exception.ResourceNotFoundException;
import com.example.wishlist.model.Profile;
import com.example.wishlist.rowMapper.ProfileRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class ProfileRepository implements IProfileRepository {

    private JdbcTemplate jdbcTemplate;

    public ProfileRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    // Hvad er forskellen på save() og update()?
    public void save(Profile profile) {

    }

    @Override
    public void create(Profile profile) {
        String sql = "INSERT INTO profile (name, birthday, email, username, password) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, profile.getName());
            ps.setString(2, profile.getBirthday().format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.setString(3, profile.getEmail());
            ps.setString(4, profile.getUserName());
            ps.setString(5, profile.getPassword());
            return ps;
        }, keyHolder);

        // Tjekker først om id'et er null eller ej. Hvis det er, så sætter vi id-variablen til -1
        int id = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;
        // Sætter profilens id til KeyHolderens værdi, hvis den ikke var null
        if (id != -1) profile.setId(id);

    }

    @Override
    public Profile findById(Integer id) {
        String sql = "SELECT * FROM profile WHERE id = ?";
        List<Profile> oneProfileAsList = jdbcTemplate.query(sql, new ProfileRowMapper(), id);
        if (oneProfileAsList.isEmpty()) throw new ResourceNotFoundException("Profile not found");
        else return oneProfileAsList.get(0);
    }

    @Override
    public List<Profile> findAll() {
        String sql = "SELECT * FROM profile";
        return jdbcTemplate.query(sql, new ProfileRowMapper());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM profile WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void update(Profile profile) {
        // Gemmer SQL-kommandoen i en String
        String sql = "UPDATE profile SET name = ?, birthday = ?, email = ?, username = ?, password = ?";

        //
        jdbcTemplate.update(
                sql,
                profile.getName(),
                profile.getBirthday(),
                profile.getEmail(),
                profile.getUserName(),
                profile.getPassword()
        );
    }

    @Override
    public Profile findProfileByUserName(String username) {
        String sql = "SELECT * FROM profile WHERE username = ?";
        List<Profile> oneProfileAsList = jdbcTemplate.query(sql, new ProfileRowMapper(), username);
        if (oneProfileAsList.isEmpty()) throw new ResourceNotFoundException("Profile not found");
        else return oneProfileAsList.get(0);
    }

    @Override
    public void editProfile(Profile uneditedProfile, Profile editedProfile) {
        String sql = "UPDATE  profile SET name = ?, birthday = ?, email = ?, username = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(
                sql,
                editedProfile.getName(),
                editedProfile.getBirthday(),
                editedProfile.getEmail(),
                editedProfile.getUserName(),
                editedProfile.getPassword(),
                uneditedProfile.getId()
        );
    }
}
