package com.ssm.book.service;

import com.ssm.book.api.v1.mapper.PublisherMapper;
import com.ssm.book.command.BookCommand;
import com.ssm.book.command.PublisherCommand;
import com.ssm.book.converter.*;
import com.ssm.book.domain.Book;
import com.ssm.book.domain.Publisher;
import com.ssm.book.repositories.BookRepository;
import com.ssm.book.repositories.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PublisherServiceImplTest {
    PublisherServiceImpl publisherService;

    private final PublisherToPublisherCommand publisherToPublisherCommand;
    private final BookToBookCommand bookToBookCommand;

    @Mock
    PublisherRepository publisherRepository;

    @Mock
    BookRepository bookRepository;

    public PublisherServiceImplTest() {
        this.publisherToPublisherCommand = new PublisherToPublisherCommand();
        this.bookToBookCommand = new BookToBookCommand(new PublisherToPublisherCommand(), new AuthorToAuthorCommand(), new CategoryToCategoryCommand(), new ShopToShopCommand(new QuantityToQuantityCommand()));
    }

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        publisherService = new PublisherServiceImpl(publisherRepository, publisherToPublisherCommand, bookRepository, bookToBookCommand, PublisherMapper.INSTANCE);
    }

    @Test
    void getPublisherList() {
        //given
        Set<Publisher> publisherSet = new HashSet<Publisher>();

        Publisher publisher = new Publisher();
        publisher.setName("p1");
        publisher.setPhone("01-999999");
        publisher.setAddress("address");
        publisherSet.add(publisher);

        //when
        when(publisherRepository.findAll()).thenReturn(Arrays.asList(publisher));

        //then
        Set<PublisherCommand> publisherCommandSet = publisherService.getPublisherList();

        assertEquals(1, publisherCommandSet.size());
        verify(publisherRepository, times(1)).findAll();
    }

    @Test
    void findPublisherCommandById(){
        //given
        Publisher publisher = new Publisher();
        publisher.setId(1L);

        Book book = new Book();
        book.setId(1L);
        book.setPublisher(publisher);

        //when
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        //then
        PublisherCommand publisherCommand = publisherService.findPublisherCommandById(String.valueOf(1));
        assertEquals(Long.valueOf(1L), publisherCommand.getId());
        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    void updatePublisher() {
        //given
        PublisherCommand publisherCommand = new PublisherCommand();
        publisherCommand.setId(1L);
        publisherCommand.setId(1L);
        publisherCommand.setName("ssm");
        publisherCommand.setPhone("09-7654321");
        publisherCommand.setAddress("Tokyo");

        Book book = new Book();
        book.setId(1L);
        Optional<Book> bookOptional = Optional.of(book);

        Publisher publisher = new Publisher();
        publisher.setId(1L);
        publisher.setName("ssm");
        publisher.setPhone("09-7654321");
        publisher.setAddress("Tokyo");
        publisher.setBook(book);

        Publisher savedPublisher = new Publisher();
        savedPublisher.setId(1L);
        savedPublisher.setName("ssm");
        savedPublisher.setPhone("09-7654321");
        savedPublisher.setAddress("Tokyo");
        savedPublisher.setBook(book);

        //when
        when(publisherRepository.findById(anyLong())).thenReturn(Optional.of(publisher));
        when(publisherRepository.save(any(Publisher.class))).thenReturn(savedPublisher);
        when(bookRepository.findById(anyLong())).thenReturn(bookOptional);

        //then
        BookCommand bookCommand = publisherService.updatePublisher(String.valueOf(1), publisherCommand);
        assertEquals(1L, bookCommand.getId());
        verify(publisherRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).save(any(Publisher.class));
        verify(bookRepository, times(1)).findById(anyLong());
    }
}