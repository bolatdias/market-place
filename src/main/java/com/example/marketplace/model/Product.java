package com.example.marketplace.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Formula;

import java.util.List;

@Data
@Entity
@NamedEntityGraph(
        name = "Product.category",
        attributeNodes = @NamedAttributeNode("category")
)
@NamedEntityGraph(
        name = "Product.images",
        attributeNodes = @NamedAttributeNode("images")
)
@NamedEntityGraph(
        name = "Product.company",
        attributeNodes = @NamedAttributeNode("company")
)
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

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    private int stock;

    @OneToMany(mappedBy = "product")
    private List<Image> images;

    @OneToMany(mappedBy = "product")
    private List<ProductRating> productRatings;

    @Formula("(SELECT AVG(pr.rating) FROM product_rating pr WHERE pr.product_id = id)")
    private Float rating;


//    private double discountPercentage;

//    private double rating;
//    private String thumbnail;
}
