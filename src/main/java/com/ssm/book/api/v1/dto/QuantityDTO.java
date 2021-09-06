package com.ssm.book.api.v1.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuantityDTO {
    private Long id;
    private String amount;
}
