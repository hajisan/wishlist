package com.example.wishlist.controller;


import com.example.wishlist.dto.ProfileWishListDTO;
import com.example.wishlist.exception.ResourceNotFoundException;
import com.example.wishlist.model.Profile;
import com.example.wishlist.model.WishList;
import com.example.wishlist.service.IWishListService;
import com.example.wishlist.service.ProfileWishListService;
import com.example.wishlist.service.WishListService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller

public class WishListController {
    private final WishListService wishListService;
    private IWishListService iWishListService;
    private ProfileWishListService profileWishListService;

    public WishListController(IWishListService iWishListService, ProfileWishListService profileWishListService, WishListService wishListService) {
        this.iWishListService = iWishListService;
        this.profileWishListService = profileWishListService;
        this.wishListService = wishListService;
    }

    // ------------ Klarer vores exceptions -------------------------
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(Model model, ResourceNotFoundException e) {

        model.addAttribute("message", e.getMessage());
        return "error"; // thymeleaf skabelon i templates/error/
    }

    // ------------- Henter brugerens ønskelister --------------------


    @GetMapping("{profileId}/wishlists")
    public String getWishLists(@PathVariable Integer profileId, HttpSession session, Model model) {

        if (session.getAttribute("profile") == null) { return "redirect:/login"; } //Tjekker om bruger er logget ind

        Profile sessionProfile = (Profile) session.getAttribute("profile");

        if (sessionProfile.getId() != profileId) { return "redirect:/login"; } //Tjekker om id matcher profilId på wishList

        ProfileWishListDTO profileWishListDTO = profileWishListService.findProfileWithWishLists(profileId);
        model.addAttribute("dto", profileWishListDTO);
        model.addAttribute("profileId", profileId);

        return "wishlists";

    }

    // ----------- Henter formular til at oprette en ønskeliste --------------

    @GetMapping("{profileId}/wishlists/createnewlist")
    public String getCreateWishList(@PathVariable int profileId, HttpSession session, Model model) {

        if (session.getAttribute("profile") == null) { return "redirect:/login"; } //Tjekker om bruger er logget ind

        model.addAttribute("profileId", profileId);

        return "create-wishlist";
    }

    // ----------- Opretter ønskelisten med formular -------------------------

    @PostMapping("{profileId}/wishlists/createnewlist")
    public String postCreateWishList(
            @PathVariable int profileId,
            @RequestParam String name,
            @RequestParam String description,
            HttpSession session, Model model
    ) {
        if (session.getAttribute("profile") == null) { return "redirect:/login"; } //Tjekker om bruger er logget ind


        model.addAttribute("profileId", profileId);

        WishList wishList = new WishList(name, description, profileId);

        iWishListService.create(wishList);

        return "redirect:/" + profileId + "/wishlists";
    }

    // -------- Henter specifik ønskeliste ud fra ønskelistenavn og profilID ---------

    @GetMapping("{profileId}/wishlists/{wishlistId}")
    public String getSpecificWishList(
            @PathVariable int profileId,
            @PathVariable int wishlistId,
            HttpSession session, Model model
    ) {
        if (session.getAttribute("profile") == null) { return "redirect:/login"; } //Tjekker om bruger er logget ind

        model.addAttribute("profileId", profileId);
        model.addAttribute("wishlistId", wishlistId);

        iWishListService.findById(wishlistId);

        return "wishlist";
    }

    // ----------- Henter specifik ønskeliste så den kan redigeres --------------------

    @GetMapping("{profileId}/wishlists/{wishlistId}/edit")
    public String getEditWishList(@PathVariable int profileId, @PathVariable int wishlistId, HttpSession session, Model model) {

        if (session.getAttribute("profile") == null) { return "redirect:/login"; } //Tjekker om bruger er logget ind

        model.addAttribute("profileId", profileId);
        model.addAttribute("wishlistId", wishlistId);

        return "edit-wishlist";
    }

    // ----------- Formular til at kunne ændre ønskelisten --------------

    @PostMapping("{profileId}/wishlists/{oldWishlistId}/edit")
    public String postEditWishList(@PathVariable int profileId, @PathVariable int oldWishlistId,
                                   @RequestParam String name, @RequestParam String description, HttpSession session, Model model) {

        if (session.getAttribute("profile") == null) { return "redirect:/login"; } //Tjekker om bruger er logget ind

        model.addAttribute("profileId", profileId);
        model.addAttribute("wishlistId", oldWishlistId);


        WishList newWishList = new WishList(oldWishlistId, name, description, profileId);

        iWishListService.update(newWishList);

        return "redirect:/wishlists";
    }

}
