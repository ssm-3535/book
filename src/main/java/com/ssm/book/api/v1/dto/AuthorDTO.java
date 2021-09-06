package com.ssm.book.api.v1.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthorDTO {
    private Long id;

    @ApiModelProperty(value = "Name of Author", required = true)
    private String name;

    @ApiModelProperty(value = "Phone of Author")
    private String phone;
    private String address;
    private String bookId;
}
