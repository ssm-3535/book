package com.ssm.book.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

}
