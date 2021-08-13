package com.ssm.book.command;

import com.ssm.book.domain.Publisher;
import com.ssm.book.domain.Shop;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class BookCommand {
    private Long id;
    @NotBlank
    @Size(min = 3, max = 255)
    private String title;

    @Min(4)
    private String year;

    private String price;
    private PublisherCommand publisher;
    private AuthorCommand author;
    private Set<ShopCommand> shops = new HashSet<>();
    private CategoryCommand category;
}
