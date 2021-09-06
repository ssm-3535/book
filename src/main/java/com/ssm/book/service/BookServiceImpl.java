package com.ssm.book.service;

import com.ssm.book.api.v1.dto.BookDTO;
import com.ssm.book.api.v1.dto.CategoryDTO;
import com.ssm.book.api.v1.dto.ResponseDTO;
import com.ssm.book.api.v1.mapper.*;
import com.ssm.book.command.*;
import com.ssm.book.converter.*;
import com.ssm.book.domain.Book;
import com.ssm.book.domain.Category;
import com.ssm.book.exception.NotFoundException;
import com.ssm.book.repositories.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService{
    private BookRepository bookRepository;
    private BookToBookCommand bookToBookCommand;
    private BookCommandToBook bookCommandToBook;
    private CategoryRepository categoryRepository;
    private CategoryToCategoryCommand categoryToCategoryCommand;
    private final BookMapper bookMapper;
    private final CategoryMapper categoryMapper;
    private final AuthorMapper authorMapper;
    private final PublisherMapper publisherMapper;
    private final ShopMapper shopMapper;

    public BookServiceImpl(BookRepository bookRepository, BookToBookCommand bookToBookCommand,
                           BookCommandToBook bookCommandToBook, CategoryRepository categoryRepository,
                           CategoryToCategoryCommand categoryToCategoryCommand, BookMapper bookMapper, CategoryMapper categoryMapper, AuthorMapper authorMapper, PublisherMapper publisherMapper, ShopMapper shopMapper) {
        this.bookRepository = bookRepository;
        this.bookToBookCommand = bookToBookCommand;
        this.bookCommandToBook = bookCommandToBook;
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.bookMapper = bookMapper;
        this.categoryMapper = categoryMapper;
        this.authorMapper = authorMapper;
        this.publisherMapper = publisherMapper;
        this.shopMapper = shopMapper;
    }

    @Override
    public Set<Book> getAllBooks() {
        Set<Book> bookSet = new HashSet<>();
        bookRepository.findAll().iterator().forEachRemaining(bookSet::add);
        return bookSet;
    }

    @Override
    public Book getBookById(String id) {
        Optional<Book> bookOptional = bookRepository.findById(Long.valueOf(id));
        if(!bookOptional.isPresent()){
            throw new NotFoundException("Book Not Found!!");
        }
        return bookOptional.get();
    }

    @Override
    public BookCommand getBookCommandById(String id) {
        Optional<Book> bookOptional = bookRepository.findById(Long.valueOf(id));
        if(!bookOptional.isPresent()){
            throw new RuntimeException("Not find Book");
        }
        return bookToBookCommand.convert(bookOptional.get());
    }

    @Override
    public BookCommand saveBookCommand(BookCommand bookCommand) {
        Book book = bookCommandToBook.convert(bookCommand);
        Book saveBook = bookRepository.save(book);
        return bookToBookCommand.convert(saveBook);
    }

    @Override
    public Set<CategoryCommand> getCategoryList() {
        return StreamSupport.stream(categoryRepository.findAll()
                .spliterator(), false)
                .map(categoryToCategoryCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteBookCommandById(String id) {
        bookRepository.deleteById(Long.valueOf(id));
    }

    @Override
    public List<BookDTO> getAllBookDTOList() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .map(bookMapper::bookToBookDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> getAllCategoryDTOList() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseDTO saveBookDTO(BookDTO bookDTO) {
        Book book = bookRepository.save(bookMapper.BookDTOToBook(bookDTO));
        if(book != null){
            return new ResponseDTO(true, "Successfully created!");
        }else {
            return new ResponseDTO(false, "Fail to create!");
        }
    }

    @Override
    public ResponseDTO saveCategoryDTO(CategoryDTO categoryDTO) {
        Category category = categoryRepository.save(categoryMapper.categoryDTOToCategory(categoryDTO));
        if(category != null){
            return new ResponseDTO(true, "Successfully created!");
        }else {
            return new ResponseDTO(false, "Fail to create!");
        }
    }

    @Override
    public void deleteBookDTOById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDTO putBookDTO(Long id, BookDTO bookDTO) {
        Book book = bookMapper.BookDTOToBook(bookDTO);
        book.setId(id);
        Book savedBook = bookRepository.save(book);
        return bookMapper.bookToBookDTO(savedBook);
    }

    @Override
    public BookDTO patchBookDTO(Long id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id).get();
        BookDTO savedBookDTO = null;
        if(book != null){
            if(bookDTO.getTitle() != null){
                book.setTitle(bookDTO.getTitle());
            }
            if(bookDTO.getYear() != null){
                book.setYear(bookDTO.getYear());
            }
            if(bookDTO.getPrice() != null){
                book.setPrice(bookDTO.getPrice());
            }
            if(bookDTO.getAuthor().getName() != null || bookDTO.getAuthor().getPhone() != null || bookDTO.getAuthor().getAddress() != null){
                book.setAuthor(authorMapper.authorDTOToAuthor(bookDTO.getAuthor()));
            }
            if(bookDTO.getPublisher().getName() != null || bookDTO.getPublisher().getPhone() != null || bookDTO.getAuthor().getAddress() != null){
                book.setPublisher(publisherMapper.publisherDTOToPublisher(bookDTO.getPublisher()));
            }
            if(bookDTO.getCategory().getTitle() != null){
                book.setCategory(categoryMapper.categoryDTOToCategory(bookDTO.getCategory()));
            }
            //TODO add shops
            Book savedBook = bookRepository.save(book);
            savedBookDTO = bookMapper.bookToBookDTO(savedBook);
        }else {
            throw new RuntimeException("Book not found!!");
        }
        return savedBookDTO;
    }

    @Override
    public CategoryDTO putCategoryDTO(Long id, CategoryDTO categoryDTO) {
        Category category = categoryMapper.categoryDTOToCategory(categoryDTO);
        category.setId(id);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryDTO(savedCategory);
    }

    @Override
    public CategoryDTO patchCategoryDTO(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id).get();
        if(category != null){
            if(categoryDTO.getTitle() != null){
                category.setTitle(categoryDTO.getTitle());
            }
        }
        return categoryMapper.categoryToCategoryDTO(category);
    }
}
