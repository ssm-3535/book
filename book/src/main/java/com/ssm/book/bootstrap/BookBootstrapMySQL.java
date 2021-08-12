package com.ssm.book.bootstrap;

import com.ssm.book.domain.*;
import com.ssm.book.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@Profile("dev")
public class BookBootstrapMySQL implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final QuantityRepository quantityRepository;

    public BookBootstrapMySQL(BookRepository bookRepository,
                              QuantityRepository quantityRepository,
                              CategoryRepository categoryRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository, QuantityRepository quantityRepository1) {
        super();
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.quantityRepository = quantityRepository1;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        // TODO Auto-generated method stub
        log.debug("loading bootstrap data");
        if(categoryRepository.count() == 0){
            loadCategories();
        }
        if(authorRepository.count() == 0){
            loadAuthors();
        }
        if(publisherRepository.count() == 0){
            loadPublishers();
        }
        if(quantityRepository.count() == 0){
            loadQuatities();
        }
    }

    private void loadCategories() {
        Category category1 = new Category();
        category1.setTitle("Drama");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setTitle("Horror");
        categoryRepository.save(category2);

    }

    private void loadAuthors() {
        Author author1 = new Author();
        author1.setName("SSM");
        author1.setPhone("070-xxxxxxx");
        author1.setAddress("Nyaung Oo");
        authorRepository.save(author1);
    }

    private void loadPublishers() {
        Publisher publisher1 = new Publisher();
        publisher1.setName("San San Moe");
        publisher1.setPhone("080-xxxxxxx");
        publisher1.setAddress("Tokyo");
        publisherRepository.save(publisher1);
    }

    private void loadQuatities() {
        Quantity quantity = new Quantity();
        quantity.setAmount("100");
        quantityRepository.save(quantity);
    }
}
