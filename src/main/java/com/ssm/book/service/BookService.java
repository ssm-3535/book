package com.ssm.book.service;

import com.ssm.book.command.*;
import com.ssm.book.domain.Book;
import com.ssm.book.domain.Shop;

import java.util.Set;

public interface BookService {
    Set<Book> getAllBooks();

    Book getBookById(String id);

    BookCommand getBookCommandById(String id);

    BookCommand saveBookCommand(BookCommand bookCommand);

    Set<CategoryCommand> getCategoryList();

    void deleteBookCommandById(String id);

}
