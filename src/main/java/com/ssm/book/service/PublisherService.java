package com.ssm.book.service;

import com.ssm.book.command.BookCommand;
import com.ssm.book.command.PublisherCommand;

import java.util.Set;

public interface PublisherService {
    Set<PublisherCommand> getPublisherList();

    PublisherCommand findPublisherCommandById(String book_id);

    BookCommand updatePublisher(String id, PublisherCommand publisherCommand);
}
