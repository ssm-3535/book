package com.ssm.book.service;

import com.ssm.book.command.AuthorCommand;
import com.ssm.book.command.BookCommand;
import com.ssm.book.command.CategoryCommand;
import com.ssm.book.command.PublisherCommand;
import com.ssm.book.converter.*;
import com.ssm.book.domain.Author;
import com.ssm.book.domain.Book;
import com.ssm.book.domain.Category;
import com.ssm.book.domain.Publisher;
import com.ssm.book.repositories.AuthorRepository;
import com.ssm.book.repositories.BookRepository;
import com.ssm.book.repositories.CategoryRepository;
import com.ssm.book.repositories.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

class BookServiceImplTest {
    @Mock
    BookRepository bookRepository;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    PublisherRepository publisherRepository;
    @Mock
    AuthorRepository authorRepository;

    private final BookToBookCommand bookToBookCommand;
    private final BookCommandToBook bookCommandToBook;
    private final CategoryToCategoryCommand categoryToCategoryCommand;

    BookServiceImpl bookService;

    public BookServiceImplTest() {
        this.bookToBookCommand = new BookToBookCommand(
                new PublisherToPublisherCommand(),
                new AuthorToAuthorCommand(),
                new CategoryToCategoryCommand(),
                new ShopToShopCommand(new QuantityToQuantityCommand()));
        this.bookCommandToBook = new BookCommandToBook(
                new ShopCommandToShop(new QuantityCommandToQuantity()),
                new PublisherCommandToPublisher(),
                new AuthorCommandToAuthor(),
                new CategoryCommandToCategory());
        this.categoryToCategoryCommand = new CategoryToCategoryCommand();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        bookService = new BookServiceImpl(bookRepository, bookToBookCommand, bookCommandToBook, categoryRepository, categoryToCategoryCommand);
    }

    @Test
    void testGetAllBooks() {
        //give
        Book book1 = new Book();
        Book book2 = new Book();
        Book book3 = new Book();
        Set<Book> bookSet = new HashSet<>();
        bookSet.add(book1);
        bookSet.add(book2);
        bookSet.add(book3);

        //when
        when(bookRepository.findAll()).thenReturn(bookSet);

        //then
        Set<Book> receivedBook = bookService.getAllBooks();
        assertEquals(3, receivedBook.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBookById() {
        //given
        Book book = new Book();
        book.setId(1L);
        //when
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        //then
        Book returnBook = bookService.getBookById(String.valueOf(1));
        assertEquals(1L, returnBook.getId());
        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetBookCommandById() {
        //given
        Book book = new Book();
        book.setTitle("ssm");
        //when
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        //then
        BookCommand returnBookCommand = bookService.getBookCommandById(String.valueOf(1));
        assertEquals("ssm", returnBookCommand.getTitle());
        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    void testSaveBookCommand() {
        //given
        Book savedBook = new Book();
        savedBook.setId(1L);

        Publisher publisher = new Publisher();
        publisher.setId(1L);

        Author author = new Author();
        author.setId(1L);

        Category category = new Category();
        category.setId(1L);

        PublisherCommand publisherCommand = new PublisherCommand();
        publisherCommand.setId(1L);
        publisherCommand.setName("Test");
        publisherCommand.setPhone("Test");
        publisherCommand.setAddress("Test");

        AuthorCommand authorCommand = new AuthorCommand();
        authorCommand.setId(1L);
        authorCommand.setName("Test");
        authorCommand.setAddress("Test");
        authorCommand.setPhone("Test");

        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(1L);
        categoryCommand.setTitle("Test");

        BookCommand bookCommand = new BookCommand();
        bookCommand.setId(1L);
        bookCommand.setPublisher(publisherCommand);
        bookCommand.setAuthor(authorCommand);
        bookCommand.setCategory(categoryCommand);

        //when
        when(bookRepository.save(any())).thenReturn(savedBook);

        //then
        BookCommand saveBookCommand = bookService.saveBookCommand(bookCommand);
        assertEquals(1L, saveBookCommand.getId());
        verify(bookRepository, times(1)).save(any());
    }

    @Test
    void testGetCategoryList() {
        //given
        Set<Category> categorySet = new HashSet<>();
        Category category1 = new Category();
        category1.setId(1L);

        Category category2 = new Category();
        category2.setId(2L);

        categorySet.add(category1);
        categorySet.add(category2);


        //when
        when(categoryRepository.findAll()).thenReturn(categorySet);

        //then
        Set<CategoryCommand> returnCategoryCommandList = bookService.getCategoryList();
        assertEquals(2, returnCategoryCommandList.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testDeleteBookCommandById() {
        bookService.deleteBookCommandById(String.valueOf(1));
        verify(bookRepository, times(1)).deleteById(anyLong());
    }
}