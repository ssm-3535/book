package com.ssm.book.controller;

import com.ssm.book.command.AuthorCommand;
import com.ssm.book.command.BookCommand;
import com.ssm.book.command.CategoryCommand;
import com.ssm.book.command.PublisherCommand;
import com.ssm.book.domain.Book;
import com.ssm.book.service.AuthorService;
import com.ssm.book.service.BookService;
import com.ssm.book.service.PublisherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class BookControllerTest {
    @Mock
    BookService bookService;
    @Mock
    PublisherService publisherService;
    @Mock
    AuthorService authorService;

    BookController bookController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        bookController = new BookController(bookService, publisherService, authorService);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void testShowBook() throws Exception{
        //given
        Book book = new Book();

        //when
        when(bookService.getBookById(anyString())).thenReturn(book);

        //then
        mockMvc.perform(get("/book/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/show"))
                .andExpect(model().attributeExists("book"));
    }

    @Test
    void testNewBook() throws Exception{
        //given
        PublisherCommand publisherCommand1 = new PublisherCommand();
        publisherCommand1.setId(1L);
        publisherCommand1.setName("p1");
        publisherCommand1.setPhone("09-xxxxxxxxxxx");
        publisherCommand1.setAddress("Tyokyo");

        PublisherCommand publisherCommand2 = new PublisherCommand();
        publisherCommand2.setId(2L);
        publisherCommand2.setName("p1");
        publisherCommand2.setPhone("09-xxxxxxxxxxx");
        publisherCommand2.setAddress("Tyokyo");

        AuthorCommand authorCommand1 = new AuthorCommand();
        authorCommand1.setId(1L);
        authorCommand1.setName("a1");
        authorCommand1.setPhone("09-33333333");
        authorCommand1.setAddress("japan");

        AuthorCommand authorCommand2 = new AuthorCommand();
        authorCommand2.setId(2L);
        authorCommand2.setName("a1");
        authorCommand2.setPhone("09-33333333");
        authorCommand2.setAddress("japan");

        CategoryCommand categoryCommand1 = new CategoryCommand();
        categoryCommand1.setId(1L);
        categoryCommand1.setTitle("Drama");

        CategoryCommand categoryCommand2 = new CategoryCommand();
        categoryCommand2.setId(2L);
        categoryCommand1.setTitle("Sample");

        Set<PublisherCommand> publisherCommandSet = new HashSet<PublisherCommand>();
        publisherCommandSet.add(publisherCommand1);
        publisherCommandSet.add(publisherCommand2);

        Set<AuthorCommand> authorCommandSet = new HashSet<>();
        authorCommandSet.add(authorCommand1);
        authorCommandSet.add(authorCommand2);

        Set<CategoryCommand> categoryCommandSet = new HashSet<>();
        categoryCommandSet.add(categoryCommand1);
        categoryCommandSet.add(categoryCommand2);

        //when
        when(publisherService.getPublisherList()).thenReturn(publisherCommandSet);
        when(authorService.getAuthorList()).thenReturn(authorCommandSet);
        when(bookService.getCategoryList()).thenReturn(categoryCommandSet);

        //then
        mockMvc.perform(get("/book/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/bookform"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("publisherList"))
                .andExpect(model().attributeExists("authorList"))
                .andExpect(model().attributeExists("categoryList"));

    }

    @Test
    void testUpdateBook() throws Exception {

        //given
        PublisherCommand publisherCommand1 = new PublisherCommand();
        publisherCommand1.setId(1L);
        publisherCommand1.setName("p1");
        publisherCommand1.setPhone("09-xxxxxxxxxxx");
        publisherCommand1.setAddress("Tyokyo");

        PublisherCommand publisherCommand2 = new PublisherCommand();
        publisherCommand2.setId(2L);
        publisherCommand2.setName("p1");
        publisherCommand2.setPhone("09-xxxxxxxxxxx");
        publisherCommand2.setAddress("Tyokyo");

        AuthorCommand authorCommand1 = new AuthorCommand();
        authorCommand1.setId(1L);
        authorCommand1.setName("a1");
        authorCommand1.setPhone("09-33333333");
        authorCommand1.setAddress("japan");

        AuthorCommand authorCommand2 = new AuthorCommand();
        authorCommand2.setId(2L);
        authorCommand2.setName("a1");
        authorCommand2.setPhone("09-33333333");
        authorCommand2.setAddress("japan");

        CategoryCommand categoryCommand1 = new CategoryCommand();
        categoryCommand1.setId(1L);
        categoryCommand1.setTitle("Drama");

        CategoryCommand categoryCommand2 = new CategoryCommand();
        categoryCommand2.setId(2L);
        categoryCommand1.setTitle("Sample");

        Set<PublisherCommand> publisherCommandSet = new HashSet<PublisherCommand>();
        publisherCommandSet.add(publisherCommand1);
        publisherCommandSet.add(publisherCommand2);

        Set<AuthorCommand> authorCommandSet = new HashSet<>();
        authorCommandSet.add(authorCommand1);
        authorCommandSet.add(authorCommand2);

        Set<CategoryCommand> categoryCommandSet = new HashSet<>();
        categoryCommandSet.add(categoryCommand1);
        categoryCommandSet.add(categoryCommand2);

        //when
        when(publisherService.getPublisherList()).thenReturn(publisherCommandSet);
        when(authorService.getAuthorList()).thenReturn(authorCommandSet);
        when(bookService.getCategoryList()).thenReturn(categoryCommandSet);

        //then
        mockMvc.perform(get("/book/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/bookform"))
                .andExpect(model().attributeExists("publisherList"))
                .andExpect(model().attributeExists("authorList"))
                .andExpect(model().attributeExists("categoryList"));

        verify(publisherService,times(1)).getPublisherList();
        verify(authorService,times(1)).getAuthorList();
        verify(bookService,times(1)).getCategoryList();

    }

    @Test
    void testSaveBook() throws Exception {
        //given
        BookCommand bookCommand = new BookCommand();
        bookCommand.setId(2L);
        bookCommand.setTitle("Test");
        bookCommand.setYear("2021");

        //when
        when(bookService.saveBookCommand(any())).thenReturn(bookCommand);

        //then
        mockMvc.perform(post("/book/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","")
                .param("title","Test")
                .param("year","2021"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book/show/2"));

        verify(bookService,times(1)).saveBookCommand(any());
    }

    @Test
    void testSaveBookFail() throws Exception {
        //given
        BookCommand bookCommand = new BookCommand();
        bookCommand.setId(2L);
        bookCommand.setTitle("Test");
        bookCommand.setYear("2021");

        //when
        when(bookService.saveBookCommand(any())).thenReturn(bookCommand);

        //then
        mockMvc.perform(post("/book/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","")
                .param("title","")
                .param("year","2021"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/bookform"));

    }

    @Test
    void testDeleteBook() throws Exception {

        mockMvc.perform(get("/book/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));

        verify(bookService,times(1)).deleteBookCommandById(anyString());
    }
}