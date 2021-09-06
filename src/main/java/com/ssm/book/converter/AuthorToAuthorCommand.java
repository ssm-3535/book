package com.ssm.book.converter;

import com.ssm.book.command.AuthorCommand;
import com.ssm.book.domain.Author;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class AuthorToAuthorCommand implements Converter<Author, AuthorCommand> {
    @Nullable
    @Synchronized
    @Override
    public AuthorCommand convert(Author source) {
        if(source == null)
            return null;

        final AuthorCommand authorCommand = new AuthorCommand();
        authorCommand.setId(source.getId());
        authorCommand.setName(source.getName());
        authorCommand.setPhone(source.getPhone());
        authorCommand.setAddress(source.getAddress());
        return authorCommand;
    }
}
