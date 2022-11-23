package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_product", unique = true)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    private double price;

    @Column(length = 300, nullable = false)
    private String description;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

}
