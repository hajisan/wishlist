package com.example.wishlist.unitTest.controller;

import com.example.wishlist.dto.ProfileWishListDTO;
import com.example.wishlist.model.Profile;
import com.example.wishlist.controller.WishListController;

import com.example.wishlist.model.WishList;
import com.example.wishlist.service.IWishListService;
import com.example.wishlist.service.ProfileWishListService;
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

        when(session.getAttribute("profile")).thenReturn(testProfile);
    }


//    @Test
//    void testGetWishLists_shouldAddUsernameAndWishlistsToModel() {
//        // Arrange
//        List<WishList> fakeWishLists = List.of(
//                new WishList(1, "Jul", "Gaver", 1),
//                new WishList(2, "Fødselsdag", "Flere gaver", 1)
//        );
//
//        ProfileWishListDTO dto = new ProfileWishListDTO(testProfile, fakeWishLists);
//
//        when(profileWishListService.findProfileWithWishLists(testProfile.getId())).thenReturn(dto);
//
//        // Act
//        String viewName = controller.getWishLists(testProfile.getId(), session, model);
//
//        // Assert
//        verify(model).addAttribute("username", "testuser");
//        verify(model).addAttribute("wishlists", dto);
//        assertEquals("wishlists", viewName);
//    }

    @Test
    void testGetCreateWishList_shouldAddUsernameAndReturnView() {
        String viewName = controller.getCreateWishList("testuser", session, model);

        verify(model).addAttribute("username", "testuser");
        assertEquals("create-wishlist", viewName);
    }


    @Test
    void testPostCreateWishList() {
        //Arrange + @BeforeEach
        String name = "Christmas";
        String description = "Wishes";

        //Act
        String viewName = controller.postCreateWishList("testuser", name, description, session, model);

        //Assert -- kaldes create() én gang + sammenligner indholdet
        ArgumentCaptor<WishList> captor = ArgumentCaptor.forClass(WishList.class);

        verify(iWishListService).create(captor.capture());

        WishList actualWishList = captor.getValue();

        assertEquals(name, actualWishList.getName());
        assertEquals(description, actualWishList.getDescription());
        assertEquals(testProfile.getId(), actualWishList.getProfileId());


        verify(model).addAttribute("username", "testuser");
        assertEquals("create-wishlist", viewName);
    }

    @Test
    void testGetSpecificWishList_shouldAddUsernameAndWishlistAndReturnView() {
        String wishListName = "Christmas";

        WishList fakeWishList = new WishList(1, "Jul", "Gaver", 1);

        when(profileWishListService.findSpecificWishListByProfileId(wishListName, testProfile.getId()))
                .thenReturn(fakeWishList);

        String viewName = controller.getSpecificWishList("testuser", wishListName, session, model);

        verify(model).addAttribute("username", "testuser");
        verify(model).addAttribute("wishlist", fakeWishList);
        assertEquals("wishlist", viewName);

    }

    @Test
    void testGetEditWishList_shouldReturnViewAndAddAttributes() {
        String wishlistName = "Christmas";
        WishList fakeWishList = new WishList(1, wishlistName, "old description", testProfile.getId());
        when(profileWishListService.findSpecificWishListByProfileId(wishlistName, testProfile.getId()))
                .thenReturn(fakeWishList);

        String viewName = controller.getEditWishList("testuser", wishlistName, session, model);

        verify(model).addAttribute("username", "testuser");
        verify(model).addAttribute("wishlist", fakeWishList);
        assertEquals("edit-wishlist", viewName);
    }

    @Test
    void testPostEditWishList_shouldUpdateAndReturnView() {
        String oldName = "Christmas";
        String newName = "Updated Name";
        String newDescription = "Update description";

        WishList existingWishList = new WishList(1, oldName, "old description", testProfile.getId());

        when(profileWishListService.findSpecificWishListByProfileId(oldName, testProfile.getId()))
                .thenReturn(existingWishList);

        String viewName = controller.postEditWishList("testuser", oldName, newName, newDescription, session, model);

        // Opdateres ønskelisten?
        assertEquals(newName, existingWishList.getName());
        assertEquals(newDescription, existingWishList.getDescription());

        verify(iWishListService).update(existingWishList);
        verify(model).addAttribute("username", "testuser");
        verify(model).addAttribute("oldname", oldName);
        assertEquals("wishlist", viewName);


    }
}







