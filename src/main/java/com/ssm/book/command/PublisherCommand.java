package com.ssm.book.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PublisherCommand {
    private Long id;

    private String name;
    private String phone;
    private String address;
}
