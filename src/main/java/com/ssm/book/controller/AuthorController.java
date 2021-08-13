package com.ssm.book.controller;

import com.ssm.book.command.AuthorCommand;
import com.ssm.book.command.BookCommand;
import com.ssm.book.domain.Book;
import com.ssm.book.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthorController {
    private BookService bookService;

    public AuthorController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book/{id}/authorupdate")
    public String showAuthorList(@PathVariable String id, Model model){
        model.addAttribute("author", bookService.getAuthorCommandByBookId(id));
        return "book/author/authorform";
    }

    @PostMapping("/author/{id}/save")
    public String saveAuthor(@PathVariable String id, @ModelAttribute AuthorCommand authorCommand, Model model){
        BookCommand bookCommand = bookService.saveOrUpdateAuthor(id, authorCommand);
        model.addAttribute("book", bookCommand);
        return "redirect:/book/show/" + bookCommand.getId();
    }
}
