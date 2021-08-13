package com.ssm.book.converter;

import com.ssm.book.command.BookCommand;
import com.ssm.book.domain.Book;
import com.ssm.book.repositories.AuthorRepository;
import com.ssm.book.repositories.CategoryRepository;
import com.ssm.book.repositories.PublisherRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookCommandToBook implements Converter<BookCommand, Book> {
    private ShopCommandToShop shopCommandToShop;
    private PublisherRepository publisherRepository;
    private AuthorRepository authorRepository;
    private CategoryRepository categoryRepository;

    public BookCommandToBook(ShopCommandToShop shopCommandToShop,
                             PublisherRepository publisherRepository, AuthorRepository authorRepository,
                             CategoryRepository categoryRepository) {
        this.shopCommandToShop = shopCommandToShop;
        this.publisherRepository = publisherRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Book convert(BookCommand source) {
        if(source == null)
            return null;
        final Book book = new Book();
        book.setId(source.getId());
        book.setTitle(source.getTitle());
        book.setYear(source.getYear());
        book.setPrice(source.getPrice());
        book.setPublisher(publisherRepository.findById(source.getPublisher().getId()).get());
        book.setAuthor(authorRepository.findById(source.getAuthor().getId()).get());
        book.setCategory(categoryRepository.findById(source.getCategory().getId()).get());
        if(source.getShops() != null && source.getShops().size() > 0){
            source.getShops().forEach(shop -> book.getShops().add(shopCommandToShop.convert(shop)));
        }
        return book;
    }
}
