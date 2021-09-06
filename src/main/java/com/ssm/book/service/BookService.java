package com.ssm.book.service;

import com.ssm.book.api.v1.dto.BookDTO;
import com.ssm.book.api.v1.dto.CategoryDTO;
import com.ssm.book.api.v1.dto.ResponseDTO;
import com.ssm.book.command.*;
import com.ssm.book.domain.Book;

import java.util.List;
import java.util.Set;

public interface BookService {
    Set<Book> getAllBooks();

    Book getBookById(String id);

    BookCommand getBookCommandById(String id);

    BookCommand saveBookCommand(BookCommand bookCommand);

    Set<CategoryCommand> getCategoryList();

    void deleteBookCommandById(String id);

    List<BookDTO> getAllBookDTOList();

    List<CategoryDTO> getAllCategoryDTOList();

    ResponseDTO saveBookDTO(BookDTO bookDTO);

    ResponseDTO saveCategoryDTO(CategoryDTO categoryDTO);

    void deleteBookDTOById(Long id);

    BookDTO putBookDTO(Long id, BookDTO bookDTO);

    BookDTO patchBookDTO(Long id, BookDTO bookDTO);

    CategoryDTO putCategoryDTO(Long id, CategoryDTO categoryDTO);

    CategoryDTO patchCategoryDTO(Long id, CategoryDTO categoryDTO);

}
