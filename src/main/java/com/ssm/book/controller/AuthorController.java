package com.ssm.book.controller;

import com.ssm.book.command.AuthorCommand;
import com.ssm.book.command.BookCommand;
import com.ssm.book.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/book/{id}/authorupdate")
    public String showAuthorList(@PathVariable String id, Model model){
        model.addAttribute("author", authorService.getAuthorCommandByBookId(id));
        return "book/author/authorform";
    }

    @PostMapping("/author/{id}/save")
    public String saveAuthor(@PathVariable String id, @ModelAttribute AuthorCommand authorCommand, Model model){
        BookCommand bookCommand = authorService.saveOrUpdateAuthor(id, authorCommand);
        model.addAttribute("book", bookCommand);
        return "redirect:/book/show/" + bookCommand.getId();
    }


}
