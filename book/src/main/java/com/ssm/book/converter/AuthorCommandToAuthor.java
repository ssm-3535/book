package com.ssm.book.converter;

import com.ssm.book.command.AuthorCommand;
import com.ssm.book.domain.Author;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class AuthorCommandToAuthor implements Converter<AuthorCommand, Author> {
    @Synchronized
    @Nullable
    @Override
    public Author convert(AuthorCommand source) {
        if(source == null)
            return null;

        final Author author = new Author();
        author.setId(source.getId());
        author.setName(source.getName());
        author.setPhone(source.getPhone());
        author.setAddress(source.getAddress());
        return author;
    }
}
