package com.ssm.book.service;

import com.ssm.book.api.v1.dto.AuthorDTO;
import com.ssm.book.api.v1.dto.PublisherDTO;
import com.ssm.book.api.v1.dto.ResponseDTO;
import com.ssm.book.command.AuthorCommand;
import com.ssm.book.command.BookCommand;

import java.util.List;
import java.util.Set;

public interface AuthorService {
    Set<AuthorCommand> getAuthorList();

    BookCommand saveOrUpdateAuthor(String id, AuthorCommand authorCommand);

    AuthorCommand getAuthorCommandByBookId(String id);

    List<AuthorDTO> getAuthorDTOList();

    ResponseDTO saveAuthorDTO(AuthorDTO authorDTO);

    AuthorDTO putAuthorDTO(Long id, AuthorDTO authorDTO);

    AuthorDTO patchAuthorDTO(Long id, AuthorDTO authorDTO);

    void deleteAuthorDTOById(Long id);
}
