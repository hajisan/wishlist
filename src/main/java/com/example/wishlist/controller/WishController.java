package com.example.wishlist.controller;

import com.example.wishlist.dto.WishWishListDTO;
import com.example.wishlist.exception.ResourceNotFoundException;
import com.example.wishlist.model.Wish;
import com.example.wishlist.model.WishList;
import com.example.wishlist.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WishController {
    private final IWishService wishService;
    private final WishWishListService wishWishListService;
    private final IWishListService iWishListService;

    public WishController(IWishService wishService, WishWishListService wishWishListService, IWishListService iWishListService) {
        this.wishService = wishService;
        this.wishWishListService = wishWishListService;
        this.iWishListService = iWishListService;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(Model model, ResourceNotFoundException e) {

        model.addAttribute("message", e.getMessage());
        return "error"; // thymeleaf skabelon i templates/error/
    }


    // ----------------------------- Hent Create() -------------------------------------

    @GetMapping("{profileId}/wishlists/{wishlistName}/createnewwish")
    public String getCreateWish(
            @PathVariable int profileId,
            @PathVariable String wishlistName,
            HttpSession session, Model model
    ) {

        if (session.getAttribute("profile") == null) { return "redirect:/login"; } //Tjekker om bruger er logget ind

        model.addAttribute("profileId", profileId);
        model.addAttribute("wishlistName", wishlistName);
        model.addAttribute("wish", new Wish());

        return "create-wish";
    }

    // ----------------------------- Create() -------------------------------------

    @PostMapping("{profileId}/wishlists/{wishlistId}/createnewwish")
    public String postCreateWish(
            @PathVariable int profileId,
            @PathVariable int wishlistId,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String link,
            @RequestParam int quantity,
            @RequestParam double price,
            HttpSession session
    ) {

        if (session.getAttribute("profile") == null) { return "redirect:/login"; } //Tjekker om bruger er logget ind

        Wish wish = new Wish(name, description, link, quantity, price, wishlistId);

        wishService.create(wish);

        return "redirect:/" + profileId + "/wishlists/" + wishlistId + "/wishes";
    }

    // ---------------------- Hent ReadAll() ----------------------------------

    @GetMapping("{profileId}/wishlists/{wishlistId}/wishes")
    public String getAllWishesForWishlist(
            @PathVariable int profileId,
            @PathVariable int wishlistId,
            HttpSession session, Model model
    ) {

        if (session.getAttribute("profile") == null) { return "redirect:/login"; } //Tjekker om bruger er logget ind

        // Hent ønskeliste + tilhørende ønsker (med ID)
        WishWishListDTO dto = wishWishListService.findWishWithWishList(wishlistId);

        model.addAttribute("profileId", profileId);
        model.addAttribute("dto", dto);

        // Vis ønskelisten med alle ønsker
        return "wishlist";
    }

    // -------------------------- Hent ReadOne() ----------------------------------

    @GetMapping("/{profileId}/wishlist/{wishlistId}/wish/{wishId}")
    public String getOneWish(
            @PathVariable int profileId,
            @PathVariable int wishlistId,
            @PathVariable int wishId,
            HttpSession session,
            Model model
    ) {

        if (session.getAttribute("profile") == null) { return "redirect:/login"; }

        Wish wish = wishService.findById(wishId);
        WishList wishList = iWishListService.findById(wishlistId);

        model.addAttribute("wish", wish);
        model.addAttribute("profileId", profileId);
        model.addAttribute("wishlistId", wishlistId);
        model.addAttribute("wishlistName", wishList.getName());

        return "wish";

    }

    // --------------------------- Hent Update() ------------------------------

    @GetMapping("/{profileId}/wishlists/{wishlistId}/wish/{wishId}/edit")
    public String showEditWishForm(
            @PathVariable int profileId,
            @PathVariable int wishlistId,
            @PathVariable int wishId,
            HttpSession session,
            Model model
    ) {
        if (session.getAttribute("profile") == null) { return "redirect:/login"; } //Tjekker om bruger er logget ind

        Wish wish = wishService.findById(wishId);

        WishList wishList = iWishListService.findById(wishlistId);

        model.addAttribute("wish", wish);
        model.addAttribute("profileId", profileId); //Lægger id-data i modellen, så html-siden kan bruge dem
        model.addAttribute("wishlistId", wishList.getId());
        return "edit-wish";
    }

    // --------------------------- Update() ------------------------------

    @PostMapping("/{profileId}/wishlists/{wishlistId}/wish/{wishId}/update")
    public String updateWish(
            @PathVariable int profileId,
            @PathVariable int wishlistId,
            @PathVariable int wishId,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String link,
            @RequestParam int quantity,
            @RequestParam double price,
            HttpSession session
    ) {
        if (session.getAttribute("profile") == null) { return "redirect:/login"; } //Tjekker om bruger er logget ind

        Wish wish = new Wish(name, description, link, quantity, price, wishlistId);
        wish.setId(wishId);
        wishService.update(wish); // din service skal have denne metode

        return "redirect:/" + profileId + "/wishlist/" + wishlistId + "/wishes";
    }

    // --------------------------- Delete() ------------------------------

    @PostMapping("/{profileId}/wishlists/{wishlistId}/wish/{wishId}/delete")
    public String deleteWish(
            @PathVariable int profileId,
            @PathVariable int wishlistId,
            @PathVariable int wishId,
            HttpSession session
    ) {
        if (session.getAttribute("profile") == null) { return "redirect:/login"; } //Tjekker om bruger er logget ind

        wishService.deleteById(wishId); // Slet ønsket

        return "redirect:/" + profileId + "/wishlists/" + wishlistId + "/wishes"; // Gå tilbage til ønskelisten
    }

}