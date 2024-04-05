package com.example.marketplace.model;


import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Entity
@Data
public class ProductRating {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;


    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;


    @NotNull
    int rating;

    @NotNull
    String comment;
}
