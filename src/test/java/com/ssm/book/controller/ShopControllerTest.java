package com.ssm.book.controller;

import com.ssm.book.command.BookCommand;
import com.ssm.book.command.QuantityCommand;
import com.ssm.book.command.ShopCommand;
import com.ssm.book.domain.Quantity;
import com.ssm.book.service.BookService;
import com.ssm.book.service.QuantityService;
import com.ssm.book.service.ShopService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by pyaesoneaung at 08/14/2021
 */
class ShopControllerTest {

    @Mock
    BookService bookService;

    @Mock
    ShopService shopService;

    @Mock
    QuantityService quantityService;

    ShopController shopController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        shopController = new ShopController(bookService,shopService,quantityService);
        mockMvc = MockMvcBuilders.standaloneSetup(shopController).build();
    }

    @Test
    void testShowShopList() throws Exception {
        //given
        BookCommand bookCommand = new BookCommand();

        //when
        when(bookService.getBookCommandById(anyString())).thenReturn(bookCommand);

        //then
        mockMvc.perform(get("/book/1/shop"))
                .andExpect(status().isOk())
                .andExpect(view().name("shop/shopList"))
                .andExpect(model().attributeExists("book"));

        verify(bookService,times(1)).getBookCommandById(anyString());
    }

    @Test
    void viewShop() throws Exception {
        //given
        ShopCommand shopCommand = new ShopCommand();

        //when
        when(shopService.findShopById(anyString(),anyString())).thenReturn(shopCommand);

        //then
        mockMvc.perform(get("/book/1/shop/view/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("shop/show"))
                .andExpect(model().attributeExists("shop"));

        verify(shopService,times(1)).findShopById(anyString(),anyString());
    }

    @Test
    void displayShopForm() throws Exception {
        //given
        ShopCommand shopCommand = new ShopCommand();
        QuantityCommand quantityCommand = new QuantityCommand();

        //when
        when(shopService.findShopById(anyString(),anyString())).thenReturn(shopCommand);
        when(quantityService.showQuantityList()).thenReturn(Set.of(quantityCommand));

        //then
        mockMvc.perform(get("/book/1/shop/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("shop/shopform"))
                .andExpect(model().attributeExists("shop","quantities"));

        verify(shopService,times(1)).findShopById(anyString(),anyString());
        verify(quantityService,times(1)).showQuantityList();

    }

    @Test
    void saveShop() throws Exception {
        //given
        ShopCommand shopCommand = new ShopCommand();
        shopCommand.setId(2L);
        shopCommand.setBookId(1L);

        //when
        when(shopService.saveShopCommand(any())).thenReturn(shopCommand);

        //then
        mockMvc.perform(post("/book/1/shop/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","")
                .param("name","New Shop"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book/1/shop/view/2"));

    }

    @Test
    void deleteShop() throws Exception {

        mockMvc.perform(get("/book/1/shop/delete/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book/1/shop"));

        verify(bookService,times(1)).deleteShop(anyString(),anyString());

    }

    @Test
    void newShop() throws Exception {
        //given
        QuantityCommand quantityCommand = new QuantityCommand();

        //when
        when(quantityService.showQuantityList()).thenReturn(Set.of(quantityCommand));

        //then
        mockMvc.perform(get("/book/1/shop/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("shop/shopform"))
                .andExpect(model().attributeExists("shop","quantities"));

        verify(quantityService,times(1)).showQuantityList();
    }
}