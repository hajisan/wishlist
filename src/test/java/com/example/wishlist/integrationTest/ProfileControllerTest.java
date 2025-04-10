package com.example.wishlist.integrationTest;

import com.example.wishlist.controller.ProfileController;
import com.example.wishlist.model.Profile;
import com.example.wishlist.service.IProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfileController.class)
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // --- Mocker de klasser, controlleren er afhængig af = Spring loader kun mock'ede objekter ---

    @MockBean
    private IProfileService iProfileService;

    private Profile testProfile;


    @BeforeEach
    void setUp() {

        int id = 10;
        String name = "Nima";
        LocalDate birthday = LocalDate.of(1992, 8, 8);
        String email = "nimseladen@example.com";
        String username = "Nimseladen";
        String password = "secret123";

        testProfile = new Profile(id, name, birthday, email, username, password);

    }

    // -------------------------------- Henter createProfile() ---------------------------

    @Test
    void getSignUp() throws Exception {

        //Act
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk()) //Assert
                .andExpect(view().name("index"));
    }

    // -------------------------------- Post createProfile() ---------------------------
    @Test
    void testPostSignUp() throws Exception {

        //Arrange
        when(iProfileService.create(testProfile)).thenReturn(testProfile);

        //Act
        mockMvc.perform(post("/signup")
                        .param("username", testProfile.getUsername())
                        .param("password", testProfile.getPassword())
                        .param("repeatPassword", testProfile.getPassword())
                        .param("name", testProfile.getName())
                        .param("email", testProfile.getEmail())
                        .param("birthday", testProfile.getBirthday().toString()))
                .andExpect(status().is3xxRedirection()) //Assert
                .andExpect(redirectedUrl("/login"));

        verify(iProfileService, times(1)).create(any(Profile.class));

    }

    // -------------------------------- Henter readProfilePage() ---------------------------
//    @Test
//    void testGetProfilePage() throws Exception {
//
//        //Arrange
//        when(iProfileService.findById(testProfile.getId())).thenReturn(testProfile);
//
//        //Act
//        mockMvc.perform(get("/" + testProfile.getId() + "/profile")
//                        .sessionAttr("profile", testProfile)) //Simumlerer at bruger er logget ind
//                .andExpect(status().isOk()) //Assert
//                .andExpect(view().name("profile-page"))
//                .andExpect(model().attribute("profileId", testProfile.getId()))
//                .andExpect(model().attribute("profile", testProfile));
//    }

    // -------------------------------- Henter updateProfile() ---------------------------

//    @Test
//    void testGetProfileEditPage() throws Exception {
//
//        //Arrange
//        lenient().when(iProfileService.findById(testProfile.getId())).thenReturn(testProfile);
//
//        //Act
//        mockMvc.perform(get("/" + testProfile.getId() + "/profile/edit")
//                .sessionAttr("profile", testProfile))
//                .andExpect(status().isOk()) //Assert
//                .andExpect(view().name("edit-profile-page"))
//                .andExpect(model().attribute("profile", testProfile)
//                );
//    }

    // Not loggedIn skal redirect
    @Test
    void testRedirectWhenNotLoggedIn() throws Exception {

        //Arrange

        //Act
        mockMvc.perform(get("/" + testProfile.getId() + "/profile/edit"))
                .andExpect(status().is3xxRedirection()) //Assert
                .andExpect(redirectedUrl("/login"));

    }

    // -------------------------------- Post updateProfile() ---------------------------

    @Test
    void testPostProfileEditPage() throws Exception {
        //Arrange
        lenient().when(iProfileService.update(testProfile)).thenReturn(testProfile);

        //Act
        mockMvc.perform(post("/" + testProfile.getId() + "/profile/edit")
                        .sessionAttr("profile", testProfile)
                        .param("username", testProfile.getUsername())
                        .param("password", testProfile.getPassword())
                        .param("repeatPassword", testProfile.getPassword())
                        .param("name", testProfile.getName())
                        .param("email", testProfile.getEmail())
                        .param("birthday", testProfile.getBirthday().toString()))
                .andExpect(status().is3xxRedirection()) //Assert
                .andExpect(redirectedUrl("/" + testProfile.getId() + "/wishlists"));
        verify(iProfileService, times(1)).update(any(Profile.class));

    }

    // -------------------------------- deleteProfile() ---------------------------

    @Test
    void testDeleteProfile() throws Exception {

        //Arrange
        doNothing().when(iProfileService).deleteById(testProfile.getId());

        //Act
        mockMvc.perform(post("/" + testProfile.getId() + "/profile/delete")
                        .sessionAttr("profile", testProfile))
                .andExpect(status().isOk()) //Assert
                .andExpect(view().name("index"));

        verify(iProfileService, times(1)).deleteById(testProfile.getId());
    }

    // -------------------------------- Post ProfileLogin() ---------------------------

    @Test
    void testPostLogin() throws Exception {

        //Arrange - når login kaldes med disse argumenter, returnér testProfilen
        lenient().when(iProfileService.login(testProfile.getUsername(), testProfile.getPassword())).thenReturn(testProfile);

        //Act - kører selve testen
        mockMvc.perform(post("/login")
                        .param("username", testProfile.getUsername())
                        .param("password", testProfile.getPassword()))
                .andExpect(status().is3xxRedirection()) //Assert - er svaret/responsen korrekt?
                .andExpect(redirectedUrl("/" + testProfile.getId() + "/wishlists"));


    }
    // ------------------------ wrong credentials for Post ProfileLogin()  -------------------------

    @Test
    void testFailedLogin() throws Exception {

        //Arrange
        lenient().when(iProfileService.login("wronguser", "wrongpassword")).thenReturn(null);

        //Act
        mockMvc.perform(post("/login")
                        .param("username", "wronguser")
                        .param("password", "wrongpassword"))
                .andExpect(status().isOk()) //Assert
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("wrongCredentials"));
    }


}
