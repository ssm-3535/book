package com.ssm.book.service;

import com.ssm.book.command.*;
import com.ssm.book.converter.*;
import com.ssm.book.domain.Author;
import com.ssm.book.domain.Book;
import com.ssm.book.domain.Publisher;
import com.ssm.book.domain.Shop;
import com.ssm.book.exception.NotFoundException;
import com.ssm.book.repositories.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService{
    private BookRepository bookRepository;
    private BookToBookCommand bookToBookCommand;
    private BookCommandToBook bookCommandToBook;
    private CategoryRepository categoryRepository;
    private CategoryToCategoryCommand categoryToCategoryCommand;

    public BookServiceImpl(BookRepository bookRepository, BookToBookCommand bookToBookCommand,
                           BookCommandToBook bookCommandToBook, CategoryRepository categoryRepository,
                           CategoryToCategoryCommand categoryToCategoryCommand) {
        this.bookRepository = bookRepository;
        this.bookToBookCommand = bookToBookCommand;
        this.bookCommandToBook = bookCommandToBook;
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Override
    public Set<Book> getAllBooks() {
        Set<Book> bookSet = new HashSet<>();
        bookRepository.findAll().iterator().forEachRemaining(bookSet::add);
        return bookSet;
    }

    @Override
    public Book getBookById(String id) {
        Optional<Book> bookOptional = bookRepository.findById(Long.valueOf(id));
        if(!bookOptional.isPresent()){
            throw new NotFoundException("Book Not Found!!");
        }
        return bookOptional.get();
    }

    @Override
    public BookCommand getBookCommandById(String id) {
        Optional<Book> bookOptional = bookRepository.findById(Long.valueOf(id));
        if(!bookOptional.isPresent()){
            throw new RuntimeException("Not find Book");
        }
        return bookToBookCommand.convert(bookOptional.get());
    }

    @Override
    public BookCommand saveBookCommand(BookCommand bookCommand) {
        Book book = bookCommandToBook.convert(bookCommand);
        Book saveBook = bookRepository.save(book);
        return bookToBookCommand.convert(saveBook);
    }

    @Override
    public Set<CategoryCommand> getCategoryList() {
        return StreamSupport.stream(categoryRepository.findAll()
                .spliterator(), false)
                .map(categoryToCategoryCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteBookCommandById(String id) {
        bookRepository.deleteById(Long.valueOf(id));
    }

}
