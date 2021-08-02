package com.ssm.book.converter;

import com.ssm.book.command.CategoryCommand;
import com.ssm.book.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {
    @Nullable
    @Synchronized
    @Override
    public Category convert(CategoryCommand source) {
        if(source == null)
            return null;
        final Category category = new Category();
        category.setId(source.getId());
        category.setTitle(source.getTitle());
        return category;
    }



}
