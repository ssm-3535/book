package com.ssm.book.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuantityCommand {
    private Long id;
    private String amount;
}
