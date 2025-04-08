package com.example.wishlist.unitTest.controller;

import com.example.wishlist.controller.WishController;
import com.example.wishlist.dto.WishWishListDTO;
import com.example.wishlist.exception.ResourceNotFoundException;
import com.example.wishlist.model.Profile;
import com.example.wishlist.model.Wish;
import com.example.wishlist.model.WishList;
import com.example.wishlist.service.IWishListService;
import com.example.wishlist.service.IWishService;
import com.example.wishlist.service.WishWishListService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class) //Til at bruge @Mock og @Injectmocks
public class WishControllerTest {

    @Mock
    private IWishListService iWishListService;

    @Mock
    private WishWishListService wishWishListService;

    @Mock
    private IWishService iWishService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private WishController controller;

    private WishList testWishList;
    private Wish testWish;
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


        //Sætter wishList op
        int wishListId = 10;
        String wishListName = "Christmas";
        String wishListDescription = "Pressy";
        testWishList = new WishList(wishListId, wishListName,wishListDescription, profileId);


        //Sætter wish op
        String wishName = "PlayMobil";
        String wishDescription = "Lego";
        String wishLink = "url";
        int wishId = 1;
        int wishQuantity = 3;
        double wishPrice = 9.95;
        testWish = new Wish(wishName, wishDescription, wishLink, wishId, wishQuantity, wishPrice, wishListId);
        testWish.setId(wishId);
    }

    // Bruger ikke tearDown() = der ændres ikke noget globalt i systemet, kun lokalt i de enkelte tests


    // ------------------------------ ResourceNotFoundException.class -----------------------

    @Test
    void handleNotFound() {

        //Arrange
        String message = "Wish not found";
        ResourceNotFoundException exception = new ResourceNotFoundException(message);

        //Act
        String view = controller.handleNotFound(model, exception);

        //Assert
        verify(model).addAttribute("message", message);
        assertEquals("error", view);
    }

    // -------------------------------- Henter createWish() ---------------------------
    @Test
    void getCreateWish() {

        //Arrange - @BeforeEach

        //Act
        String viewName = controller.getCreateWish(testProfile.getId(), testWishList.getName(), session, model);

        //Assert
        verify(model).addAttribute("profileId", testProfile.getId());
        verify(model).addAttribute("wishlistName", testWishList.getName());
        verify(model).addAttribute(eq("wish"), any(Wish.class));
        assertEquals("create-wish", viewName);
    }

    // -------------------------------- Post createWish() ------------------------------
    @Test
    void postCreateWish() {

        //Arrange - @BeforeEach

        //Act
        String viewName = controller.postCreateWish(
                testProfile.getId(), testWishList.getId(),
                testWish.getName(), testWish.getDescription(),
                testWish.getLink(), testWish.getQuantity(),
                testWish.getPrice(), session);

        //Assert
        verify(iWishService).create(any(Wish.class));
        assertEquals("redirect:/" + testProfile.getId() + "/wishlists/" + testWishList.getId() + "/wishes", viewName);

    }

    // -------------------------------- Read allWishes() ------------------------------

    @Test
    void getAllWishesForWishlist() {
        //Arrange
        WishWishListDTO dto = mock(WishWishListDTO.class);


        //Act
        when(wishWishListService.findWishWithWishList(testWishList.getId())).thenReturn(dto);
        String viewName = controller.getAllWishesForWishlist(testProfile.getId(), testWishList.getId(), session, model);

        //Assert
        verify(model).addAttribute("profileId", testProfile.getId());
        verify(model).addAttribute("dto", dto);
        assertEquals("wishlist", viewName);
    }

    // -------------------------------- Read oneWish() ------------------------------

    @Test
    void getWishDetails() {
        //Arrange - @BeforeEach

        //Act
        when(iWishService.findById(testWish.getId())).thenReturn(testWish);
        when(iWishListService.findById(testWishList.getId())).thenReturn(testWishList);

        String viewName = controller.getOneWish(testProfile.getId(), testWishList.getId(), testWish.getId(), session, model);

        //Assert
        verify(model).addAttribute("wish", testWish);
        verify(model).addAttribute("profileId", testProfile.getId());
        verify(model).addAttribute("wishlistId", testWishList.getId());
        assertEquals("wish", viewName);
    }


    // -------------------------------- Get updateWish() ------------------------------

    @Test
    void getEditWishForm() {
        //Arrange - @BeforeEach


        //Act
        when(iWishService.findById(testWish.getId())).thenReturn(testWish);
        when(iWishListService.findById(testWishList.getId())).thenReturn(testWishList);
        String viewName = controller.showEditWishForm(testProfile.getId(), testWishList.getId(), testWish.getId(), session, model);

        //Assert
        verify(model).addAttribute("wish", testWish);
        verify(model).addAttribute("profileId", testProfile.getId());
        verify(model).addAttribute("wishlistId", testWishList.getId());
        assertEquals("edit-wish", viewName);

    }

    // -------------------------------- Post updateWish() ------------------------------

    @Test
    void postEditWishForm() {
        //Arrange - @BeforeEach

        //Act
        String viewName = controller.updateWish(
                testProfile.getId(), testWishList.getId(),
                testWish.getId(), testWish.getName(),
                testWish.getDescription(), testWish.getLink(),
                testWish.getQuantity(), testWish.getPrice(),
                session);

        ArgumentCaptor<Wish> captor = ArgumentCaptor.forClass(Wish.class);
        verify(iWishService).update(captor.capture());

        Wish updated = captor.getValue();

        //Assert
        assertEquals(testWish.getId(), updated.getId());
        assertEquals(testWish.getName(), updated.getName());
        assertEquals(testWish.getDescription(), updated.getDescription());
        assertEquals(testWish.getLink(), updated.getLink());
        assertEquals(testWish.getQuantity(), updated.getQuantity());
        assertEquals(testWishList.getId(), updated.getWishListId());

        assertEquals("redirect:/" + testProfile.getId() + "/wishlists/" + testWishList.getId() + "/wishes", viewName);

    }
    @Test
    void deleteWish() {

        //Arrange - @BeforeEach

        //Act
        String viewName = controller.deleteWish(testProfile.getId(), testWishList.getId(), testWish.getId(), session);
        verify(iWishService).deleteById(testWish.getId());

        //Assert
        assertEquals("redirect:/" + testProfile.getId() + "/wishlists/" + testWishList.getId() + "/wishes", viewName);

    }
}
