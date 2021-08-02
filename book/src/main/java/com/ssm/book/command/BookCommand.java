package com.ssm.book.command;

import com.ssm.book.domain.Publisher;
import com.ssm.book.domain.Shop;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class BookCommand {
    private Long id;
    private String title;
    private String year;
    private String price;
    private PublisherCommand publisher;
    private AuthorCommand author;
    private Set<ShopCommand> shops = new HashSet<>();
    private CategoryCommand category;
}
