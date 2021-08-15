package com.ssm.book.service;

import com.ssm.book.command.AuthorCommand;
import com.ssm.book.command.BookCommand;
import com.ssm.book.converter.AuthorToAuthorCommand;
import com.ssm.book.converter.BookToBookCommand;
import com.ssm.book.domain.Author;
import com.ssm.book.domain.Book;
import com.ssm.book.repositories.AuthorRepository;
import com.ssm.book.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepository;
    private final AuthorToAuthorCommand authorToAuthorCommand;
    private final BookRepository bookRepository;
    private final BookToBookCommand bookToBookCommand;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorToAuthorCommand authorToAuthorCommand, BookRepository bookRepository, BookToBookCommand bookToBookCommand) {
        this.authorRepository = authorRepository;
        this.authorToAuthorCommand = authorToAuthorCommand;
        this.bookRepository = bookRepository;
        this.bookToBookCommand = bookToBookCommand;
    }

    @Override
    public Set<AuthorCommand> getAuthorList() {
        return StreamSupport.stream(authorRepository.findAll()
                .spliterator(), false)
                .map(authorToAuthorCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public BookCommand saveOrUpdateAuthor(String id, AuthorCommand authorCommand) {
        Author author = authorRepository.findById(Long.valueOf(id)).get();
        author.setName(authorCommand.getName());
        author.setPhone(authorCommand.getPhone());
        author.setAddress(authorCommand.getAddress());
        Author savedAuthor = authorRepository.save(author);

        Book book = bookRepository.findById(Long.valueOf(savedAuthor.getBook().getId())).get();
        return bookToBookCommand.convert(book);
    }

    @Override
    public AuthorCommand getAuthorCommandByBookId(String id) {
        Book book = bookRepository.findById(Long.valueOf(id)).get();
        Author author = book.getAuthor();
        return authorToAuthorCommand.convert(author);
    }
}
