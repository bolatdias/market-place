package com.example.marketplace.payload;


import com.example.marketplace.model.Category;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class CreateProductInput {
    private String title;
    private String description;
    private int price;
    private int category;
    private int stock;
}
