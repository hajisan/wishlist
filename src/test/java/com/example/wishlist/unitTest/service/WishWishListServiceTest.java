package com.example.wishlist.unitTest.service;

import com.example.wishlist.dto.WishWishListDTO;
import com.example.wishlist.model.Wish;
import com.example.wishlist.model.WishList;
import com.example.wishlist.repository.IWishListRepository;
import com.example.wishlist.repository.IWishRepository;
import com.example.wishlist.service.WishWishListService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class WishWishListServiceTest {

    @Mock
    private IWishListRepository iWishListRepository;

    @Mock
    private IWishRepository iWishRepository;

    @InjectMocks
    private WishWishListService wishWishListService;

    @Test
    void findWishListWithWishes_returnsDTO_whenWishListExists() {

        int wishListId = 1;

        WishList wishList = new WishList(wishListId, "Christmas", "Pressy", 7);

        List<Wish> wishes =
                List.of(
                new Wish("iPhone", "iPhone 16", "https://apple.com", 1, 2, 999.95, wishListId),
                new Wish("Bog", "Kvinden i buret", "https://saxo.com", 1, 3, 59.95, wishListId)
                );

        Mockito.when(iWishListRepository.findById(wishListId)).thenReturn(wishList);
        Mockito.when(iWishRepository.findByWishListId(wishListId)).thenReturn(wishes);

        WishWishListDTO result = wishWishListService.findWishWithWishList(wishListId);

        assertEquals(wishList, result.wishList());
        assertEquals(wishes, result.wishes());
        assertEquals(2, result.wishes().size());
        assertEquals("iPhone", result.wishes().get(0).getName());
        assertEquals("https://apple.com", result.wishes().get(0).getLink());

    }

    @Test
    void findWishWithWishList_returnsDTO_withEmptyWishList() {
        int wishListId = 2;

        WishList wishList = new WishList(wishListId, "Christmas", "Pressy", 5);
        List<Wish> emptyWishes = List.of();

        Mockito.when(iWishListRepository.findById(wishListId)).thenReturn(wishList);
        Mockito.when(iWishRepository.findByWishListId(wishListId)).thenReturn(emptyWishes);

        WishWishListDTO result = wishWishListService.findWishWithWishList(wishListId);

        assertEquals(wishList, result.wishList());
        assertTrue(result.wishes().isEmpty());
    }
}