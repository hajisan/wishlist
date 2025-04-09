package com.example.wishlist.integrationTest;

import com.example.wishlist.model.Profile;
import com.example.wishlist.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"classpath:h2init.sql"}
)
class ProfileRepositoryTest {

    @Autowired
    ProfileRepository profileRepository;


    @Test
    void create() {
        // Arrange
        String profile1Name = "Arne";
        String profile2Name = "Birger";
        String profile3Name = "Charlie";

        // Act - Indsæt ny profil i databasen og gem personer fra deres ID (ikke muligt uden også at kalde andre metoder)
        profileRepository.create(new Profile("Charlie", Profile.getLocalDateFromString("1967-11-12"), "Charlies@Angels.dk", "charchar", "angels4lyfe"));
        Profile testProfileFromDatabase1 = profileRepository.findById(1);
        Profile testProfileFromDatabase2 = profileRepository.findById(2);
        Profile testProfileFromDatabase3 = profileRepository.findById(3);

        // Assert - Tester om navn stemmer overens for både at teste om den nye person blev indsat
        //          og for at teste, om den blev indsat med det rigtige ID
        assertEquals(profile1Name, testProfileFromDatabase1.getName());
        assertEquals(profile2Name, testProfileFromDatabase2.getName());
        assertEquals(profile3Name, testProfileFromDatabase3.getName());
    }

    @Test
    void findById() {
        // Arrange
        String profile1Name = "Arne";
        LocalDate profile1Birthday = LocalDate.of(1995, 12, 12);
        String profile1Email = "Test1@example.com";

        String profile2Name = "Birger";
        LocalDate profile2Birthday = LocalDate.of(2000, 1, 1);
        String profile2Email = "Test2@example.com";

        // Act - Hent profiler fra database med deres ID
        Profile testProfileFromDatabase1 = profileRepository.findById(1);
        Profile testProfileFromDatabase2 = profileRepository.findById(2);

        // Assert
        assertEquals(profile1Name, testProfileFromDatabase1.getName());
        assertEquals(profile1Birthday, testProfileFromDatabase1.getBirthday());
        assertEquals(profile1Email, testProfileFromDatabase1.getEmail());

        assertEquals(profile2Name, testProfileFromDatabase2.getName());
        assertEquals(profile2Birthday, testProfileFromDatabase2.getBirthday());
        assertEquals(profile2Email, testProfileFromDatabase2.getEmail());
    }

    @Test
    void findAll() {
        // Arrange - Opretter en liste med profilernes navne
        List<String> profileNames = new ArrayList<>(Arrays.asList("Arne", "Birger"));

        // Act
        List<Profile> testList = profileRepository.findAll();

        // Assert
        for (int i = 0; i < testList.size(); i++) {
            assertEquals(profileNames.get(i), testList.get(i).getName());
        }
        // assertEquals(profileNames.get(0), testList.get(0).getName());
        // assertEquals(profileNames.get(1), testList.get(1).getName());
    }

    @Test
    void deleteById() {
        // Arrange -
        String profileName = "Birger";
        LocalDate profileBirthday = LocalDate.of(2000, 1, 1);
        String profileEmail = "Test2@example.com";
        String deletedProfileName = "Arne";

        // Act -
        profileRepository.deleteById(1);
        Profile testProfileFromDatabase = profileRepository.findById(2);
        List<Profile> testList = new ArrayList<>(profileRepository.findAll());

        // Assert -
        assertEquals(profileName, testProfileFromDatabase.getName());
        assertEquals(profileBirthday, testProfileFromDatabase.getBirthday());
        assertEquals(profileEmail, testProfileFromDatabase.getEmail());
        assertNotEquals(profileName, deletedProfileName);
        assertEquals(1, testList.size());
    }

    @Test
    void update() {
        // Arrange
        String profileNameBeforeUpdate = "Arne";
        LocalDate profileBirthdayBeforeUpdate = LocalDate.of(1995, 12, 12);
        String profileNameAfterUpdate = "Annika";
        LocalDate profileBirthdayAfterUpdate = LocalDate.of(1999, 6, 26);
        Profile updatedProfile = new Profile(1, profileNameAfterUpdate, profileBirthdayAfterUpdate, "Test1@example.com", "test1", "12345678");

        // Act
        profileRepository.update(updatedProfile);

        // Assert
        assertNotEquals(profileNameBeforeUpdate, profileRepository.findById(1).getName());
        assertNotEquals(profileBirthdayBeforeUpdate, profileRepository.findById(1).getBirthday());
        assertEquals(profileNameAfterUpdate, profileRepository.findById(1).getName());
        assertEquals(profileBirthdayAfterUpdate, profileRepository.findById(1).getBirthday());
    }

    @Test
    void findProfileByUserName() {
        // Arrange -
        String desiredProfileName = "Arne";
        String undesiredProfileName = "Birger";
        int desiredId = 1;
        String profileUserName = "test1";

        // Act -
        Profile testProfileFromDatabase = profileRepository.findProfileByUserName(profileUserName);

        // Assert -
        assertEquals(desiredId, testProfileFromDatabase.getId());
        assertEquals(desiredProfileName, testProfileFromDatabase.getName());
        assertNotEquals(undesiredProfileName, testProfileFromDatabase.getName());
    }
}