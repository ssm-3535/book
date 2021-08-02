package com.ssm.book.converter;

import com.ssm.book.command.BookCommand;
import com.ssm.book.domain.Book;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class BookToBookCommand implements Converter<Book, BookCommand> {
    private PublisherToPublisherCommand publisherToPublisherCommand;
    private AuthorToAuthorCommand authorToAuthorCommand;
    private CategoryToCategoryCommand categoryToCategoryCommand;
    private ShopToShopCommand shopToShopCommand;

    public BookToBookCommand(PublisherToPublisherCommand publisherToPublisherCommand, AuthorToAuthorCommand authorToAuthorCommand,
                             CategoryToCategoryCommand categoryCommand, ShopToShopCommand shopCommand) {
        this.publisherToPublisherCommand = publisherToPublisherCommand;
        this.authorToAuthorCommand = authorToAuthorCommand;
        this.categoryToCategoryCommand = categoryCommand;
        this.shopToShopCommand = shopCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public BookCommand convert(Book source) {
        if(source == null)
            return null;
        final BookCommand bookCommand = new BookCommand();
        bookCommand.setId(source.getId());
        bookCommand.setTitle(source.getTitle());
        bookCommand.setYear(source.getYear());
        bookCommand.setPrice(source.getPrice());
        bookCommand.setPublisher(publisherToPublisherCommand.convert(source.getPublisher()));
        bookCommand.setAuthor(authorToAuthorCommand.convert(source.getAuthor()));
        bookCommand.setCategory(categoryToCategoryCommand.convert(source.getCategory()));
        if(source.getShops() != null && source.getShops().size() > 0){
            source.getShops().forEach(shop -> bookCommand.getShops().add(shopToShopCommand.convert(shop)));
        }
        return bookCommand;
    }
}
