package com.example.wishlist.controller;


import com.example.wishlist.dto.ProfileWishListDTO;
import com.example.wishlist.exception.ResourceNotFoundException;
import com.example.wishlist.model.Profile;
import com.example.wishlist.model.WishList;
import com.example.wishlist.service.IWishListService;
import com.example.wishlist.service.ProfileWishListService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller

public class WishListController {
    private IWishListService iWishListService;
    private ProfileWishListService profileWishListService;

    public WishListController(IWishListService iWishListService, ProfileWishListService profileWishListService) {
        this.iWishListService = iWishListService;
        this.profileWishListService = profileWishListService;
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

        return "wishlists";

    }

    // ----------- Henter formular til at oprette en ønskeliste --------------

    @GetMapping("{username}/wishlists/createnewlist")
    public String getCreateWishList(@PathVariable String username, HttpSession session, Model model) {

        model.addAttribute("username", username);

        return ProfileController.loginTernary(session, "create-wishlist");
    }

    // ----------- Opretter ønskelisten med formular -------------------------

    @PostMapping("{username}/wishlists/createnewlist")
    public String postCreateWishList(
            @PathVariable String username,
            @RequestParam String name,
            @RequestParam String description,
            HttpSession session, Model model
    ) {
        Profile profile = (Profile) session.getAttribute("profile");
        int profileId = profile.getId();

        model.addAttribute("username", username);

        WishList wishList = new WishList();
        wishList.setName(name);
        wishList.setDescription(description);
        wishList.setProfileId(profileId);

        iWishListService.create(wishList);

        return ProfileController.loginTernary(session, "create-wishlist");
    }

    // -------- Henter specifik ønskeliste ud fra ønskelistenavn og profilID ---------

    @GetMapping("{username}/wishlists/{wishlist}")
    public String getSpecificWishList(
            @PathVariable String username,
            @PathVariable String wishlist,
            HttpSession session, Model model
    ) {

        Profile profile = (Profile) session.getAttribute("profile");

        int profileId = profile.getId();

        model.addAttribute("username", username);
        model.addAttribute("wishlist", profileWishListService.findSpecificWishListByProfileId(wishlist, profileId));

        return ProfileController.loginTernary(session, "wishlist");
    }

    // ----------- Henter specifik ønskeliste så den kan redigeres --------------------

    @GetMapping("{username}/wishlists/{wishlist}/edit")
    public String getEditWishList(@PathVariable String username, @PathVariable String wishlist, HttpSession session, Model model) {

        Profile profile = (Profile) session.getAttribute("profile");
        int profileId = profile.getId();

        WishList excistingWishList = profileWishListService.findSpecificWishListByProfileId(wishlist, profileId);

        model.addAttribute("username", username);
        model.addAttribute("wishlist", excistingWishList);

        return ProfileController.loginTernary(session, "edit-wishlist"); //EKSTRA HTML-SIDE
    }

    // ----------- Formular til at kunne ændre ønskelisten --------------

    @PostMapping("{username}/wishlists/{oldname}/edit")
    public String postEditWishList(@PathVariable String username, @PathVariable String oldname,
                                   @RequestParam String name, @RequestParam String description, HttpSession session, Model model) {

        Profile profile = (Profile) session.getAttribute("profile");

        int profileId = profile.getId();

        model.addAttribute("username", username);
        model.addAttribute("oldname", oldname);

        WishList excistingWishList = profileWishListService.findSpecificWishListByProfileId(oldname, profileId);

        excistingWishList.setName(name);
        excistingWishList.setDescription(description);

        iWishListService.update(excistingWishList);

        return ProfileController.loginTernary(session, "wishlist");
    }

}
