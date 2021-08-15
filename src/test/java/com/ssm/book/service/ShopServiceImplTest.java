package com.ssm.book.service;

import com.ssm.book.command.QuantityCommand;
import com.ssm.book.command.ShopCommand;
import com.ssm.book.converter.QuantityCommandToQuantity;
import com.ssm.book.converter.QuantityToQuantityCommand;
import com.ssm.book.converter.ShopCommandToShop;
import com.ssm.book.converter.ShopToShopCommand;
import com.ssm.book.domain.Book;
import com.ssm.book.domain.Shop;
import com.ssm.book.repositories.BookRepository;
import com.ssm.book.repositories.QuantityRepository;
import com.ssm.book.repositories.ShopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Created by pyaesoneaung at 08/14/2021
 */
class ShopServiceImplTest {

    ShopServiceImpl shopService;

    private final ShopToShopCommand shopToShopCommand;
    private final ShopCommandToShop shopCommandToShop;

    @Mock
    BookRepository bookRepository;

    @Mock
    QuantityRepository quantityRepository;

    @Mock
    ShopRepository shopRepository;

    public ShopServiceImplTest() {
        this.shopToShopCommand = new ShopToShopCommand(new QuantityToQuantityCommand());
        this.shopCommandToShop = new ShopCommandToShop(new QuantityCommandToQuantity());
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        shopService = new ShopServiceImpl(shopToShopCommand, bookRepository, shopCommandToShop, quantityRepository, shopRepository);
    }

    @Test
    void testFindShopById() throws Exception {

        //given
        Book book = new Book();
        book.setId(1L);

        Shop shop1 = new Shop();
        shop1.setId(1L);

        Shop shop2 = new Shop();
        shop2.setId(2L);

        Shop shop3 = new Shop();
        shop2.setId(3L);

        book.addShops(shop1);
        book.addShops(shop2);
        book.addShops(shop3);

        Optional<Book> bookOptional = Optional.of(book);

        //when
        when(bookRepository.findById(anyLong())).thenReturn(bookOptional);

        //then
        ShopCommand shopCommand = shopService.findShopById(String.valueOf(1), String.valueOf(3));

        assertEquals(Long.valueOf(3L), shopCommand.getId());
        assertEquals(Long.valueOf(1L), shopCommand.getBookId());
        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    void saveShopCommand() throws Exception {
        //given
        ShopCommand shopCommand = new ShopCommand();
        shopCommand.setName("New Shop");
        shopCommand.setAddress("New Address");
        shopCommand.setPhone("New Phone");
        shopCommand.setId(1L);
        shopCommand.setBookId(1L);
        shopCommand.setQuantity(new QuantityCommand());

        Optional<Book> bookOptional = Optional.of(new Book());

        Book savedBook = new Book();
        Shop savedShop = new Shop();
        savedShop.setName("New Shop");
        savedShop.setAddress("New Address");
        savedShop.setPhone("New Phone");
        savedBook.addShops(savedShop);
        savedBook.getShops().iterator().next().setId(1L);

        //when
        when(bookRepository.findById(anyLong())).thenReturn(bookOptional);
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        //then
        ShopCommand returnShop = shopService.saveShopCommand(shopCommand);

        assertEquals(Long.valueOf(1L), returnShop.getId());
        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, times(1)).save(any(Book.class));

    }
}