package com.ssm.book.service;

import com.ssm.book.command.BookCommand;
import com.ssm.book.command.PublisherCommand;
import com.ssm.book.converter.BookToBookCommand;
import com.ssm.book.converter.PublisherToPublisherCommand;
import com.ssm.book.domain.Book;
import com.ssm.book.domain.Publisher;
import com.ssm.book.repositories.BookRepository;
import com.ssm.book.repositories.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PublisherServiceImpl implements PublisherService{
    private PublisherRepository publisherRepository;
    private PublisherToPublisherCommand publisherToPublisherCommand;
    private final BookRepository bookRepository;
    private final BookToBookCommand bookToBookCommand;

    public PublisherServiceImpl(PublisherRepository publisherRepository, PublisherToPublisherCommand publisherToPublisherCommand, BookRepository bookRepository, BookToBookCommand bookToBookCommand) {
        this.publisherRepository = publisherRepository;
        this.publisherToPublisherCommand = publisherToPublisherCommand;
        this.bookRepository = bookRepository;
        this.bookToBookCommand = bookToBookCommand;
    }

    @Override
    public Set<PublisherCommand> getPublisherList() {

        return StreamSupport.stream(publisherRepository.findAll()
        .spliterator(),false)
                .map(publisherToPublisherCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public PublisherCommand findPublisherCommandById(String book_id) {
        Book book = bookRepository.findById(Long.valueOf(book_id)).get();
        return publisherToPublisherCommand.convert(book.getPublisher());
    }

    @Override
    public BookCommand updatePublisher(String id, PublisherCommand publisherCommand) {
        Publisher publisher = publisherRepository.findById(Long.valueOf(id)).get();
        publisher.setName(publisherCommand.getName());
        publisher.setPhone(publisherCommand.getPhone());
        publisher.setAddress(publisherCommand.getAddress());
        Publisher savedPublisher = publisherRepository.save(publisher);
        Book book = bookRepository.findById(Long.valueOf(savedPublisher.getBook().getId())).get();
        return bookToBookCommand.convert(book);
    }
}
