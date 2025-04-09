package com.example.wishlist.integrationTest;

import com.example.wishlist.model.WishList;
import com.example.wishlist.repository.WishListRepository;
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
class WishListRepositoryTest {

    @Autowired
    WishListRepository wishListRepository;

    @Test
    void create() {
        // Arrange
        String newWishListName = "Christmas 2025";
        String newWishListDescription = "Electronics Not Allowed!";
        String wishList1Name = "Arnes Liste";
        String wishList2Name = "Birgers Liste";

        // Act
        wishListRepository.create(new WishList(3, newWishListName, newWishListDescription, 1));
        WishList testWishListFromDatabase1 = wishListRepository.findById(1);
        WishList testWishListFromDatabase2 = wishListRepository.findById(2);
        WishList testWishListFromDatabase3 = wishListRepository.findById(3);

        // Assert
        assertEquals(wishList1Name, testWishListFromDatabase1.getName());
        assertEquals(wishList2Name, testWishListFromDatabase2.getName());
        assertEquals(newWishListName, testWishListFromDatabase3.getName());
        assertEquals(newWishListDescription, testWishListFromDatabase3.getDescription());
    }

    @Test
    void findById() {
        // Arrange
        String wishList1Name = "Arnes Liste";
        String wishList1Description = "Ting til Arne";


        String wishList2Name = "Birgers Liste";
        String wishList2Description = "Birgers ønsker";


        // Act
        WishList testWishlistFromDatabase1 = wishListRepository.findById(1);
        WishList testWishListFromDatabase2 = wishListRepository.findById(2);

        // Assert
        assertEquals(wishList1Name, testWishlistFromDatabase1.getName());
        assertEquals(wishList1Description, testWishlistFromDatabase1.getDescription());


        assertEquals(wishList2Name, testWishListFromDatabase2.getName());
        assertEquals(wishList2Description, testWishListFromDatabase2.getDescription());
    }

    @Test
    void findAll() {
        // Arrange - Opretter en liste med profilernes navne
        List<String> wishlistNames = new ArrayList<>(Arrays.asList("Arnes Liste", "Birgers Liste"));

        // Act
        List<WishList> testList = wishListRepository.findAll();

        // Assert
        for (int i = 0; i < testList.size(); i++) {
            assertEquals(wishlistNames.get(i), testList.get(i).getName());
        }
    }

    @Test
    void deleteById() {
        // Arrange
        String wishListName = "Birgers Liste";
        String wishListDescription = "Birgers ønsker";
        String deletedWishListName = "Arnes Liste";

        // Act
        wishListRepository.deleteById(1);
        WishList testWishListFromDatabase = wishListRepository.findById(2);
        List<WishList> testList = new ArrayList<>(wishListRepository.findAll());

        // Assert
        assertEquals(wishListName, testWishListFromDatabase.getName());
        assertEquals(wishListDescription, testWishListFromDatabase.getDescription());
        assertNotEquals(wishListName, deletedWishListName);
        assertEquals(1, testList.size());
    }

    @Test
    void update() {
        // Arrange
        String wishlistNameBeforeUpdate = "Arnes Liste";
        String wishlistDescriptionBeforeUpdate = "Ting til Arne";
        String wishListNameAfterUpdate = "Annikas Liste";
        String wishListDescriptionAfterUpdate = "Ting til Annika";
        WishList updatedWishList = new WishList(1, wishListNameAfterUpdate, wishListDescriptionAfterUpdate, 1);

        // Act
        wishListRepository.update(updatedWishList);

        // Assert
        assertNotEquals(wishlistNameBeforeUpdate, wishListRepository.findById(1).getName());
        assertNotEquals(wishlistDescriptionBeforeUpdate, wishListRepository.findById(1).getDescription());
        assertEquals(wishListNameAfterUpdate, wishListRepository.findById(1).getName());
        assertEquals(wishListDescriptionAfterUpdate, wishListRepository.findById(1).getDescription());
    }

    @Test
    void findWishListsByProfileId() {
        // Arrange
        String wishListName = "Arnes Liste";
        String wishListDescription = "Ting til Arne";
        List<WishList> desiredWishLists = new ArrayList<>();
        WishList wishListInListOfWishes = new WishList(1, wishListName, wishListDescription, 1);
        desiredWishLists.add(wishListInListOfWishes);

        // Act
        List<WishList> actualWishListsFromDatabase = new ArrayList<>(wishListRepository.findWishListsByProfileId(1));

        // Assert
        assertEquals(desiredWishLists.get(0).getName(), actualWishListsFromDatabase.get(0).getName());
        assertEquals(desiredWishLists.get(0).getDescription(), actualWishListsFromDatabase.get(0).getDescription());
    }

    @Test
    void findWishListByWishListNameAndProfileId() {
        // Arrange
        String wishList1Name = "Arnes Liste";
        String wishList1Description = "Ting til Arne";
        WishList wishList1 = new WishList(1, wishList1Name, wishList1Description, 1);

        String wishList2Name = "Birgers Liste";
        String wishList2Description = "Birgers ønsker";
        WishList wishList2 = new WishList(1, wishList2Name, wishList2Description, 2);

        // Act
        WishList actualWishListFromDatabase1 = wishListRepository.findWishListByWishListNameAndProfileId(wishList1Name, 1);
        WishList actualWishListFromDatabase2 = wishListRepository.findWishListByWishListNameAndProfileId(wishList2Name, 2);

        // Assert
        assertEquals(wishList1.getName(), actualWishListFromDatabase1.getName());
        assertEquals(wishList1.getDescription(), actualWishListFromDatabase1.getDescription());
        assertEquals(wishList2.getName(), actualWishListFromDatabase2.getName());
        assertEquals(wishList2.getDescription(), actualWishListFromDatabase2.getDescription());
    }
}