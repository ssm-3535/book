package com.ssm.book.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = {"book"})
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String address;

    @OneToOne
    private Book book;

    public Publisher(){
        super();
    }

    public Publisher(String name, String phone, String address) {
        super();
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
}
