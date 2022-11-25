package com.example.demo.model;


import lombok.Getter;
import lombok.Setter;
import org.codehaus.groovy.control.messages.Message;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "supplier")
@Getter
@Setter
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_supplier", unique = true)
    private Long id;

    @NotEmpty(message="Supplier name can´t be void")
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String name;

    @NotEmpty
    @Column(length = 100, nullable = false)
    private String address;

    @NotEmpty
    @Min(value = 1)
    @Column(length = 20, nullable = false)
    private String phone;

    @NotEmpty
    @Column(length = 50, nullable = false, name = "contact_name")
    private String contactName;



}
