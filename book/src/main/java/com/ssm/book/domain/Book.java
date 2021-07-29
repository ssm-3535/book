package com.ssm.book.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String year;
    private String price;

    @OneToOne(cascade = CascadeType.ALL)
    private Publisher publisher;

    @OneToOne(cascade = CascadeType.ALL)
    private Author author;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "book")
    private Set<Shop> shops = new HashSet<>();

    public Book addShops(Shop shop){
        shop.setBook(this);
        this.shops.add(shop);
        return this;
    }

    @OneToOne(fetch = FetchType.EAGER)
    private Category category;
}
