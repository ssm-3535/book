package com.ssm.book.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class AuthorDTOList {
    private List<AuthorDTO> authorList;
}
