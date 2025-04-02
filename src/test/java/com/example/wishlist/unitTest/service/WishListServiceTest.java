package com.example.wishlist.unitTest.service;

import com.example.wishlist.exception.ResourceNotFoundException;
import com.example.wishlist.model.Wish;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void findById_shouldReturnWish_whenFound() {

        //Arrange
        when(iWishListRepository.findById(200)).thenReturn(testWishList);

        WishList result = wishListService.findById(200);

        //Act & Assert

        assertNotNull(result);
        assertEquals("ØnskeListeTest", result.getName());
        assertEquals("Test", result.getDescription());
        assertEquals(1, result.getProfileId());

        verify(iWishListRepository, times(1)).findById(anyInt());
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        when(iWishListRepository.findById(2)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> wishListService.findById(2));

        verify(iWishListRepository, times(1)).findById(anyInt());
    }

    // ---------------- deleteById() ---------------- METODE

    @Test
    void deleteById_shouldCallDelete_whenWishExists() {
        when(iWishListRepository.findById(1)).thenReturn(testWishList);

        wishListService.deleteById(1);

        verify(iWishListRepository).deleteById(1);
        verify(iWishListRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void deleteById_shouldThrowException_whenWishNotFound() {

        when(iWishListRepository.findById(2)).thenReturn(null); //2 = dummy id

        assertThrows(ResourceNotFoundException.class, () -> wishListService.deleteById(2));

        verify(iWishListRepository, never()).deleteById(anyInt());
    }
    // ------------------ update() -------------------- METODE

    @Test
    void update_shouldCallUpdate_whenWishNotNull() {
        wishListService.update(testWishList);

        verify(iWishListRepository).update(testWishList);

        verify(iWishListRepository, times(1)).update(testWishList);
    }

    @Test
    void update_shouldThrowException_whenWishIsNull() {
        assertThrows(ResourceNotFoundException.class, () -> wishListService.update(null));

        verify(iWishListRepository, never()).update(testWishList);
    }

    // -------------------- save() -------------------- METODE

    @Test
    void save_shouldCallSave() {
        wishListService.save(testWishList);

        verify(iWishListRepository).save(testWishList);
        verify(iWishListRepository, times(1)).save(testWishList);

    }

    // -------------------- create() ----------------- METODE

    @Test
    void create_shouldCallCreate() {
        wishListService.create(testWishList);

        verify(iWishListRepository).create(testWishList);
        verify(iWishListRepository, times(1)).create(testWishList);
    }

    // -------------------- findAll() ----------------- METODE

    @Test
    void findAll_shouldReturnList() {

        when(iWishListRepository.findAll()).thenReturn(List.of(testWishList));

        List<WishList> result = wishListService.findAll();

        assertEquals(1, result.size());
        assertEquals("ØnskeListeTest", result.get(0).getName());
        assertEquals("Test", result.get(0).getDescription());
        assertEquals(1, result.get(0).getProfileId());

        verify(iWishListRepository).findAll();
        verify(iWishListRepository, times(1)).findAll();
    }
}
