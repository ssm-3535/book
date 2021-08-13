package com.ssm.book.service;

import com.ssm.book.command.AuthorCommand;
import com.ssm.book.converter.AuthorCommandToAuthor;
import com.ssm.book.converter.AuthorToAuthorCommand;
import com.ssm.book.domain.Author;
import com.ssm.book.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService{
    private AuthorRepository authorRepository;
    private AuthorToAuthorCommand authorToAuthorCommand;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorToAuthorCommand authorToAuthorCommand) {
        this.authorRepository = authorRepository;
        this.authorToAuthorCommand = authorToAuthorCommand;
    }

    @Override
    public Set<AuthorCommand> getAuthorList() {
        return StreamSupport.stream(authorRepository.findAll()
                .spliterator(), false)
                .map(authorToAuthorCommand::convert)
                .collect(Collectors.toSet());
    }
}
