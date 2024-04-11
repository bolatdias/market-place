package com.example.marketplace.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Formula;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "product")
    private List<Image> images;

    @OneToMany(mappedBy = "product")
    private List<ProductRating> productRatings;

    @Formula("(SELECT AVG(pr.rating) FROM product_rating pr WHERE pr.product_id = id)")
    private Float rating;
//    private double discountPercentage;

//    private double rating;
//    private String thumbnail;
//    private String[] images;
}
