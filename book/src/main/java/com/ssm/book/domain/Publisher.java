package com.ssm.book.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String address;

    @ManyToOne
    private Book book;
}
