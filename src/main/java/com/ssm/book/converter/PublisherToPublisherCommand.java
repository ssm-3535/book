package com.ssm.book.converter;

import com.ssm.book.command.PublisherCommand;
import com.ssm.book.domain.Publisher;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class PublisherToPublisherCommand implements Converter<Publisher, PublisherCommand> {
    @Synchronized
    @Nullable
    @Override
    public PublisherCommand convert(Publisher source) {
        if(source == null)
            return null;

        final PublisherCommand publisherCommand = new PublisherCommand();
        publisherCommand.setId(source.getId());
        publisherCommand.setName(source.getName());
        publisherCommand.setPhone(source.getPhone());
        publisherCommand.setAddress(source.getAddress());
        return publisherCommand;
    }
}
