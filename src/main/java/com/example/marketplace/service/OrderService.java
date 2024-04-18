package com.example.marketplace.service;


import com.example.marketplace.model.*;
import com.example.marketplace.payload.CreateOrderInput;
import com.example.marketplace.repository.OrderProductRepository;
import com.example.marketplace.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final CartService cartService;


    @Transactional
    public Order createOrder(User user, CreateOrderInput input) {
        Order order = orderRepository.save(new Order(user));

        Cart cart = cartService.purchaseCart(user);

        List<OrderProduct> orderProductList = new ArrayList<>();
        for (CartProduct cartProduct : cart.getCartProducts()) {
            OrderProduct orderProduct = new OrderProduct();

            orderProduct.setProduct(cartProduct.getProduct());
            orderProduct.setQuantity(cartProduct.getQuantity());
            orderProduct.setOrder(order);
            orderProductList.add(orderProduct);
        }
        orderProductRepository.saveAll(orderProductList);

        order.setStatus(OrderStatus.IN_PROGRESS);
        order.setAddress(input.address());
        order.setOrderProducts(orderProductList);


        cartService.cleanCart(user.getId());
        return orderRepository.save(order);
    }

}
