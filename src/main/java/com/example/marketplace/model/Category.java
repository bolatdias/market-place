package com.example.marketplace.model;


import jakarta.persistence.*;
import lombok.Data;
import java.util.List;


@Entity
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

}
