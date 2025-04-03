package com.example.wishlist.controller;

import com.example.wishlist.model.Profile;
import com.example.wishlist.service.IProfileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class ProfileController {
    private IProfileService profileService;

    public ProfileController(IProfileService profileService) {
        this.profileService = profileService; // der stod this.profileRepository
    }

    public static boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("profile") != null;
    }

    public static String loginTernary(HttpSession session, String htmlPage) {
        return isLoggedIn(session) ? htmlPage : "login";
    }
}

    //---------------------------------------------------------------------------------------------------
    //----------------------------------     Application Mappings     -----------------------------------
    //---------------------------------------------------------------------------------------------------

    @GetMapping("/")
    public String getFrontPage() {
        return "index";
    }

    @GetMapping("/sustainability")
    public String getSustainabilityPage() {
        return "sustainability";
    }

    @GetMapping("/charity")
    public String getCharityPage() {
        return "charity";
    }

    @GetMapping("/information")
    public String getInfoPage() {
        return "information";
    }

    @GetMapping("/information/contact")
    public String getContactPage() {
        return "redirect:/information";
    }

    @GetMapping("/information/faq")
    public String getFAQPage() {
        return "redirect:/information";
    }

    @GetMapping("/information/cookies")
    public String getCookiesPage() {
        return "redirect:/information";
    }

    @GetMapping("/information/privacy")
    public String getPrivacyPolicyPage() {
        return "redirect:/information";
    }

    //---------------------------------------------------------------------------------------------------
    //------------------------------------     Profile Mappings     -------------------------------------
    //---------------------------------------------------------------------------------------------------

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session, Model model
    ) {
        // Brug en metode i IProfileService til at tjekke login-informationer
        if (profileService.login(username, password)) {
            // Hent profiloplysninger og opret nyt Profile-objekt ud fra dem
            Profile profile = new Profile(username, password);
            // Indsæt evt. data her
            session.setAttribute("profile", profile);
            session.setMaxInactiveInterval(120);
            return "redirect:/{profile}/wishlists";
        }
        model.addAttribute("wrongCredentials", true);
        return "login";
    }

    @GetMapping("/signup")
    public String getSignUp() {
        return "signup";
    }

    @PostMapping("/signup")
    public String postSignUp(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("birthday") String birthday,
            Model model
    ) {
        Profile profile = new Profile(name, Profile.getStringAsLocalDate(birthday), email, username, password);
        // Tjek om profilen allerede findes i databasen ved hjælp af username, da det er unikt
        if (!profileService.profileAlreadyExists(username)) {
            // Tilføj profilen til databasen over eksisterende profiler
            profileService.createProfile(profile);
            // Redirect til login-siden
            return "redirect:/login";
        }
        // Tilføj attribute til model, så vi kan vise det på vores HTML-side
        model.addAttribute("profileAlreadyExists", true);
        return "signup";
    }

    @GetMapping("/{username}/profile")
    public String getProfilePage(
            @PathVariable String username,
            HttpSession session, Model model
    ) {
        model.addAttribute("profile", profileService.findProfileByUserName(username));
        return loginTernary(session, "profile-page");
    }

    @GetMapping("/{username}/profile/edit")
    public String getProfileEditPage(
            @PathVariable String username,
            HttpSession session, Model model
    ) {
        model.addAttribute("profile", profileService.findProfileByUserName(username));
        return loginTernary(session, "edit-profile-page");
    }

    @PostMapping("/{username}/profile/edit")
    public String postProfileEditPage(
            @PathVariable @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("birthday") String birthday,
            HttpSession session, Model model
    ) {
        model.addAttribute("profile", profileService.findProfileByUserName(username));
        // Opret en Profile med både de uændrede (fra HttpSession) og de ændrede data (fra parametre)
        Profile uneditedProfile = (Profile) session.getAttribute("profile");
        Profile editedProfile = new Profile(name, Profile.getStringAsLocalDate(birthday), email, username, password);
        // Edit profilen ved at finde den vha. det gamle username i det tilfælde at det er blevet opdateret
        profileService.editProfile(uneditedProfile, editedProfile);
        return loginTernary(session, "redirect:/{username}/profile");
    }
}
