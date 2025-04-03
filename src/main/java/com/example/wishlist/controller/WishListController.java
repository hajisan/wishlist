package com.example.wishlist.controller;


import com.example.wishlist.service.IWishListService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class WishListController {
    private IWishListService wishListService;

    public WishListController(IWishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping("{username}/wishlists")
    public String getWishLists(@PathVariable String username, HttpSession session, Model model) {
        // @TODO Brug Model til at håndtere @PathVariables

        // @TODO Indhold

        return ProfileController.isLoggedIn(session) ? "wishlists" : "login";

    }

    @GetMapping("{username}/wishlists/createnewlist")
    public String getCreateWishList(@PathVariable String username, HttpSession session, Model model) {
        // @TODO Brug Model til at håndtere @PathVariables

        // @TODO Indhold

        return ProfileController.loginTernary(session, "create-wishlist");
    }

    @PostMapping("{username}/wishlists/createnewlist")
    public String postCreateWishList(
            @PathVariable String username,
            @RequestParam String name,
            @RequestParam String description,
            HttpSession session, Model model
    ) {
        // @TODO Brug Model til at håndtere @PathVariables

        // @TODO Indhold

        return ProfileController.loginTernary(session, "create-wishlist");
    }

    @GetMapping("{username}/wishlists/{wishlist}")
    public String getSpecificWishList(
            @PathVariable String username,
            @PathVariable String wishlistName,
            HttpSession session, Model model
    ) {
        // @TODO Brug Model til at håndtere @PathVariables

        // @TODO Indhold

        return ProfileController.loginTernary(session, "wishlist");
    }
}
