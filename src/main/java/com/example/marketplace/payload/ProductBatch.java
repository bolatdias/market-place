package com.example.marketplace.payload;


import com.example.marketplace.model.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductBatch {
    private Long id;
    private String title;
    private String description;
    private int price;
    private String category;

    private String brand;
    private int stock;

//    private double discountPercentage;

//    private double rating;
//    private String thumbnail;
//    private String[] images;
}
