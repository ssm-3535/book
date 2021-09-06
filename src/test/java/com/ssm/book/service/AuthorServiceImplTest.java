package com.ssm.book.service;

import com.ssm.book.api.v1.mapper.AuthorMapper;
import com.ssm.book.command.AuthorCommand;
import com.ssm.book.converter.*;
import com.ssm.book.domain.Author;
import com.ssm.book.domain.Book;
import com.ssm.book.repositories.AuthorRepository;
import com.ssm.book.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthorServiceImplTest {
    AuthorServiceImpl authorService;

    private final AuthorToAuthorCommand authorToAuthorCommand;
    private final BookToBookCommand bookToBookCommand;

    @Mock
    AuthorRepository authorRepository;

    @Mock
    BookRepository bookRepository;

    public AuthorServiceImplTest(){
        this.authorToAuthorCommand = new AuthorToAuthorCommand();
        this.bookToBookCommand = new BookToBookCommand(new PublisherToPublisherCommand(), new AuthorToAuthorCommand(), new CategoryToCategoryCommand(), new ShopToShopCommand(new QuantityToQuantityCommand()));
    }

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        authorService = new AuthorServiceImpl(authorRepository, authorToAuthorCommand, bookRepository, bookToBookCommand, AuthorMapper.INSTANCE);
    }

    @Test
    void testGetAuthorList() throws Exception{
        //given

        Author author1 = new Author();
        Author author2 = new Author();
        Author author3 = new Author();

        //when
        when(authorRepository.findAll()).thenReturn(Arrays.asList(author1,author2,author3));

        //then
        Set<AuthorCommand> authorCommandSet = authorService.getAuthorList();

        assertEquals(3, authorCommandSet.size());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void testSaveOrUpdateAuthor(){
        //given
        Book book = new Book();
        book.setId(1L);

        AuthorCommand authorCommand = new AuthorCommand();
        authorCommand.setName("ssm");
        authorCommand.setPhone("09-98543324455");
        authorCommand.setAddress("Yangon");

        Author author = new Author();
        author.setName("SSM");
        author.setPhone("09-1234567");
        author.setAddress("Nyaung U");
        author.setBook(book);

        Author savedAuthor = new Author();
        savedAuthor.setName("SSM");
        savedAuthor.setPhone("09-1234567");
        savedAuthor.setAddress("Nyaung U");
        savedAuthor.setBook(book);

        //when
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        //then
        authorService.saveOrUpdateAuthor(String.valueOf(1), authorCommand);
        verify(authorRepository, times(1)).findById(anyLong());
        verify(authorRepository, times(1)).save(any(Author.class));
        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetAuthorCommandByBookId(){
        //give
        Author author = new Author();
        author.setId(1L);
        author.setName("SSM");

        Book book = new Book();
        book.setId(1L);
        book.setAuthor(author);

        //when
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        //then
        AuthorCommand authorCommand = authorService.getAuthorCommandByBookId(String.valueOf(1));
        assertEquals(1L, authorCommand.getId());
        verify(bookRepository, times(1)).findById(anyLong());
    }
}