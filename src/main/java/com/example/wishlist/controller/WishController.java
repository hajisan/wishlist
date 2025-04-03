package com.example.wishlist.controller;

import com.example.wishlist.dto.WishWishListDTO;
import com.example.wishlist.model.Profile;
import com.example.wishlist.model.Wish;
import com.example.wishlist.service.IWishService;
import com.example.wishlist.service.WishWishListService;
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
    private final WishWishListService wishWishListService;

    public WishController(IWishService wishService, WishWishListService wishWishListService) {
        this.wishService = wishService;
        this.wishWishListService = wishWishListService;
    }

    // Vis ét enkelt ønske fra en ønskeliste, som tilhører den aktuelle bruger
    @GetMapping("{username}/wishlists/{wishlist}/wish/{wishId}")
    public String getWishDetails(
            @PathVariable String username,
            @PathVariable String wishlist,
            @PathVariable int wishId,
            HttpSession session, Model model
    ) {
        // Hent profil-id fra sessionen
        Profile profile = (Profile) session.getAttribute("profile");
        int profileId = profile.getId();

        // Find ønsket via id
        Wish wish = wishService.findById(wishId);

        // Find den ønskeliste ønsket burde høre til
        int expectedWishListId = wishWishListService.findWishListIdByNameAndProfile(wishlist, profileId);

        // Tjek at ønsket faktisk tilhører ønskelisten (sikkerhed)
        if (wish.getWishListId() != expectedWishListId) {
            return "redirect:/access-denied";
        }

        // Tilføj ønsket og info til modellen
        model.addAttribute("username", username);
        model.addAttribute("wishlist", wishlist);
        model.addAttribute("wish", wish);

        // Vis ønsket
        return ProfileController.loginTernary(session, "wish");
    }

    // Vis formular til at oprette et nyt ønske
    @GetMapping("{username}/wishlists/{wishlist}/createnewwish")
    public String getCreateWish(
            @PathVariable String username,
            @PathVariable String wishlist,
            HttpSession session, Model model
    ) {
        // Forudfyld formular med tomt Wish-objekt
        model.addAttribute("username", username);
        model.addAttribute("wishlist", wishlist);
        model.addAttribute("wish", new Wish());

        return ProfileController.loginTernary(session, "create-wish");
    }

    // Modtag og gem nyt ønske (fra formularen ovenfor)
    @PostMapping("{username}/wishlists/{wishlist}/createnewwish")
    public String postCreateWish(
            @PathVariable String username,
            @PathVariable String wishlistName,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String link,
            @RequestParam int quantity,
            @RequestParam double price,
            HttpSession session, Model model
    ) {
        // Find profil-id fra session
        Profile profile = (Profile) session.getAttribute("profile");
        int profileId = profile.getId();

        // Find ønskeliste-id baseret på navn og profil
        int wishListId = wishWishListService.findWishListIdByNameAndProfile(wishlistName, profileId);

        // Opret nyt Wish-objekt
        Wish wish = new Wish();
        wish.setName(name);
        wish.setDescription(description);
        wish.setLink(link);
        wish.setQuantity(quantity);
        wish.setPrice(price);
        wish.setReserved(false);
        wish.setWishListId(wishListId);

        // Gem ønsket
        wishService.create(wish);

        // Gå tilbage til ønskelisten
        return ProfileController.loginTernary(session, "redirect:/" + username + "/wishlists/" + wishlistName);
    }

    // Vis alle ønsker for én ønskeliste (bruger DTO)
    @GetMapping("{username}/wishlists/{wishlist}")
    public String getAllWishesForWishlist(
            @PathVariable String username,
            @PathVariable String wishlist,
            HttpSession session, Model model
    ) {
        // Hent profil fra session
        Profile profile = (Profile) session.getAttribute("profile");

        // Hent ønskeliste + tilhørende ønsker
        WishWishListDTO dto = wishWishListService.getWishListWithWishes(wishlist, profile.getId());

        model.addAttribute("username", username);
        model.addAttribute("dto", dto);

        // Vis ønskelisten med alle ønsker
        return ProfileController.loginTernary(session, "wishlist");
    }

    // Vis formular til at redigere et eksisterende ønske
    @GetMapping("{username}/wishlists/{wishlist}/wish/{wishId}/edit")
    public String getEditWishForm(
            @PathVariable String username,
            @PathVariable String wishlist,
            @PathVariable int wishId,
            HttpSession session, Model model
    ) {
        Profile profile = (Profile) session.getAttribute("profile");
        int profileId = profile.getId();

        // Hent ønskeliste + ønsker
        WishWishListDTO dto = wishWishListService.getWishListWithWishes(wishlist, profileId);

        // Find ønsket ud fra id
        Wish wish = dto.wishes().stream()
                .filter(w -> w.getId() == wishId)
                .findFirst()
                .orElse(null);

        // Hvis ønsket ikke blev fundet (eller ikke tilhører ønskelisten), nægt adgang
        if (wish == null) {
            return "redirect:/access-denied";
        }

        model.addAttribute("username", username);
        model.addAttribute("dto", dto);
        model.addAttribute("wish", wish);

        return ProfileController.loginTernary(session, "edit-wish");
    }

    // Modtag og gem ændringer til et eksisterende ønske
    @PostMapping("{username}/wishlists/{wishlist}/wish/{wishId}/edit")
    public String postEditWishForm(
            @PathVariable String username,
            @PathVariable String wishlist,
            @PathVariable int wishId,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String link,
            @RequestParam int quantity,
            @RequestParam double price,
            HttpSession session
    ) {
        Profile profile = (Profile) session.getAttribute("profile");
        int profileId = profile.getId();

        // Find ønsket via DTO og tjek om det tilhører brugerens ønskeliste
        WishWishListDTO dto = wishWishListService.getWishListWithWishes(wishlist, profileId);
        Wish wish = dto.wishes().stream()
                .filter(w -> w.getId() == wishId)
                .findFirst()
                .orElse(null);

        if (wish == null) {
            return "redirect:/access-denied";
        }

        // Opdater ønskets værdier
        wish.setName(name);
        wish.setDescription(description);
        wish.setLink(link);
        wish.setQuantity(quantity);
        wish.setPrice(price);

        // Gem ændringer
        wishService.update(wish);

        // Gå tilbage til ønskelisten
        return ProfileController.loginTernary(session, "redirect:/" + username + "/wishlists/" + wishlist);
    }

    // Jeg vil gerne kunne reservere ét wish, på wishlist, som hører til en profil
    // (mangler metode – fx en POST med /{wishId}/reserve)
}