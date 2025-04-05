package com.example.wishlist.controller;

import com.example.wishlist.dto.WishWishListDTO;
import com.example.wishlist.exception.ResourceNotFoundException;
import com.example.wishlist.model.Profile;
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
    private final ProfileWishListService profileWishListService;
    private final IWishListService iWishListService;

    public WishController(IWishService wishService, WishWishListService wishWishListService, ProfileWishListService profileWishListService, IWishListService iWishListService) {
        this.wishService = wishService;
        this.wishWishListService = wishWishListService;
        this.profileWishListService = profileWishListService;
        this.iWishListService = iWishListService;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(Model model, ResourceNotFoundException e) {

        model.addAttribute("message", e.getMessage());
        return "error"; // thymeleaf skabelon i templates/error/
    }

//    // Vis ét enkelt ønske fra en ønskeliste, som tilhører den aktuelle bruger
//    @GetMapping("{username}/wishlists/{wishlist}/wish/{wishId}")
//    public String getWishDetails(
//            @PathVariable String username,
//            @PathVariable String wishlist,
//            @PathVariable int wishId,
//            HttpSession session, Model model
//    ) {
//        // Hent profil-id fra sessionen
//        Profile profile = (Profile) session.getAttribute("profile");
//        int profileId = profile.getId();
//
//        // Find ønsket via id
//        Wish wish = wishService.findById(wishId);
//
//        // Find den ønskeliste ønsket burde høre til
//        int expectedWishListId = wishWishListService.findWishListIdByNameAndProfile(wishlist, profileId);
//
//        // Tjek at ønsket faktisk tilhører ønskelisten (sikkerhed)
//        if (wish.getWishListId() != expectedWishListId) {
//            return "redirect:/error";
//        }
//
//        // Tilføj ønsket og info til modellen
//        model.addAttribute("username", username);
//        model.addAttribute("wishlist", wishlist);
//        model.addAttribute("wish", wish);
//
//        // Vis ønsket
//        return ProfileController.loginTernary(session, "wish");
//    }

    // Vis formular til at oprette et nyt ønske
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

    // Modtag og gem nyt ønske (fra formularen ovenfor)
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


        // Opret nyt Wish-objekt
        Wish wish = new Wish(name, description, link, quantity, price, wishlistId);


        // Gem ønsket
        wishService.create(wish);


        // Gå tilbage til ønskelisten
        return "redirect:/" + profileId + "/wishlists/" + wishlistId + "/wishes";

    }

    // Vis alle ønsker for én ønskeliste (bruger DTO)
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

    @GetMapping("/{profileId}/wishlists/{wishlistId}/wish/{wishId}")
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
        model.addAttribute("profileId", profileId);
        model.addAttribute("wishlistId", wishList.getId());
        return "edit-wish";
    }

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

        return "redirect:/" + profileId + "/wishlists/" + wishlistId + "/wishes";
    }

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









//    // Vis formular til at redigere et eksisterende ønske
//    @GetMapping("{username}/wishlists/{wishlist}/wish/{wishId}/edit")
//    public String getEditWishForm(
//            @PathVariable String username,
//            @PathVariable String wishlist,
//            @PathVariable int wishId,
//            HttpSession session, Model model
//    ) {
//        Profile profile = (Profile) session.getAttribute("profile");
//        int profileId = profile.getId();
//
//        // Hent ønskeliste + ønsker
//        WishWishListDTO dto = wishWishListService.getWishListWithWishes(wishlist, profileId);
//
//        // Find ønsket ud fra id
//        Wish wish = dto.wishes().stream()
//                .filter(w -> w.getId() == wishId)
//                .findFirst()
//                .orElse(null);
//
//        model.addAttribute("username", username);
//        model.addAttribute("dto", dto);
//        model.addAttribute("wish", wish);
//
//        return ProfileController.loginTernary(session, "edit-wish");
//    }
//
//    // Modtag og gem ændringer til et eksisterende ønske
//    @PostMapping("{username}/wishlists/{wishlist}/wish/{wishId}/edit")
//    public String postEditWishForm(
//            @PathVariable String username,
//            @PathVariable String wishlist,
//            @PathVariable int wishId,
//            @RequestParam String name,
//            @RequestParam String description,
//            @RequestParam String link,
//            @RequestParam int quantity,
//            @RequestParam double price,
//            HttpSession session
//    ) {
//        Profile profile = (Profile) session.getAttribute("profile");
//        int profileId = profile.getId();
//
//        // Find ønsket via DTO og tjek om det tilhører brugerens ønskeliste
//        WishWishListDTO dto = wishWishListService.getWishListWithWishes(wishlist, profileId);
//        Wish wish = dto.wishes().stream()
//                .filter(w -> w.getId() == wishId)
//                .findFirst()
//                .orElse(null);
//
//        // Opdater ønskets værdier
//        wish.setName(name);
//        wish.setDescription(description);
//        wish.setLink(link);
//        wish.setQuantity(quantity);
//        wish.setPrice(price);
//
//        // Gem ændringer
//        wishService.update(wish);
//
//        // Gå tilbage til ønskelisten
//        return ProfileController.loginTernary(session, "redirect:/" + username + "/wishlists/" + wishlist);
//    }

    // Jeg vil gerne kunne reservere ét wish, på wishlist, som hører til en profil
    // (mangler metode – fx en POST med /{wishId}/reserve)
}