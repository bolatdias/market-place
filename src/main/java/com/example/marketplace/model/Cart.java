package com.example.marketplace.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Cart implements ListProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart")
    private List<CartProduct> cartProducts;

    @Transient
    private int totalPrice = 0;


    public Cart(User user) {
        this.user = user;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (CartProduct cartProduct : cartProducts) {
            totalPrice += cartProduct.getProduct().getPrice() * cartProduct.getQuantity();
        }
        return totalPrice;
    }
}
