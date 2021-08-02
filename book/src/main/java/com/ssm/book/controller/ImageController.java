package com.ssm.book.controller;

import com.ssm.book.domain.Book;
import com.ssm.book.service.BookService;
import com.ssm.book.service.ImageService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {
    private BookService bookService;
    private ImageService imageService;

    public ImageController(BookService bookService, ImageService imageService) {
        this.bookService = bookService;
        this.imageService = imageService;
    }

    @GetMapping("book/{id}/image")
    public String showImageUploadForm(@PathVariable String id, Model model){
        model.addAttribute("book", bookService.getBookCommandById(id));
        return "book/imageuploadform";
    }

    @PostMapping("book/{id}/image/save")
    public String handleSubmitImage(@PathVariable String id, @RequestParam("imagefile") MultipartFile file){
        imageService.saveImage(id, file);
        return "redirect:/book/show/" + id;
    }

    @GetMapping("book/{id}/image/show")
    public void showImage(@PathVariable String id, HttpServletResponse response){
        try {
            Book book = bookService.getBookById(id);
            byte[] image = new byte[book.getImage().length];
            int i = 0;
            for (byte b: book.getImage()) {
                image[i++] = b;
            }
            response.setContentType("image/jpeg");
            InputStream inputStream = new ByteArrayInputStream(image);
            IOUtils.copy(inputStream, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
