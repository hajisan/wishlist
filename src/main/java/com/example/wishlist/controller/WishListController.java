package com.example.wishlist.controller;

import com.example.wishlist.dto.ProfileWishListDTO;
import com.example.wishlist.dto.WishWishListDTO;
import com.example.wishlist.exception.ResourceNotFoundException; // Vores egen custom exception
import com.example.wishlist.model.Profile;
import com.example.wishlist.model.WishList;
import com.example.wishlist.service.IWishListService;
import com.example.wishlist.service.ProfileWishListService;
import com.example.wishlist.service.WishWishListService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

public class WishListController {

    private final WishWishListService wishWishListService;
    private final IWishListService iWishListService;
    private final ProfileWishListService profileWishListService;

    // Dependency Injection af IWishListService-interfacet, ProfileWishListService og WishWishListService i constructoren
    public WishListController(IWishListService iWishListService, ProfileWishListService profileWishListService, WishWishListService wishWishListService) {
        this.iWishListService = iWishListService;
        this.profileWishListService = profileWishListService;
        this.wishWishListService = wishWishListService;
    }

    // -------------------------- Klarer vores exceptions -------------------------
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(Model model, ResourceNotFoundException e) {

        model.addAttribute("message", e.getMessage());
        return "error"; // Thymeleaf-skabelon i templates/error/
    }


    // ----------------------------- Hent Create() --------------------------------
    @GetMapping("{profileId}/wishlists/createnewlist")
    public String getCreateWishList(
            @PathVariable int profileId,
            HttpSession session, Model model
    ) {

        if (session.getAttribute("profile") == null) {
            return "redirect:/login";
        } // Tjekker om bruger er logget ind

        model.addAttribute("profileId", profileId);

        return "wishlists";
    }

    // ----------------------------- Create() -------------------------------------

    @PostMapping("{profileId}/wishlists/createnewlist")
    public String postCreateWishList(
            @PathVariable int profileId,
            @RequestParam String name,
            @RequestParam String description,
            HttpSession session
    ) {
        if (session.getAttribute("profile") == null) {
            return "redirect:/login";
        }

        WishList wishList = new WishList(name, description, profileId);

        iWishListService.create(wishList);

        return "redirect:/" + profileId + "/wishlists";
    }

    // ----------------------------- ReadAll() -------------------------------------

    @GetMapping("{profileId}/wishlists")
    public String getWishLists(@PathVariable Integer profileId,
                               HttpSession session, Model model
    ) {

        if (session.getAttribute("profile") == null) {
            return "redirect:/login";
        } // Tjekker om bruger er logget ind

        Profile sessionProfile = (Profile) session.getAttribute("profile");

        if (sessionProfile.getId() != profileId) {
            return "redirect:/login";
        } // Tjekker om id matcher profilId på wishList

        ProfileWishListDTO profileWishListDTO = profileWishListService.findProfileWithWishLists(profileId);
        model.addAttribute("dto", profileWishListDTO);
        model.addAttribute("profileId", profileId);

        return "wishlists";

    }

    // ----------------------------- ReadOne() -------------------------------------

    @GetMapping("{profileId}/wishlists/{wishlistId}")
    public String getSpecificWishList(
            @PathVariable int profileId,
            @PathVariable int wishlistId,
            HttpSession session, Model model
    ) {
        if (session.getAttribute("profile") == null) {
            return "redirect:/login";
        }

        WishWishListDTO dto = wishWishListService.findWishWithWishList(wishlistId);

        model.addAttribute("profileId", profileId); // Sender id med til frontend
        model.addAttribute("dto", dto);

        return "wishlist";
    }

    // ----------------------------- Hent Update() -------------------------------------

    @GetMapping("{profileId}/wishlists/{wishlistId}/edit")
    public String getEditWishList(
            @PathVariable int profileId,
            @PathVariable int wishlistId,
            HttpSession session, Model model
    ) {
        if (session.getAttribute("profile") == null) {
            return "redirect:/login";
        } // Tjekker om bruger er logget ind

        model.addAttribute("profileId", profileId);
        model.addAttribute("wishlistId", wishlistId);

        return "wishlists";
    }

    // ----------------------------- Update() -------------------------------------

    @PostMapping("{profileId}/wishlists/{wishlistId}/edit")
    public String postEditWishList(
            @PathVariable int profileId,
            @PathVariable int wishlistId,
            @RequestParam String name,
            @RequestParam String description,
            HttpSession session
    ) {
        if (session.getAttribute("profile") == null) {
            return "redirect:/login";
        } // Tjekker om bruger er logget ind


        WishList newWishList = new WishList(wishlistId, name, description, profileId);

        iWishListService.update(newWishList);

        return "redirect:/" + profileId + "/wishlists";
    }

    // ----------------------------- Delete() -------------------------------------


    @PostMapping("{profileId}/wishlists/{wishlistId}/delete")
    public String deleteWishList(
            @PathVariable int profileId,
            @PathVariable int wishlistId,
            HttpSession session
    ) {
        if (session.getAttribute("profile") == null) {
            return "redirect:/login";
        }

        iWishListService.deleteById(wishlistId);

        return "redirect:/" + profileId + "/wishlists";
    }
}
