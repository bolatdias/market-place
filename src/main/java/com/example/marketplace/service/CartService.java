package com.example.marketplace.service;

import com.example.marketplace.exception.ResourceNotFoundException;
import com.example.marketplace.model.Cart;
import com.example.marketplace.model.Product;
import com.example.marketplace.model.User;
import com.example.marketplace.payload.AddToCartInput;
import com.example.marketplace.payload.PurchasePayload;
import com.example.marketplace.repository.CartRepository;
import com.example.marketplace.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;


    public List<Cart> getCarts(Long userId) {
        return cartRepository.findAllByUserId(userId);
    }


    public Cart addProductToCart(User user, AddToCartInput input) {
        Cart cart = cartRepository.findAllByUserIdAndProductId(user.getId(), input.productId()).orElse(null);


        if (cart == null) {
            cart = new Cart();

            Product product = productRepository.findById(input.productId()).orElseThrow(
                    () -> new ResourceNotFoundException("Product not founded", "id", input.productId())
            );

            cart.setProduct(product);
            cart.setUser(user);
            cart.setQuantity(input.quantity());

        } else {
            int quantity = cart.getQuantity();
            quantity = quantity + input.quantity();
            cart.setQuantity(quantity);
        }


        return cartRepository.save(cart);
    }


    @Transactional
    public PurchasePayload purchaseCart(User user) {
        List<Cart> cartList = cartRepository.findAllByUserId(user.getId());

        for (Cart cart : cartList) {
            productService.decreaseStock(cart.getProduct().getId(), cart.getQuantity());
        }
        cartRepository.deleteAll(cartList);

        return new PurchasePayload("Success");
    }
}
