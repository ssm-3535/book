package com.ssm.book.converter;

import com.ssm.book.command.BookCommand;
import com.ssm.book.domain.Book;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookCommandToBook implements Converter<BookCommand, Book> {
    private ShopCommandToShop shopCommandToShop;
    private PublisherCommandToPublisher pcToPublisher;
    private AuthorCommandToAuthor acToAuthor;
    private CategoryCommandToCategory ccToCategory;

    public BookCommandToBook(ShopCommandToShop shopCommandToShop,
                             PublisherCommandToPublisher pcToPublisher,
                             AuthorCommandToAuthor acToAuthor,
                             CategoryCommandToCategory ccToCategory) {
        this.shopCommandToShop = shopCommandToShop;
        this.pcToPublisher = pcToPublisher;
        this.acToAuthor = acToAuthor;
        this.ccToCategory = ccToCategory;
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
        book.setPublisher(pcToPublisher.convert(source.getPublisher()));
        book.setAuthor(acToAuthor.convert(source.getAuthor()));
        book.setCategory(ccToCategory.convert(source.getCategory()));
        if(source.getShops() != null && source.getShops().size() > 0){
            source.getShops().forEach(shop -> book.getShops().add(shopCommandToShop.convert(shop)));
        }
        return book;
    }
}
