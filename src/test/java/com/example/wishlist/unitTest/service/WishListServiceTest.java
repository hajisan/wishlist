package com.example.wishlist.unitTest.service;

import com.example.wishlist.exception.ResourceNotFoundException;
import com.example.wishlist.model.WishList;
import com.example.wishlist.repository.IWishListRepository;
import com.example.wishlist.service.WishListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Til at kunne bruge @Mock og @InjectMock
public class WishListServiceTest {

    @Mock
    private IWishListRepository iWishListRepository; // Mocker databasen

    @InjectMocks
    private WishListService wishListService; // Instantierer @Service

    private WishList testWishList;

    @BeforeEach
    void setUp() {
        testWishList = new WishList();
        testWishList.setId(200);
        testWishList.setName("ØnskeListeTest");
        testWishList.setDescription("Test");
        testWishList.setProfileId(1);
    }

    // --------------- findById() ---------------------- METODE

    @Test
    void testFindByIdShouldReturnWishWhenFound() {
        // Arrange
        lenient().when(iWishListRepository.findById(200)).thenReturn(testWishList);

        // Act & Assert
        WishList result = wishListService.findById(200);

        assertNotNull(result);
        assertEquals("ØnskeListeTest", result.getName());
        assertEquals("Test", result.getDescription());
        assertEquals(1, result.getProfileId());

        verify(iWishListRepository, times(1)).findById(anyInt());
    }

    @Test
    void testFindByIdShouldThrowExceptionWhenNotFound() {
        // Arrange
        lenient().when(iWishListRepository.findById(2)).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> wishListService.findById(2));
        verify(iWishListRepository, times(1)).findById(anyInt());
    }

    // ---------------- deleteById() ---------------- METODE

    @Test
    void testDeleteByIdShouldCallDeleteWhenWishExists() {
        // Arrange
        lenient().when(iWishListRepository.findById(1)).thenReturn(testWishList);

        // Act & Assert
        wishListService.deleteById(1);
        verify(iWishListRepository).deleteById(1);
        verify(iWishListRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void testDeleteByIdShouldThrowExceptionWhenWishNotFound() {
        // Arrange
        lenient().when(iWishListRepository.findById(2)).thenReturn(null); //2 = dummy id

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> wishListService.deleteById(2));
        verify(iWishListRepository, never()).deleteById(anyInt());
    }
    // ------------------ update() -------------------- METODE

    @Test
    void testUpdateShouldCallUpdateWhenWishNotNull() {
        // Arrange
        wishListService.update(testWishList);

        // Act & Assert
        verify(iWishListRepository).update(testWishList);
        verify(iWishListRepository, times(1)).update(testWishList);
    }

    @Test
    void testUpdateShouldThrowExceptionWhenWishIsNull() {
        // Arrange

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> wishListService.update(null));

        verify(iWishListRepository, never()).update(testWishList);
    }

    // -------------------- create() ----------------- METODE

    @Test
    void testCreateShouldCallCreate() {
        // Arrange

        // Act & Assert
        wishListService.create(testWishList);

        verify(iWishListRepository).create(testWishList);
        verify(iWishListRepository, times(1)).create(testWishList);
    }

    // -------------------- findAll() ----------------- METODE

    @Test
    void testFindAllShouldReturnList() {
        // Arrange
        lenient().when(iWishListRepository.findAll()).thenReturn(List.of(testWishList));

        // Act & Assert
        List<WishList> result = wishListService.findAll();

        assertEquals(1, result.size());
        assertEquals("ØnskeListeTest", result.get(0).getName());
        assertEquals("Test", result.get(0).getDescription());
        assertEquals(1, result.get(0).getProfileId());

        verify(iWishListRepository).findAll();
        verify(iWishListRepository, times(1)).findAll();
    }
}