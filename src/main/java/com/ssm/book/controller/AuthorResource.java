package com.ssm.book.controller;

import com.ssm.book.api.v1.dto.AuthorDTO;
import com.ssm.book.api.v1.dto.AuthorDTOList;
import com.ssm.book.api.v1.dto.PublisherDTO;
import com.ssm.book.api.v1.dto.ResponseDTO;
import com.ssm.book.service.AuthorService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AuthorResource.AUTHOR_URL)
public class AuthorResource {
    public static final String AUTHOR_URL = "/api/v1/authors";
    private final AuthorService authorService;

    public AuthorResource(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ApiOperation(value = "To get all authors", notes = "example")
    @GetMapping
    public AuthorDTOList getAuthorList(){
        return new AuthorDTOList(authorService.getAuthorDTOList());
    }

    @PostMapping
    public ResponseDTO registerAuthor(@RequestBody AuthorDTO authorDTO){
        return authorService.saveAuthorDTO(authorDTO);
    }

    @PutMapping("{id}")
    public AuthorDTO putAuthorDTO(@PathVariable Long id,@RequestBody AuthorDTO authorDTO){
        return authorService.putAuthorDTO(id, authorDTO);
    }

    @PatchMapping("{id}")
    public AuthorDTO patchAuthorDTO(@PathVariable Long id, @RequestBody AuthorDTO authorDTO){
        return authorService.patchAuthorDTO(id, authorDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAuthorDTO(@PathVariable Long id){
        authorService.deleteAuthorDTOById(id);
    }
}
