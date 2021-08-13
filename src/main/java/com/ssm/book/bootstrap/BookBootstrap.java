package com.ssm.book.bootstrap;

import com.ssm.book.domain.*;
import com.ssm.book.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by pyaesoneaung on 24/7/2021
 */
@Slf4j
@Component
public class BookBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private BookRepository bookRepository;
    private QuantityRepository quantityRepository;
    private CategoryRepository categoryRepository;


    public BookBootstrap(BookRepository bookRepository,
                         QuantityRepository quantityRepository,
                         CategoryRepository categoryRepository) {
        super();
        this.bookRepository = bookRepository;
        this.quantityRepository = quantityRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        // TODO Auto-generated method stub
        bookRepository.saveAll(getBooks());
        log.debug("loading bootstrap data");

    }

    private List<Book> getBooks(){

        List<Book> books = new ArrayList<>();

        Optional<Quantity> oneHundredOptional = quantityRepository.findByAmount("100");
        if(!oneHundredOptional.isPresent()){
            throw new RuntimeException("Expected Quantity Not Found");
        }

        Optional<Quantity> threeHundredOptional = quantityRepository.findByAmount("300");
        if(!threeHundredOptional.isPresent()){
            throw new RuntimeException("Expected Quantity Not Found");
        }

        Optional<Quantity> fiveHundredOptional = quantityRepository.findByAmount("500");
        if(!fiveHundredOptional.isPresent()){
            throw new RuntimeException("Expected Quantity Not Found");
        }

        Quantity amount100 = oneHundredOptional.get();
        Quantity amount300 = threeHundredOptional.get();
        Quantity amount500 = fiveHundredOptional.get();

        Optional<Category> comendyOptional = categoryRepository.findByTitle("Comendy");
        if(!comendyOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> classicOptional = categoryRepository.findByTitle("Classic");
        if(!classicOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> fantasyOptional = categoryRepository.findByTitle("Fantasy");
        if(!fantasyOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> comicOptional = categoryRepository.findByTitle("Comic");
        if(!comicOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Category comendy = comendyOptional.get();
        Category classic = classicOptional.get();
        Category fantasy = fantasyOptional.get();
        Category comic = comicOptional.get();

        Book book1 = new Book();
        book1.setTitle("Dream Come True");
        book1.setYear("2021");
        book1.setPrice("20000");

        Author book1author = new Author();
        book1author.setName("Pyae Sone Aung");
        book1author.setPhone("09-xxx-xxx-xxx");
        book1author.setAddress("Yangon");
        book1.setAuthor(book1author);

        Publisher book1publisher = new Publisher();
        book1publisher.setName("ZeeKwet");
        book1publisher.setPhone("09-xxx");
        book1publisher.setAddress("Yangon");
        book1.setPublisher(book1publisher);

        book1.addShops(new Shop("PyoneCho","09-xxx-xxx-xxx","Yangon",amount100));
        book1.addShops(new Shop("ZeeKwet","09-xxx-xxx-xxx","Yangon",amount300));
        book1.addShops(new Shop("PyaeWa","09-xxx-xxx-xxx","Yangon",amount300));
        book1.addShops(new Shop("Tokyo","09-xxx-xxx-xxx","Yangon",amount500));

        book1.setCategory(comic);

        books.add(book1);

        Book book2 = new Book();
        book2.setTitle("Happy Together");
        book2.setYear("2020");
        book2.setPrice("50000");

        Author book2author = new Author();
        book2author.setName("Sensei");
        book2author.setPhone("08-xxx-xxx-xxx");
        book2author.setAddress("Inadatsuzumi");
        book2.setAuthor(book2author);

        Publisher book2publisher = new Publisher();
        book2publisher.setName("ZeeKwet");
        book2publisher.setPhone("08-xxxxxxx");
        book2publisher.setAddress("Tokyo");
        book2.setPublisher(book2publisher);

        book2.addShops(new Shop("PyoneCho","09-xxx-xxx-xxx","Yangon",amount100));
        book2.addShops(new Shop("ZeeKwet","09-xxx-xxx-xxx","Yangon",amount100));
        book2.addShops(new Shop("Tokyo","09-xxx-xxx-xxx","Yangon",amount100));

        book2.setCategory(comendy);

        books.add(book2);

        return books;
    }
}
