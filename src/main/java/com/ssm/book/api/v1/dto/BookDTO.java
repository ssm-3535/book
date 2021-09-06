package com.ssm.book.api.v1.dto;

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
public class BookDTO {
    private Long id;
    @NotBlank
    @Size(min = 3, max = 255)
    private String title;

    @Min(4)
    private String year;

    private String price;
    private PublisherDTO publisher;
    private AuthorDTO author;
    private Set<ShopDTO> shops = new HashSet<>();
    private CategoryDTO category;
}
