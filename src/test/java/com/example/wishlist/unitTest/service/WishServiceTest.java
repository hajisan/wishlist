package com.example.wishlist.unitTest.service;


import com.example.wishlist.exception.ResourceNotFoundException;
import com.example.wishlist.model.Wish;
import com.example.wishlist.repository.IWishRepository;
import com.example.wishlist.service.WishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //Til at kunne bruge @Mock og @InjectMock
public class WishServiceTest {

    @Mock
    private IWishRepository iWishRepository; //Mocker databasen

    @InjectMocks
    private WishService wishService; //Instantierer @Service

    private Wish testWish;

    @BeforeEach
    void setUp() {
        testWish = new Wish();
        testWish.setId(1);
        testWish.setName("Ønsketest");
        testWish.setDescription("Test");
        testWish.setLink("URL");
        testWish.setQuantity(1);
        testWish.setPrice(1);
    }

    // --------------- findById() ---------------------- METODE

    @Test
    void findById_shouldReturnWish_whenFound() {

        //Arrange
        when(iWishRepository.findById(1)).thenReturn(testWish);

        Wish result = wishService.findById(1);

        //Act & Assert

        assertNotNull(result);
        assertEquals("Ønsketest", result.getName());
        assertEquals("Test", result.getDescription());
        assertEquals("URL", result.getLink());
        assertEquals(1, result.getQuantity());
        assertEquals(1, result.getPrice());

        verify(iWishRepository, times(1)).findById(anyInt());
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        when(iWishRepository.findById(2)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> wishService.findById(2));

        verify(iWishRepository, times(1)).findById(anyInt());
    }

    // ---------------- deleteById() ---------------- METODE

    @Test
    void deleteById_shouldCallDelete_whenWishExists() {
        when(iWishRepository.findById(1)).thenReturn(testWish);

        wishService.deleteById(1);

        verify(iWishRepository).deleteById(1);
        verify(iWishRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void deleteById_shouldThrowException_whenWishNotFound() {

        when(iWishRepository.findById(2)).thenReturn(null); //2 = dummy id

        assertThrows(ResourceNotFoundException.class, () -> wishService.deleteById(2));

        verify(iWishRepository, never()).deleteById(anyInt());
    }
    // ------------------ update() -------------------- METODE

    @Test
    void update_shouldCallUpdate_whenWishNotNull() {
        wishService.update(testWish);

        verify(iWishRepository).update(testWish);

        verify(iWishRepository, times(1)).update(testWish);
    }

    @Test
    void update_shouldThrowException_whenWishIsNull() {
        assertThrows(ResourceNotFoundException.class, () -> wishService.update(null));

        verify(iWishRepository, never()).update(testWish);
    }

    // -------------------- create() ----------------- METODE

    @Test
    void create_shouldCallCreate() {
        wishService.create(testWish);

        verify(iWishRepository).create(testWish);
        verify(iWishRepository, times(1)).create(testWish);
    }

    // -------------------- findAll() ----------------- METODE

    @Test
    void findAll_shouldReturnList() {

        when(iWishRepository.findAll()).thenReturn(List.of(testWish));

        List<Wish> result = wishService.findAll();

        assertEquals(1, result.size());
        assertEquals("Ønsketest", result.get(0).getName());
        assertEquals("Test", result.get(0).getDescription());
        assertEquals("URL", result.get(0).getLink());
        assertEquals(1, result.get(0).getQuantity());
        assertEquals(1, result.get(0).getPrice());
        verify(iWishRepository).findAll();
        verify(iWishRepository, times(1)).findAll();
    }
}


