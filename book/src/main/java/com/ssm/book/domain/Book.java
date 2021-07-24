package com.ssm.book.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String year;
    private String price;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    private Set<Publisher> publishers = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    private Set<Author> authors = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "book_shop", joinColumns = @JoinColumn(name = "book_id"),
    inverseJoinColumns = @JoinColumn(name = "shop_id"))
    private Set<Shop> shops = new HashSet<>();

    @ManyToOne
    private Category category;
}
