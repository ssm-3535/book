package com.ssm.book.controller;

import com.ssm.book.api.v1.dto.BookDTO;
import com.ssm.book.api.v1.dto.BookDTOList;
import com.ssm.book.api.v1.dto.ResponseDTO;
import com.ssm.book.service.BookService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(BookResource.BOOK_URL)
public class BookResource {
    public static final String BOOK_URL = "/api/v1/books";
    private final BookService bookService;

    public BookResource(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public BookDTOList getBookList(){
        return new BookDTOList(bookService.getAllBookDTOList());
    }

    @PostMapping
    public ResponseDTO registerBookDTO(@RequestBody BookDTO bookDTO){
        return bookService.saveBookDTO(bookDTO);
    }

    @PutMapping("{id}")
    public BookDTO putBookDTO(@PathVariable Long id,@RequestBody BookDTO bookDTO){
        return bookService.putBookDTO(id, bookDTO);
    }

    @PatchMapping("{id}")
    public BookDTO patchBookDTO(@PathVariable Long id, @RequestBody BookDTO bookDTO){
        return bookService.patchBookDTO(id, bookDTO);
    }

    @DeleteMapping("{id}")
    public void deleteBookDTO(@PathVariable Long id){
        bookService.deleteBookDTOById(id);
    }
}
