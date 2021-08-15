package com.ssm.book.controller;

import com.ssm.book.command.AuthorCommand;
import com.ssm.book.command.BookCommand;
import com.ssm.book.command.PublisherCommand;
import com.ssm.book.service.BookService;
import com.ssm.book.service.PublisherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PublisherController {
    private PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/book/{book_id}/publisherupdate")
    public String displayPublisherForm(@PathVariable String book_id, Model model){
        model.addAttribute("publisher", publisherService.findPublisherCommandById(book_id));
        return "book/publisher/publisherform";
    }

    @PostMapping("/publisher/{id}/save")
    public String savePublisher(@PathVariable String id, @ModelAttribute PublisherCommand publisherCommand, Model model){
        BookCommand bookCommand = publisherService.updatePublisher(id, publisherCommand);
        model.addAttribute("book", bookCommand);
        return "redirect:/book/show/" + bookCommand.getId();
    }
}
