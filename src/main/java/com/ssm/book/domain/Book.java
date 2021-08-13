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

    @Lob
    private Byte[] image;

    @OneToOne(cascade = CascadeType.ALL)
    private Publisher publisher;

    public void setPublisher(Publisher publisher){
        this.publisher = publisher;
        publisher.setBook(this);
    }

    @OneToOne(cascade = CascadeType.ALL)
    private Author author;

    public void setAuthor(Author author){
        this.author = author;
        author.setBook(this);
    }

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
