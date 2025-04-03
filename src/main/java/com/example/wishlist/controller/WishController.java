package com.example.wishlist.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WishController {
    private final IWishService wishService;

    public WishController(IWishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping("{username}/wishlists/{wishlist}/createnewwish")
    public String getCreateWish(
            @PathVariable String username,
            @PathVariable String wishlist,
            HttpSession session, Model model
    ) {
        // @TODO Brug Model til at håndtere @PathVariables

        // @TODO Indhold

        return ProfileController.loginTernary(session, "create-wish");
    }

    @PostMapping("{username}/wishlists/{wishlist}/createnewwish")
    public String postCreateWish(
            @PathVariable String username,
            @PathVariable String wishlistName,
            @RequestParam String name,
            @RequestParam String description,
            HttpSession session, Model model
    ) {
        // @TODO Brug Model til at håndtere @PathVariables

        // @TODO Indhold

        return ProfileController.loginTernary(session, "wishlists/{wishlist}");
    }
}
