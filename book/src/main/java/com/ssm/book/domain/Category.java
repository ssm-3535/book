package com.ssm.book.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"book"})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String amount;

    @ManyToOne
    private Book book;

    @OneToOne(fetch = FetchType.EAGER)
    private Type type;

    public Category() {
        super();
    }

    public Category(String title, String amount, Type type) {
        super();
        this.title = title;
        this.amount = amount;
        this.type = type;
    }
}
