package com.ssm.book.service;

import com.ssm.book.domain.Book;
import com.ssm.book.repositories.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Service
public class ImageServiceImpl implements ImageService{
    private BookRepository bookRepository;

    public ImageServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void saveImage(String id, MultipartFile file) {
        try {
            Book book = bookRepository.findById(Long.valueOf(id)).get();
            Byte[] image = new Byte[file.getBytes().length];
            int i = 0;
            for (byte b : file.getBytes()) {
                image[i++] = b;
            }
            book.setImage(image);
            bookRepository.save(book);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
