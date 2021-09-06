package com.ssm.book.service;

import com.ssm.book.api.v1.dto.AuthorDTO;
import com.ssm.book.api.v1.dto.ResponseDTO;
import com.ssm.book.api.v1.mapper.AuthorMapper;
import com.ssm.book.command.AuthorCommand;
import com.ssm.book.command.BookCommand;
import com.ssm.book.converter.AuthorToAuthorCommand;
import com.ssm.book.converter.BookToBookCommand;
import com.ssm.book.domain.Author;
import com.ssm.book.domain.Book;
import com.ssm.book.repositories.AuthorRepository;
import com.ssm.book.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepository;
    private final AuthorToAuthorCommand authorToAuthorCommand;
    private final BookRepository bookRepository;
    private final BookToBookCommand bookToBookCommand;
    private final AuthorMapper authorMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorToAuthorCommand authorToAuthorCommand, BookRepository bookRepository, BookToBookCommand bookToBookCommand, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorToAuthorCommand = authorToAuthorCommand;
        this.bookRepository = bookRepository;
        this.bookToBookCommand = bookToBookCommand;
        this.authorMapper = authorMapper;
    }

    @Override
    public Set<AuthorCommand> getAuthorList() {
        return StreamSupport.stream(authorRepository.findAll()
                .spliterator(), false)
                .map(authorToAuthorCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public BookCommand saveOrUpdateAuthor(String id, AuthorCommand authorCommand) {
        Author author = authorRepository.findById(Long.valueOf(id)).get();
        author.setName(authorCommand.getName());
        author.setPhone(authorCommand.getPhone());
        author.setAddress(authorCommand.getAddress());
        Author savedAuthor = authorRepository.save(author);

        Book book = bookRepository.findById(Long.valueOf(savedAuthor.getBook().getId())).get();
        return bookToBookCommand.convert(book);
    }

    @Override
    public AuthorCommand getAuthorCommandByBookId(String id) {
        Book book = bookRepository.findById(Long.valueOf(id)).get();
        Author author = book.getAuthor();
        return authorToAuthorCommand.convert(author);
    }

    @Override
    public List<AuthorDTO> getAuthorDTOList() {
        return StreamSupport.stream(authorRepository.findAll().spliterator(), false)
                .map(authorMapper::authorToAuthorDTO).collect(Collectors.toList());
    }

    @Override
    public ResponseDTO saveAuthorDTO(AuthorDTO authorDTO) {
        Author savedAuthor = authorRepository.save(authorMapper.authorDTOToAuthor(authorDTO));
        if(savedAuthor != null){
            return new ResponseDTO(true, "Successfully created!");
        }else {
            return new ResponseDTO(false, "Fail to create author!");
        }
    }

    @Override
    public AuthorDTO putAuthorDTO(Long id, AuthorDTO authorDTO) {
        Author author = authorMapper.authorDTOToAuthor(authorDTO);
        author.setId(id);
        Author savedAuthor = authorRepository.save(author);
        return authorMapper.authorToAuthorDTO(savedAuthor);
    }

    @Override
    public AuthorDTO patchAuthorDTO(Long id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id).get();
        AuthorDTO savedAuthorDTO = null;
        if(author != null){
            if(authorDTO.getName() != null){
                author.setName(authorDTO.getName());
            }
            if(authorDTO.getPhone() != null){
                author.setPhone(authorDTO.getPhone());
            }
            if(authorDTO.getAddress() != null){
                author.setAddress(authorDTO.getAddress());
            }
            Author savedAuthor = authorRepository.save(author);
            savedAuthorDTO = authorMapper.authorToAuthorDTO(savedAuthor);
        }else {
            throw new RuntimeException("Author not found!!");
        }
        return savedAuthorDTO;
    }

    @Override
    public void deleteAuthorDTOById(Long id) {
        authorRepository.deleteById(id);
    }
}
