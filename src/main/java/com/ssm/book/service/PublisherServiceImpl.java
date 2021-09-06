package com.ssm.book.service;

import com.ssm.book.api.v1.dto.PublisherDTO;
import com.ssm.book.api.v1.dto.ResponseDTO;
import com.ssm.book.api.v1.mapper.PublisherMapper;
import com.ssm.book.command.BookCommand;
import com.ssm.book.command.PublisherCommand;
import com.ssm.book.converter.BookToBookCommand;
import com.ssm.book.converter.PublisherToPublisherCommand;
import com.ssm.book.domain.Book;
import com.ssm.book.domain.Publisher;
import com.ssm.book.repositories.BookRepository;
import com.ssm.book.repositories.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PublisherServiceImpl implements PublisherService{
    private PublisherRepository publisherRepository;
    private PublisherToPublisherCommand publisherToPublisherCommand;
    private final BookRepository bookRepository;
    private final BookToBookCommand bookToBookCommand;
    private final PublisherMapper publisherMapper;

    public PublisherServiceImpl(PublisherRepository publisherRepository, PublisherToPublisherCommand publisherToPublisherCommand, BookRepository bookRepository, BookToBookCommand bookToBookCommand, PublisherMapper publisherMapper) {
        this.publisherRepository = publisherRepository;
        this.publisherToPublisherCommand = publisherToPublisherCommand;
        this.bookRepository = bookRepository;
        this.bookToBookCommand = bookToBookCommand;
        this.publisherMapper = publisherMapper;
    }

    @Override
    public Set<PublisherCommand> getPublisherList() {

        return StreamSupport.stream(publisherRepository.findAll()
        .spliterator(),false)
                .map(publisherToPublisherCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public PublisherCommand findPublisherCommandById(String book_id) {
        Book book = bookRepository.findById(Long.valueOf(book_id)).get();
        return publisherToPublisherCommand.convert(book.getPublisher());
    }

    @Override
    public BookCommand updatePublisher(String id, PublisherCommand publisherCommand) {
        Publisher publisher = publisherRepository.findById(Long.valueOf(id)).get();
        publisher.setName(publisherCommand.getName());
        publisher.setPhone(publisherCommand.getPhone());
        publisher.setAddress(publisherCommand.getAddress());
        Publisher savedPublisher = publisherRepository.save(publisher);
        Book book = bookRepository.findById(Long.valueOf(savedPublisher.getBook().getId())).get();
        return bookToBookCommand.convert(book);
    }

    @Override
    public List<PublisherDTO> getPublisherDTOList() {
        return StreamSupport.stream(publisherRepository.findAll().spliterator(), false)
                .map(publisherMapper::publisherToPublisherDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseDTO savePublisherDTO(PublisherDTO publisherDTO) {
        Publisher publisher = publisherRepository.save(publisherMapper.publisherDTOToPublisher(publisherDTO));
        if(publisher != null){
            return new ResponseDTO(true, "Successfully created!");
        }else {
            return new ResponseDTO(false, "Fail to create!");
        }
    }

    @Override
    public PublisherDTO putPublisherDTO(Long id, PublisherDTO publisherDTO) {
        Publisher publisher = publisherMapper.publisherDTOToPublisher(publisherDTO);
        publisher.setId(id);
        Publisher savedPublisher = publisherRepository.save(publisher);
        return publisherMapper.publisherToPublisherDTO(savedPublisher);
    }

    @Override
    public PublisherDTO patchPublisherDTO(Long id, PublisherDTO publisherDTO) {
        Publisher publisher = publisherRepository.findById(id).get();
        PublisherDTO savedPublisherDTO;
        if(publisher != null){
            if(publisherDTO.getName() != null){
                publisher.setName(publisherDTO.getName());
            }
            if(publisherDTO.getPhone() != null){
                publisher.setPhone(publisherDTO.getPhone());
            }
            if(publisherDTO.getAddress() != null){
                publisher.setAddress(publisherDTO.getAddress());
            }
            publisherRepository.save(publisher);
            savedPublisherDTO = publisherMapper.publisherToPublisherDTO(publisher);

        }else {
            throw new RuntimeException("Publisher not found!");
        }
        return savedPublisherDTO;
    }

    @Override
    public void deletePublisherDTOById(Long id) {
        publisherRepository.deleteById(id);
    }
}
