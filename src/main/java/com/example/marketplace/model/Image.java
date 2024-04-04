package com.example.marketplace.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
