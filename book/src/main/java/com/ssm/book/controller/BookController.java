package com.ssm.book.controller;

import com.ssm.book.command.AuthorCommand;
import com.ssm.book.command.BookCommand;
import com.ssm.book.command.CategoryCommand;
import com.ssm.book.command.PublisherCommand;
import com.ssm.book.service.AuthorService;
import com.ssm.book.service.BookService;
import com.ssm.book.service.PublisherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {
    private BookService bookService;
    private PublisherService publisherService;
    private AuthorService authorService;

    public BookController(BookService bookService, PublisherService publisherService, AuthorService authorService) {
        this.bookService = bookService;
        this.publisherService = publisherService;
        this.authorService = authorService;
    }

    @GetMapping("/book/show/{id}")
    public String showBook(@PathVariable String id, Model model){
        System.out.println("id ====" + id);
        model.addAttribute("book",bookService.getBookById(id));
        return "book/show";
    }

    @GetMapping("/book/new")
    public String newBook(Model model){
        BookCommand bookCommand = new BookCommand();
        model.addAttribute("book", bookCommand);
        bookCommand.setCategory(new CategoryCommand());
        bookCommand.setPublisher(new PublisherCommand());
        bookCommand.setAuthor(new AuthorCommand());

        model.addAttribute("publisherList",publisherService.getPublisherList());
        model.addAttribute("authorList", authorService.getAuthorList());
        model.addAttribute("categoryList", bookService.getCategoryList());
        return "book/bookform";
    }

    @GetMapping("/book/update/{id}")
    public String updateBook(@PathVariable String id, Model model){
        model.addAttribute("book",bookService.getBookCommandById(id));
        model.addAttribute("publisherList",publisherService.getPublisherList());
        model.addAttribute("authorList", authorService.getAuthorList());
        model.addAttribute("categoryList", bookService.getCategoryList());
        return "book/bookform";
    }

    @PostMapping("/book/save")
    public String saveBook(@ModelAttribute BookCommand bookCommand, Model model){
        BookCommand bookCommand1 = bookService.saveBookCommand(bookCommand);
        return "redirect:/book/show/" + bookCommand1.getId();
    }

    @GetMapping("/book/delete/{id}")
    public String deleteBook(@PathVariable String id){
        bookService.deleteBookCommandById(id);
        return "redirect:/index";
    }

}
