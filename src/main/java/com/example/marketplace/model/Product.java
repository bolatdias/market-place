package com.example.marketplace.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    private String description;
    private int price;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private String brand;
    private int stock;

//    private double discountPercentage;

//    private double rating;
//    private String thumbnail;
//    private String[] images;
}
