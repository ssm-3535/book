package com.ssm.book.converter;

import com.ssm.book.command.CategoryCommand;
import com.ssm.book.domain.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {
    @Override
    public CategoryCommand convert(Category source) {
        if(source == null)
            return null;
        final CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(source.getId());
        categoryCommand.setTitle(source.getTitle());
        return categoryCommand;
    }
}
