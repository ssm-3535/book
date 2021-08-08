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
    private ShopRepository shopRepository;
    private AuthorToAuthorCommand authorToAuthorCommand;
    private AuthorRepository authorRepository;
    private PublisherToPublisherCommand publisherToPublisherCommand;
    private PublisherRepository publisherRepository;

    public BookServiceImpl(BookRepository bookRepository, BookToBookCommand bookToBookCommand,
                           BookCommandToBook bookCommandToBook, CategoryRepository categoryRepository,
                           CategoryToCategoryCommand categoryToCategoryCommand, ShopRepository shopRepository,
                           AuthorToAuthorCommand authorToAuthorCommand,AuthorRepository authorRepository,
                           PublisherToPublisherCommand publisherToPublisherCommand, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.bookToBookCommand = bookToBookCommand;
        this.bookCommandToBook = bookCommandToBook;
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.shopRepository = shopRepository;
        this.authorToAuthorCommand = authorToAuthorCommand;
        this.authorRepository = authorRepository;
        this.publisherToPublisherCommand = publisherToPublisherCommand;
        this.publisherRepository = publisherRepository;
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

    @Override
    public void deleteShop(String book_id, String id) {
//        Book book = bookRepository.findById(Long.valueOf(book_id)).get();
//        Optional<Shop> shopOptional = book.getShops().stream().filter(shop -> shop.getId().equals(id)).findFirst();
//        if(shopOptional.isPresent()){
//            System.out.println("win lar ");
//            Shop shopToDelete = shopOptional.get();
//            shopToDelete.setBook(null);
//            book.getShops().remove(shopOptional.get());
//            bookRepository.save(book);
//        }else {
//            System.out.println("no no ======");
//        }
        shopRepository.deleteById(Long.valueOf(id));
    }

    @Override
    public BookCommand saveOrUpdateAuthor(String id, AuthorCommand authorCommand) {
        Author author = authorRepository.findById(Long.valueOf(id)).get();
        author.setName(authorCommand.getName());
        author.setPhone(authorCommand.getPhone());
        author.setAddress(authorCommand.getAddress());
        Author savedAuthor = authorRepository.save(author);

        Book book = bookRepository.findById(Long.valueOf(savedAuthor.getBook().getId())).get();
        return bookToBookCommand.convert(book);
    }

    @Override
    public AuthorCommand getAuthorCommandByBookId(String id) {
        Book book = bookRepository.findById(Long.valueOf(id)).get();
        Author author = book.getAuthor();
        return authorToAuthorCommand.convert(author);
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
