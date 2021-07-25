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

    @ManyToMany
    @JoinTable(name = "book_shop", joinColumns = @JoinColumn(name = "book_id"),
    inverseJoinColumns = @JoinColumn(name = "shop_id"))
    private Set<Shop> shops = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "book")
    private Set<Category> categories = new HashSet<Category>();

    public Book addCategory(Category category) {
        category.setBook(this);
        this.categories.add(category);
        return this;
    }
}
