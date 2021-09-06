package com.ssm.book.controller;

import com.ssm.book.api.v1.dto.CategoryDTO;
import com.ssm.book.api.v1.dto.CategoryDTOList;
import com.ssm.book.api.v1.dto.ResponseDTO;
import com.ssm.book.service.BookService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CategoryResource.CATEGORY_URL)
public class CategoryResource {
    public static final String CATEGORY_URL = "/api/v1/categories";
    private final BookService bookService;

    public CategoryResource(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public CategoryDTOList getCategoryDTOList(){
        return new CategoryDTOList(bookService.getAllCategoryDTOList());
    }

    @PostMapping
    public ResponseDTO registerCategory(@RequestBody CategoryDTO categoryDTO){
        return bookService.saveCategoryDTO(categoryDTO);
    }

    @PutMapping("{id}")
    public CategoryDTO putCategoryDTO(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO){
        return bookService.putCategoryDTO(id, categoryDTO);
    }

    @PatchMapping("{id}")
    public CategoryDTO patchCategoryDTO(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO){
        return bookService.patchCategoryDTO(id, categoryDTO);
    }
}
