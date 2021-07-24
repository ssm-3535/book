package com.ssm.book.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String amount;
    private Long typeid;

    @OneToMany
    private Set<Book> books = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Type type;
}
