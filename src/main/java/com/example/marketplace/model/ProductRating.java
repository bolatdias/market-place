package com.example.marketplace.model;


import com.example.marketplace.model.audit.DateAudit;
import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "product_id"}))
public class ProductRating  extends DateAudit {


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
