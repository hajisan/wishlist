package com.example.wishlist.unitTest.service;

import com.example.wishlist.dto.ProfileWishListDTO;
import com.example.wishlist.exception.ResourceNotFoundException;
import com.example.wishlist.model.Profile;
import com.example.wishlist.model.WishList;
import com.example.wishlist.repository.IProfileRepository;
import com.example.wishlist.repository.IWishListRepository;

import com.example.wishlist.service.ProfileWishListService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileWishListServiceTest {

    @Mock
    private IProfileRepository iProfileRepository;

    @Mock
    private IWishListRepository iWishListRepository;

    @InjectMocks
    private ProfileWishListService profileWishListService;

    @Test
    void testFindProfileWithWishListsReturnsDTOWhenProfileExists() {

        // Arrange
        Profile profile = new Profile(
                1,
                "Test Name",
                LocalDate.of(1995, 8, 28),
                "test@example.com",
                "testUser",
                "secret123"
        );

        List<WishList> wishLists = List.of(
                new WishList(10, "Christmas", "horse", 1),
                new WishList(11, "Birthday", "gaming gear", 1)
        );

        Mockito.lenient().when(iProfileRepository.findById(1)).thenReturn(profile);
        Mockito.lenient().when(iWishListRepository.findWishListsByProfileId(1)).thenReturn(wishLists);

        // Act

        ProfileWishListDTO dto = profileWishListService.findProfileWithWishLists(1);

        // Assert
        assertEquals(profile, dto.profile());     // Sammenligner dto'ens indhold med vores hjemmelavede profil
        assertEquals(wishLists, dto.wishLists()); // og vores hjemmelavede liste af wishlists

        verify(iProfileRepository, times(1)).findById(anyInt());
        verify(iWishListRepository, times(1)).findWishListsByProfileId(anyInt());

    }

    @Test
    void testFindProfileWithWishListsThrowsExceptionWhenProfileNotFound() {
        // Arrange
        int profileId = 404;

        Mockito.when(iProfileRepository.findById(profileId)).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> profileWishListService.findProfileWithWishLists(profileId)
        );

        verify(iWishListRepository, never()).findWishListsByProfileId(anyInt());
    }
}
