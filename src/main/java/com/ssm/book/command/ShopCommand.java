package com.ssm.book.command;

import com.ssm.book.domain.Book;
import com.ssm.book.domain.Quantity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShopCommand {
    private Long id;

    private Long bookId;
    private String name;
    private String phone;
    private String address;
    private QuantityCommand quantity;
}
