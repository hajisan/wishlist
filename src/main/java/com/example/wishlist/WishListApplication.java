package com.example.wishlist;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WishListApplication {

    public static void main(String[] args) {
        SpringApplication.run(WishListApplication.class, args);



    }
    @PostConstruct
    public void debugEnvVars() {
        System.out.println("✔ DEV_DATABASE_URL: " + System.getenv("DEV_DATABASE_URL"));
        System.out.println("✔ DEV_USERNAME: " + System.getenv("DEV_USERNAME"));
        System.out.println("✔ DEV_PASSWORD: " + System.getenv("DEV_PASSWORD"));
    }
}
