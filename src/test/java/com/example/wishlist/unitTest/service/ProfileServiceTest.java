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
    private IProfileRepository iProfileRepository; //Mock'er repository, så vi ikke behøver database

    @InjectMocks
    private ProfileService profileService; //Opretter instans af @Service - vi tester kun service-laget

    private Profile testProfile;

    @BeforeEach
    void setUp() { //Alle felter for Profile
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
    void findById_shouldReturnProfile_whenFound() {

        //Arrange
        when(iProfileRepository.findById(1)).thenReturn(testProfile);

        Profile result = profileService.findById(1);

        //Act & Assert
        assertNotNull(result);
        assertEquals("Test Name", result.getName());
        assertEquals("testUser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("secret123", result.getPassword());
        assertEquals(LocalDate.of(1990, 1, 1), result.getBirthday());

        verify(iProfileRepository, times(1)).findById(anyInt());
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        when(iProfileRepository.findById(999)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> profileService.findById(999));

        verify(iProfileRepository, never()).deleteById(anyInt());
    }

    // ---------------- deleteById() ---------------- METODE

    @Test
    void deleteById_shouldCallDelete_whenProfileExists() {
        when(iProfileRepository.findById(1)).thenReturn(testProfile);

        profileService.deleteById(1);

        verify(iProfileRepository).deleteById(1);
        verify(iProfileRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void deleteById_shouldThrowException_whenProfileNotFound() {

        when(iProfileRepository.findById(999)).thenReturn(null); //999 = dummy id

        assertThrows(ResourceNotFoundException.class, () -> profileService.deleteById(999)); //lampda = inline funktion

        verify(iProfileRepository, never()).deleteById(anyInt());
    }

    // ------------------ update() -------------------- METODE

    @Test
    void update_shouldCallUpdate_whenProfileNotNull() {

        profileService.update(testProfile);

        verify(iProfileRepository).update(testProfile);

        verify(iProfileRepository, times(1)).update(testProfile);
    }

    @Test
    void update_shouldThrowException_whenProfileIsNull() {

        assertThrows(ResourceNotFoundException.class, () -> profileService.update(null));

        verify(iProfileRepository, never()).update(testProfile);
    }

    // -------------------- create() ----------------- METODE

    @Test
    void create_shouldCallCreate() {
        profileService.create(testProfile);

        verify(iProfileRepository).create(testProfile);
        verify(iProfileRepository, times(1)).create(testProfile);
    }

    // -------------------- findAll() ----------------- METODE

    @Test
    void findAll_shouldReturnList() {
        when(iProfileRepository.findAll()).thenReturn(List.of(testProfile));

        List<Profile> result = profileService.findAll();

        assertEquals(1, result.size());
        assertEquals("testUser", result.get(0).getUsername());
        assertEquals("secret123", result.get(0).getPassword());
        verify(iProfileRepository).findAll();
        verify(iProfileRepository, times(1)).findAll();
    }
}