package com.example.wishlist.controller;

import com.example.wishlist.exception.DateTimeFormatException;
import com.example.wishlist.exception.ResourceNotFoundException;
import com.example.wishlist.model.Profile;
import com.example.wishlist.service.IProfileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("")
public class ProfileController {
    private IProfileService profileService;

    public ProfileController(IProfileService profileService) {
        this.profileService = profileService; // der stod this.profileRepository
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(Model model, ResourceNotFoundException e) {

        model.addAttribute("message", e.getMessage());
        return "error"; // thymeleaf skabelon i templates/error/
    }

    @ExceptionHandler(DateTimeFormatException.class)
    public String handleDateParseError(Model model, DateTimeFormatException e) {
        model.addAttribute("dateErrorMessage", e.getMessage());
        return "signup";
    }

    public static boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("profile") != null;
    }

    public static String loginTernary(HttpSession session, String htmlPage) {
        return isLoggedIn(session) ? htmlPage : "login";
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
            HttpSession session, Model model,
            RedirectAttributes redirectAttributes) {
        // Brug en metode i IProfileService til at tjekke login-informationer

        Profile profile = profileService.login(username, password);

        if (profile != null) {
            session.setAttribute("profile", profile);
            session.setMaxInactiveInterval(1000);
            redirectAttributes.addAttribute("profile", profile.getId());
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
            @RequestParam("repeatPassword") String repeatPassword,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("birthday") String birthday,
            Model model
    ) {
        if (!password.equals(repeatPassword)) { //Adgangskoder skal være ens
            model.addAttribute("passwordMismatch", true);
            return "signup";
        }

        if (profileService.profileAlreadyExists(username)) { //Tjekker om bruger findes
            model.addAttribute("profileAlreadyExists", true);
            return "signup";

        }
        LocalDate parsedDate; //gemmer parsed dato

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            parsedDate = LocalDate.parse(birthday, formatter);
        }
        catch (DateTimeFormatException e) {
            throw new DateTimeFormatException(); //Kaster brugerdefineret fejl
        }

        Profile profile = new Profile(name, parsedDate, email, username, password);
        profileService.create(profile);

        return "redirect:/login";
        }


    @GetMapping("/{profileId}/profile")
    public String getProfilePage(
            @PathVariable int profileId,
            HttpSession session, Model model
    ) {

        if (session.getAttribute("profile") == null) { return "redirect:/login"; } //Tjekker om bruger er logget ind

        model.addAttribute("profile", profileService.findById(profileId));

        return "profile-page";
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
