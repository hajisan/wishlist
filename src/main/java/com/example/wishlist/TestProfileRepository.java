package com.example.wishlist;

import com.example.wishlist.model.*;
import com.example.wishlist.repository.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.time.LocalDate;
import java.util.*;

public class TestProfileRepository {

    public static void main(String[] args) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(
                "jdbc:mysql://localhost:3306/wish_list",
                "root",
                "(74GrydeUfolog)"
        );
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ProfileRepository repo = new ProfileRepository(new JdbcTemplate(dataSource));

        testFindAll(repo);
        System.out.println("---------------------------------------------");
        testCreate(repo);

    }

    private static void testCreate(ProfileRepository repo) {
        testFindAll(repo);
        repo.create(new Profile("Lance Armstrong", LocalDate.now(), "Strong@Arms.com", "StrongBoi", "LeTourDeFrance69"));
        testFindAll(repo);
    }

    private static void testFindAll(ProfileRepository repo) {
        for (Profile p : repo.findAll()) {
            System.out.println(p.getId());
        }
    }
}
