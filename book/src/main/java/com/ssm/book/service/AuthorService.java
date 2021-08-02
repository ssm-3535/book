package com.ssm.book.service;

import com.ssm.book.command.AuthorCommand;
import com.ssm.book.domain.Author;

import java.util.Set;

public interface AuthorService {
    Set<AuthorCommand> getAuthorList();
}
