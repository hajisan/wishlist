package com.example.wishlist.unitTest.service;

import com.example.wishlist.exception.ResourceNotFoundException;
import com.example.wishlist.model.Profile;
import com.example.wishlist.repository.IProfileRepository;
import com.example.wishlist.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //For at kunne bruge @Mock og @InjectMock
public class ProfileServiceTest {

    @Mock
    private IProfileRepository iProfileRepository; //Mocker repository, så vi ikke behøver database

    @InjectMocks
    private ProfileService profileService; // Opretter instans af @Service - vi tester kun service-laget

    private Profile testProfile;

    @BeforeEach
    void setUp() { // Alle felter for Profile
        // Arrange for alle testmetoder
        testProfile = new Profile();
        testProfile.setId(1);
        testProfile.setName("Test Name");
        testProfile.setEmail("test@example.com");
        testProfile.setUsername("testUser");
        testProfile.setPassword("secret123");
        testProfile.setBirthday(LocalDate.of(1990, 1, 1));
    }

    // --------------- findById() ---------------------- METODE

    @Test
    void testFindByIdShouldReturnProfileWhenProfileIsFound() {
        // Arrange
        lenient().when(iProfileRepository.findById(1)).thenReturn(testProfile);

        // Act & Assert
        Profile result = profileService.findById(1);

        assertNotNull(result);
        assertEquals("Test Name", result.getName());
        assertEquals("testUser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("secret123", result.getPassword());
        assertEquals(LocalDate.of(1990, 1, 1), result.getBirthday());

        verify(iProfileRepository, times(1)).findById(anyInt());
    }

    @Test
    void testFindByIdShouldThrowExceptionWhenProfileNotFound() {
        // Arrange
        lenient().when(iProfileRepository.findById(999)).thenReturn(null);

        // Act & Assert - forventer at metoden kaster en exception, når ikke-eksisterende ID gives som argument
        assertThrows(ResourceNotFoundException.class, () -> profileService.findById(999));

        //Ikke kald deleteById(), hvis ID ikke findes
        verify(iProfileRepository, never()).deleteById(anyInt());
    }

    // ---------------- deleteById() ---------------- METODE

    @Test
    void testDeleteByIdShouldCallDeleteWhenProfileExists() {
        // Arrange
        lenient().when(iProfileRepository.findById(1)).thenReturn(testProfile);

        // Act & Assert
        profileService.deleteById(1);

        verify(iProfileRepository).deleteById(1);
        verify(iProfileRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void testDeleteByIdShouldThrowExceptionWhenProfileNotFound() {
        // Arrange
        when(iProfileRepository.findById(999)).thenReturn(null); //999 = dummy id

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> profileService.deleteById(999)); //lampda = inline funktion
        verify(iProfileRepository, never()).deleteById(anyInt());
    }

    // ------------------ update() -------------------- METODE

    @Test
    void testUpdateShouldCallUpdateWhenProfileNotNull() {
        //Arrange

        // Act & Assert
        profileService.update(testProfile);
        verify(iProfileRepository).update(testProfile);
        verify(iProfileRepository, times(1)).update(testProfile);
    }

    @Test
    void testUpdateShouldThrowExceptionWhenProfileIsNull() {
        // Arrange

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> profileService.update(null));
        verify(iProfileRepository, never()).update(testProfile);
    }

    // -------------------- create() ----------------- METODE

    @Test
    void testCreateShouldCallCreate() {
        // Arrange

        // Act & Assert
        profileService.create(testProfile);
        verify(iProfileRepository).create(testProfile);
        verify(iProfileRepository, times(1)).create(testProfile);
    }

    // -------------------- findAll() ----------------- METODE

    @Test
    void testFindAllShouldReturnList() {
        // Arrange
        lenient().when(iProfileRepository.findAll()).thenReturn(List.of(testProfile));

        // Act & Assert
        List<Profile> result = profileService.findAll();

        assertEquals(1, result.size());
        assertEquals("testUser", result.get(0).getUsername());
        assertEquals("secret123", result.get(0).getPassword());
        verify(iProfileRepository).findAll();
        verify(iProfileRepository, times(1)).findAll();
    }
}