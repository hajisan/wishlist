package com.example.wishlist.unitTest.controller;

import com.example.wishlist.dto.ProfileWishListDTO;
import com.example.wishlist.dto.WishWishListDTO;
import com.example.wishlist.model.Profile;
import com.example.wishlist.controller.WishListController;
import com.example.wishlist.model.WishList;
import com.example.wishlist.service.IWishListService;
import com.example.wishlist.service.ProfileWishListService;
import com.example.wishlist.service.WishWishListService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.Model;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;

@ExtendWith(MockitoExtension.class) // For at kunne bruge @Mock og @InjectMocks
public class WishListControllerTest {

    @Mock
    private IWishListService iWishListService;

    @Mock
    private ProfileWishListService profileWishListService;

    @Mock
    private WishWishListService wishWishListService;

    @Mock
    private WishWishListDTO wishWishListDTO;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private WishListController controller;

    private Profile testProfile;

    @BeforeEach
    void setUp() {
        testProfile = new Profile(1, "Test", LocalDate.of(2000, 1, 1),
                "test@example.com", "testuser", "secret123");

        lenient().when(session.getAttribute("profile")).thenReturn(testProfile);
    }


    // -------------------------------- Henter createWishList() ------------------------------
    @Test
    void testGetCreateWishList() {
        //Arrange @BeforeEach

        //Act - kalder metoden, jeg vil teste: getCreateWishList()
        String viewName = controller.getCreateWishList(testProfile.getId(), session, model);


        //Assert - tjekker om modellen får profilId med + viewName
        verify(model).addAttribute("profileId", testProfile.getId());
        assertEquals("wishlists", viewName);
    }

    // -------------------------------- Post createWishList() ------------------------------
    @Test
    void testPostCreateWishList() { //skal create() wishList og redirect
        //Arrange + @BeforeEach
        String name = "Christmas";
        String description = "Wishes";

        //Act - kalder metoden, jeg vil teste: postCreateWishList()
        String viewName = controller.postCreateWishList(testProfile.getId(), name, description, session);

        //ArgumentCaptor skal fange information der bliver posted for WishList
        ArgumentCaptor<WishList> captor = ArgumentCaptor.forClass(WishList.class);

        //Assert - tjekker at værdierne for de to lister er de samme (de skal være ens)
        verify(iWishListService).create(captor.capture());//kalder create() og fanger ønskelistens værdier
        //De fangede data laves til det opdaterede Wish, som bruges til at tjekke, om vores testWish faktisk blev oprettet
        WishList actualWishList = captor.getValue();
        assertEquals(name, actualWishList.getName());
        assertEquals(description, actualWishList.getDescription());
        assertEquals(testProfile.getId(), actualWishList.getProfileId());
        assertEquals("redirect:/" + testProfile.getId() + "/wishlists", viewName);
    }


    // -------------------------------- Henter readALLWishLists() ------------------------------

    @Test
    void testGetWishLists() {
        // Arrange - opretter to ønskelister med ønsker
        List<WishList> fakeWishLists = List.of(
                new WishList(1, "Jul", "Gaver", 1),
                new WishList(2, "Fødselsdag", "Flere gaver", 1)
        );

        ProfileWishListDTO dto = new ProfileWishListDTO(testProfile, fakeWishLists);

        lenient().when(profileWishListService.findProfileWithWishLists(testProfile.getId())).thenReturn(dto);

        // Act - kalder metoden, jeg vil teste: getWishLists()
        String viewName = controller.getWishLists(testProfile.getId(), session, model);


        // Assert - tjekker at modellen tager dto-objektet og profilId med + viewName
        verify(model).addAttribute("dto", dto);
        verify(model).addAttribute("profileId", testProfile.getId());
        assertEquals("wishlists", viewName);
    }

    // -------------------------------- Henter readOneWishList() ------------------------------

    @Test
    void testGetSpecificWishList() {

        //Arrange
        int profileId = 1;
        int wishListId = 10;

        lenient().when(wishWishListService.findWishWithWishList(wishListId)).thenReturn(wishWishListDTO);

        //Act - kalder metoden, jeg vil teste: getSpecificWishList()
        String viewName = controller.getSpecificWishList(profileId, wishListId, session, model);


        //Assert - tjekker at modellen tager dto-objektet med og profilID + viewName()
        verify(model).addAttribute("dto", wishWishListDTO);
        verify(model).addAttribute("profileId", profileId);
        assertEquals("wishlist", viewName);
    }

    // -------------------------------- Henter updateWishList()  ------------------------------

    @Test
    void testGetEditWishList() {
        //Arrange
        int profileId = testProfile.getId();
        int wishlistId = 10;

        //Act - kalder metoden, jeg vil teste: getEditWishList()
        String viewName = controller.getEditWishList(profileId, wishlistId, session, model);


        //Assert - tjekker at modellen tager profilID og ønskelisteID med + viewName
        verify(model).addAttribute("profileId", profileId);
        verify(model).addAttribute("wishlistId", wishlistId);
        assertEquals("wishlists", viewName);

    }

    // -------------------------------- Post updateWishList()  ------------------------------

    @Test
    void testPostEditWishList() {

        //Arrange
        int profileId = testProfile.getId();
        int wishlistId = 10;
        String name = "Christmas";
        String description = "Update description";

        //Act - kalder metoden, jeg vil teste: postEditWishList()
        String viewName = controller.postEditWishList(profileId, wishlistId, name, description, session);
        ArgumentCaptor<WishList> captor = ArgumentCaptor.forClass(WishList.class);

        //Assert - tjekker at ønskelisten opdateres + sammenligner værdierne for de to ønskelister (skal være de samme)
        verify(iWishListService).update(captor.capture());
        WishList updated = captor.getValue();
        assertEquals(wishlistId, updated.getId());
        assertEquals(name, updated.getName());
        assertEquals(description, updated.getDescription());
        assertEquals(profileId, updated.getProfileId());
        assertEquals("redirect:/" + profileId + "/wishlists", viewName);

    }
    // -------------------------------- deleteWishlist()  ------------------------------

    @Test
    void testDeleteWishList() {

        //Arrange
        int profileId = testProfile.getId();
        int wishlistId = 10;

        //Act - kalder metoden, jeg vil teste: deleteWishList()
        String viewName = controller.deleteWishList(profileId, wishlistId, session);


        //Assert - tjekker at ønskelisten blev slettet + redirect
        verify(iWishListService).deleteById(wishlistId);
        assertEquals("redirect:/" + profileId + "/wishlists", viewName);
    }
}