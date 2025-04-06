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

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
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

    // --------------------------- Hent Create() -------------------------------------

    @GetMapping("/signup")
    public String getSignUp() {
        return "signup";
    }

    // ----------------------------- Create() -------------------------------------

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

    // ----------------------------- Read() -------------------------------------

    @GetMapping("/{profileId}/profile")
    public String getProfilePage(
            @PathVariable int profileId,
            HttpSession session, Model model
    ) {

        if (session.getAttribute("profile") == null) { return "redirect:/login"; } //Tjekker om bruger er logget ind

        model.addAttribute("profileId", profileId);
        model.addAttribute("profile", profileService.findById(profileId));

        return "profile-page";
    }

    // ----------------------------- Hent Update() -----------------------------------

    @GetMapping("/{profileId}/profile/edit")
    public String getProfileEditPage(
            @PathVariable int profileId,
            HttpSession session, Model model
    ) {
        if (session.getAttribute("profile") == null) { return "redirect:/login"; } //Tjekker om bruger er logget ind

        model.addAttribute("profile", profileService.findById(profileId)); //Sender hele objektet med til redigering

        return "edit-profile-page";
    }

    // ----------------------------- Update() -------------------------------------

    @PostMapping("/{profileId}/profile/edit")
    public String postProfileEditPage(
            @PathVariable int profileId,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("repeatPassword") String repeatPassword,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("birthday") String birthday,
            HttpSession session, Model model
    ) {
        if (session.getAttribute("profile") == null) { return "redirect:/login"; } //Tjekker om bruger er logget ind


        if (!password.equals(repeatPassword)) { //Adgangskoder skal være ens
            model.addAttribute("passwordMismatch", true);
            return "signup";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");//Formatterer brugerinput til MySQL
        LocalDate parsedDate = LocalDate.parse(birthday, formatter); //Gemmer parsed dato

        Profile profile = new Profile(profileId, name, parsedDate, email, username, password);

        profileService.update(profile);
        return "redirect:/{profileId}/profile";
    }

    // ----------------------------- Delete() -------------------------------------

    @PostMapping("/{profileId}/profile/delete")
    public String deleteProfile(
            @PathVariable int profileId,
            HttpSession session) {

        if (session.getAttribute("profile") == null) { return "redirect:/login"; }

        profileService.deleteById(profileId);

        session.invalidate();

        return "index";
    }


}
