package com.example.wishlist.unitTest.controller;

import com.example.wishlist.controller.WishController;
import com.example.wishlist.model.Profile;
import com.example.wishlist.service.IProfileService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) //Til at bruge @Mock og @Injectmocks
class ProfileControllerTest {

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @Mock
    private IProfileService iProfileService;

    @InjectMocks
    private WishController controller;

    private Profile testProfile;

    @BeforeEach
    void setUp() {

        //Sætter profile op
        String profileName = "Joe";
        String email = "joe@example.com";
        String username = "joe-laden";
        String password = "secret123";
        LocalDate birthday = LocalDate.of(1999, 5, 7);
        int profileId = 100;
        testProfile = new Profile(profileId, profileName, birthday, email, username, password);

        //Mock session, så mock'ed controller tror at bruger er logget ind
        when(session.getAttribute("profile")).thenReturn(testProfile);
    }

    // Bruger ikke tearDown() = der ændres ikke noget globalt i systemet, kun lokalt i de enkelte tests


    // ------------------------------ ResourceNotFoundException.class -----------------------


    // -------------------------------- Henter createProfile() ---------------------------

    @Test
    void getSignUp() {
    }

    // -------------------------------- Post createProfile() ---------------------------


    @Test
    void postSignUp() {
    }

    // -------------------------------- Hent readProfile() ---------------------------

    @Test
    void getProfilePage() {
    }

    // -------------------------------- Hent updateProfile() ---------------------------

    @Test
    void getProfileEditPage() {
    }

    // -------------------------------- Post updateProfile() ---------------------------

    @Test
    void postProfileEditPage() {

    }

    // -------------------------------- deleteProfile() -------------------------------

    @Test
    void deleteById() {

    }

    // -------------------------------- Alle de andre --------------------------------

    @Test
    void postLogin() {
    }
}