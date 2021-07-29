package com.ssm.book.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"book"})
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String address;

    @ManyToOne
    private Book book;

    @OneToOne(fetch = FetchType.EAGER)
    private Quantity quantity;

    public Shop() {
        super();
    }

    public Shop(String name, String phone, String address,Quantity quantity) {
        super();
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.quantity = quantity;
    }
}
