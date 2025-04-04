package com.example.wishlist.unitTest.controller;

import com.example.wishlist.controller.WishController;
import com.example.wishlist.model.Wish;
import com.example.wishlist.model.WishList;
import com.example.wishlist.service.IWishListService;
import com.example.wishlist.service.WishWishListService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import java.util.List;


@ExtendWith(MockitoExtension.class) //Til at bruge @Mock og @Injectmocks
public class WishControllerTest {

    @Mock
    private IWishListService iWishListService;

    @Mock
    private WishWishListService wishWishListService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private WishController controller;

    private WishList wishList;

    @BeforeEach
    void setUp() {

        String wishName = "PlayMobil";
        String description = "Lego";
        String link = "url";

        int wishListId = 1;
        String wishListName = "Christmas";
        String wishListDescription = "Pressy";
        int profileId = 100;

        wishList = new WishList(wishListId, wishListName,wishListDescription, profileId);


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void handleNotFound() {
    }

    @Test
    void getWishDetails() {
    }

    @Test
    void getCreateWish() {
    }

    @Test
    void postCreateWish() {
    }

    @Test
    void getAllWishesForWishlist() {
    }

    @Test
    void getEditWishForm() {
    }

    @Test
    void postEditWishForm() {
    }
}
