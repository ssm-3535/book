package com.ssm.book.api.v1.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShopDTO {
    private Long id;

    private Long bookId;
    private String name;
    private String phone;
    private String address;
    private QuantityDTO quantity;
}
