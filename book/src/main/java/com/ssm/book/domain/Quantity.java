package com.ssm.book.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Quantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String amount;

}
