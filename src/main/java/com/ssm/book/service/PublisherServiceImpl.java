package com.ssm.book.service;

import com.ssm.book.command.PublisherCommand;
import com.ssm.book.converter.PublisherToPublisherCommand;
import com.ssm.book.repositories.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PublisherServiceImpl implements PublisherService{
    private PublisherRepository publisherRepository;
    private PublisherToPublisherCommand publisherToPublisherCommand;

    public PublisherServiceImpl(PublisherRepository publisherRepository, PublisherToPublisherCommand publisherToPublisherCommand) {
        this.publisherRepository = publisherRepository;
        this.publisherToPublisherCommand = publisherToPublisherCommand;
    }

    @Override
    public Set<PublisherCommand> getPublisherList() {

        return StreamSupport.stream(publisherRepository.findAll()
        .spliterator(),false)
                .map(publisherToPublisherCommand::convert)
                .collect(Collectors.toSet());
    }
}
