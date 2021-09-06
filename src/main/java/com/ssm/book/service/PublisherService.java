package com.ssm.book.service;

import com.ssm.book.api.v1.dto.PublisherDTO;
import com.ssm.book.api.v1.dto.ResponseDTO;
import com.ssm.book.command.BookCommand;
import com.ssm.book.command.PublisherCommand;

import java.util.List;
import java.util.Set;

public interface PublisherService {
    Set<PublisherCommand> getPublisherList();

    PublisherCommand findPublisherCommandById(String book_id);

    BookCommand updatePublisher(String id, PublisherCommand publisherCommand);

    List<PublisherDTO> getPublisherDTOList();

    ResponseDTO savePublisherDTO(PublisherDTO publisherDTO);

    PublisherDTO putPublisherDTO(Long id, PublisherDTO publisherDTO);

    PublisherDTO patchPublisherDTO(Long id, PublisherDTO publisherDTO);

    void deletePublisherDTOById(Long id);
}
