package com.ssm.book.service;

import com.ssm.book.command.PublisherCommand;

import java.util.Set;

public interface PublisherService {
    Set<PublisherCommand> getPublisherList();
}
