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
    private AuthorRepository authorRepository;
    private PublisherRepository publisherRepository;
    private ShopRepository shopRepository;
    private TypeRepository typeRepository;


    public BookBootstrap(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository, ShopRepository shopRepository, TypeRepository typeRepository) {
        super();
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.shopRepository = shopRepository;
        this.typeRepository = typeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        // TODO Auto-generated method stub
        bookRepository.saveAll(getBooks());
        log.debug("loading bootstrap data");

    }

    private List<Book> getBooks(){

        List<Book> books = new ArrayList<>();

        Optional<Type> softcopyOptional = typeRepository.findByDescription("SoftCopy");
        if(!softcopyOptional.isPresent()){
            throw new RuntimeException("Expected Type Not Found");
        }

        Optional<Type> hardcopyOptional = typeRepository.findByDescription("HardCopy");
        if(!softcopyOptional.isPresent()){
            throw new RuntimeException("Expected Type Not Found");
        }

        Type softcopy = softcopyOptional.get();
        Type hardCopy = hardcopyOptional.get();

        Optional<Shop> yangonOptional = shopRepository.findByAddress("Yangon");
        if(!yangonOptional.isPresent()){
            throw new RuntimeException("Expected Type Not Found");
        }

        Optional<Shop> mandalayOptional = shopRepository.findByAddress("Mandalay");
        if(!yangonOptional.isPresent()){
            throw new RuntimeException("Expected Type Not Found");
        }

        Optional<Shop> nptOptional = shopRepository.findByAddress("NayPyiTaw");
        if(!nptOptional.isPresent()){
            throw new RuntimeException("Expected Type Not Found");
        }

        Optional<Shop> mknOptional = shopRepository.findByAddress("MyitKyiNar");
        if(!mknOptional.isPresent()){
            throw new RuntimeException("Expected Type Not Found");
        }

        Shop yangon = yangonOptional.get();
        Shop mandalay = mandalayOptional.get();
        Shop naypyitaw = nptOptional.get();
        Shop myitkyinar = mknOptional.get();

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

        book1.getShops().add(yangon);
        book1.getShops().add(mandalay);
        book1.getShops().add(naypyitaw);

        book1.addCategory(new Category("Classics","100",softcopy));

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

        book2.getShops().add(yangon);
        book2.getShops().add(mandalay);
        book2.getShops().add(naypyitaw);
        book2.getShops().add(myitkyinar);

        book2.addCategory(new Category("Comendy","100",hardCopy));

        books.add(book2);

        return books;
    }
}
