package com.ssm.book.controller;

import com.ssm.book.command.AuthorCommand;
import com.ssm.book.command.BookCommand;
import com.ssm.book.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class AuthorControllerTest {

    @Mock
    AuthorService authorService;

    AuthorController authorController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        authorController = new AuthorController(authorService);
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
    }

    @Test
    void testShowAuthorList() throws Exception{
        //given
        AuthorCommand authorCommand = new AuthorCommand();

        //when
        when(authorService.getAuthorCommandByBookId(anyString())).thenReturn(authorCommand);

        //then
        mockMvc.perform(get("/book/1/authorupdate"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/author/authorform"))
                .andExpect(model().attributeExists("author"));

        verify(authorService, times(1)).getAuthorCommandByBookId(anyString());
    }

    @Test
    void testSaveAuthor() throws Exception{
        //given
        BookCommand bookCommand = new BookCommand();
        bookCommand.setId(1L);

        //when
        when(authorService.saveOrUpdateAuthor(anyString(),any())).thenReturn(bookCommand);

        //then
        mockMvc.perform(post("/author/1/save"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book/show/1"))
                .andExpect(model().attributeExists("book"));
    }
}