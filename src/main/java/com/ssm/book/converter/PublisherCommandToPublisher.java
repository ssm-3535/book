package com.ssm.book.converter;

import com.ssm.book.command.PublisherCommand;
import com.ssm.book.domain.Publisher;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class PublisherCommandToPublisher implements Converter<PublisherCommand, Publisher> {
    @Synchronized
    @Nullable
    @Override
    public Publisher convert(PublisherCommand source) {
        if(source == null)
            return null;

        final Publisher publisher = new Publisher();
        publisher.setId(source.getId());
        publisher.setName(source.getName());
        publisher.setPhone(source.getPhone());
        publisher.setAddress(source.getAddress());
        return publisher;
    }
}
