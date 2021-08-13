package com.ssm.book.controller;

import com.ssm.book.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    private BookService bookService;

    public IndexController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping({"/","/book","/index"})
    public String getIndex(Model model){
        model.addAttribute("bookSet",bookService.getAllBooks());
        return "index";
    }
}
