package com.ssm.book.api.v1.mapper;

import com.ssm.book.api.v1.dto.BookDTO;
import com.ssm.book.domain.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDTO bookToBookDTO(Book book);

    Book BookDTOToBook(BookDTO bookDTO);
}
