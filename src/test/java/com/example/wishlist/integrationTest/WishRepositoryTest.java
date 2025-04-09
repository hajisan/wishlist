package com.example.wishlist.integrationTest;

import com.example.wishlist.model.Wish;
import com.example.wishlist.repository.WishRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"classpath:h2init.sql"}
)
class WishRepositoryTest {

    @Autowired
    WishRepository wishRepository;

    @Test
    void create() {
        // Arrange
        String newWishName = "Arnes Oral-B tandbørste";
        String newWish1Description = "Ninja Airfryer";
        String newWishLink = "https://www.elgiganten.dk/product/hjem-rengoring-kokkenudstyr/kokkenudstyr/airfryer/ninja-airfryer-foodi-dual-zone-af300eu/244981?gStoreCode=3074&gQT=1";
        int newWishQuantity = 1;
        double newWishPrice = 1300.0;
        int wishListId = 1;

        // Act
        wishRepository.create(new Wish(newWishName, newWish1Description, newWishLink, newWishQuantity, newWishPrice, wishListId));
        Wish testWishFromDatabase1 = wishRepository.findById(1);
        Wish testWishFromDatabase3 = wishRepository.findById(5);

        // Assert
        assertEquals("Arnes Yndlingsairfryer", testWishFromDatabase1.getName());
        assertEquals(newWishName, testWishFromDatabase3.getName());
    }

    @Test
    void findById() {
        // Arrange
        String wish1Name = "Arnes Yndlingsairfryer";
        String wish1Link = "https://www.elgiganten.dk/product/hjem-rengoring-kokkenudstyr/kokkenudstyr/airfryer/ninja-airfryer-foodi-dual-zone-af300eu/244981?gStoreCode=3074&gQT=1";


        String wish4Name = "Birgers Spisepinde";
        String wish4Link = "https://www.ikea.com/dk/da/p/trebent-spisepinde-4-saet-bambus-90342971/?utm_source=google&utm_medium=surfaces&utm_campaign=shopping_feed&utm_content=free_google_shopping_clicks_Eating&gStoreCode=686&gQT=1";


        // Act
        Wish testWishFromDatabase1 = wishRepository.findById(1);
        Wish testWishFromDatabase4 = wishRepository.findById(4);

        // Assert
        assertEquals(wish1Name, testWishFromDatabase1.getName());
        assertEquals(wish1Link, testWishFromDatabase1.getLink());


        assertEquals(wish4Name, testWishFromDatabase4.getName());
        assertEquals(wish4Link, testWishFromDatabase4.getLink());
    }

    @Test
    void findAll() {
        // Arrange - Opretter en liste med profilernes navne
        List<String> wishNames = new ArrayList<>(Arrays.asList(
                "Arnes Yndlingsairfryer",
                "Arnes 85 tommers fladskærm med Energimærke G",
                "Birgers Lurpak",
                "Birgers Spisepinde"
        ));

        // Act
        List<Wish> testList = wishRepository.findAll();

        // Assert
        for (int i = 0; i < testList.size(); i++) {
            assertEquals(wishNames.get(i), testList.get(i).getName());
        }
    }

    @Test
    void deleteById() {
        // Arrange
        String wishName = "Birgers Lurpak";
        String deletedWishListName = "Arnes Yndlingsairfryer";

        // Act
        wishRepository.deleteById(1);
        Wish testWishFromDatabase = wishRepository.findById(3);
        List<Wish> testList = new ArrayList<>(wishRepository.findAll());

        // Assert
        assertEquals(wishName, testWishFromDatabase.getName());
        assertNotEquals(wishName, deletedWishListName);
        assertEquals(3, testList.size());
    }

    @Test
    void update() {
        // Arrange
        String wishNameBeforeUpdate = "Arnes 85 tommers fladskærm med Energimærke G";
        String wishDescriptionBeforeUpdate = "Samsung 4K Smart TV";
        String wishNameAfterUpdate = "Annikas Polestar 2";
        String wishDescriptionAfterUpdate = "Måske lidt for dyr til en ønskeliste...";
        Wish updatedWish = new Wish(wishNameAfterUpdate, wishDescriptionAfterUpdate, "https://www.google.dk", 2, 1, 500000.0, 1);

        // Act
        wishRepository.update(updatedWish);

        // Assert
        assertNotEquals(wishNameBeforeUpdate, wishRepository.findById(2).getName());
        assertNotEquals(wishDescriptionBeforeUpdate, wishRepository.findById(2).getDescription());
        assertEquals(wishNameAfterUpdate, wishRepository.findById(2).getName());
        assertEquals(wishDescriptionAfterUpdate, wishRepository.findById(2).getDescription());
    }

    @Test
    void findWishesByWishListId() {
        // Arrange
        String wishName = "Arnes Yndlingsairfryer";
        String wishDescription = "Ninja Airfryer";
        String wishLink = "https://www.elgiganten.dk/product/hjem-rengoring-kokkenudstyr/kokkenudstyr/airfryer/ninja-airfryer-foodi-dual-zone-af300eu/244981?gStoreCode=3074&gQT=1";
        int wishQuantity = 1;
        double wishPrice = 1329.0;
        int wishListId = 1;
        List<Wish> desiredWishes = new ArrayList<>();
        Wish wishesInListOfWishes = new Wish(wishName, wishDescription, wishLink, 1, wishQuantity, wishPrice, wishListId);
        desiredWishes.add(wishesInListOfWishes);

        // Act
        List<Wish> actualWishesFromDatabase = new ArrayList<>(wishRepository.findWishesByWishListId(wishListId));

        // Assert
        assertEquals(desiredWishes.get(0).getName(), actualWishesFromDatabase.get(0).getName());
        assertEquals(desiredWishes.get(0).getDescription(), actualWishesFromDatabase.get(0).getDescription());
    }
}