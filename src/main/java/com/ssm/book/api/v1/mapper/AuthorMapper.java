package com.ssm.book.api.v1.mapper;

import com.ssm.book.api.v1.dto.AuthorDTO;
import com.ssm.book.domain.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorDTO authorToAuthorDTO(Author author);
    Author authorDTOToAuthor(AuthorDTO authorDTO);
}
