package com.example.marketplace.model;

import com.example.marketplace.model.audit.DateAudit;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
public class Order extends DateAudit implements ListProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private String address;

    @Transient
    private int totalPrice = 0;

    public Order(User user) {
        this.user = user;
        totalPrice = 0;
    }


    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderProduct orderProduct : orderProducts) {
            totalPrice += orderProduct.getProduct().getPrice() * orderProduct.getQuantity();
        }
        return totalPrice;
    }
}
