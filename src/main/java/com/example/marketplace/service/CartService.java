package com.example.marketplace.service;

import com.example.marketplace.exception.AppException;
import com.example.marketplace.exception.ResourceNotFoundException;
import com.example.marketplace.model.Cart;
import com.example.marketplace.model.CartProduct;
import com.example.marketplace.model.Product;
import com.example.marketplace.model.User;
import com.example.marketplace.payload.AddToCartInput;
import com.example.marketplace.payload.ApiResponse;
import com.example.marketplace.repository.CartProductRepository;
import com.example.marketplace.repository.CartRepository;
import com.example.marketplace.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final CartProductRepository cartProductRepository;

    public Cart getCarts(Long userId) {
        return cartRepository.findAllByUserId(userId).orElse(null);
    }


    @Transactional
    public CartProduct addProductToCart(User user, AddToCartInput input) {
        Cart cart = cartRepository.findAllByUserId(user.getId())
                .orElse( cartRepository.save(new Cart(user)));


        CartProduct cartProduct = cartProductRepository.findAllByUserIdAndProductId(user.getId(), input.productId())
                .orElse(null);

        if (cartProduct == null) {
            cartProduct = new CartProduct();

            Product product = productRepository.findById(input.productId()).orElseThrow(
                    () -> new ResourceNotFoundException("Product not founded", "id", input.productId())
            );


            cartProduct.setProduct(product);
            cartProduct.setCart(cart);
            cartProduct.setQuantity(input.quantity());

        } else {
            int quantity = cartProduct.getQuantity();
            quantity = quantity + input.quantity();
            cartProduct.setQuantity(quantity);
        }


        if (cartProduct.getProduct().getStock() < input.quantity()) {
            throw new AppException("Cart quantity exceeds stock");
        }

        return cartProductRepository.save(cartProduct);
    }


    @Transactional
    public ApiResponse purchaseCart(User user) {
        Cart cart = cartRepository.findAllByUserId(user.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Cart not founded", "id", user.getId())
        );

        for (CartProduct cartProduct : cart.getCartProducts()) {
            productService.decreaseStock(cartProduct.getProduct().getId(), cartProduct.getQuantity());
        }
        cartProductRepository.deleteAll(cart.getCartProducts());
        return new ApiResponse(true, "Successfully purchase cart");
    }


    public ApiResponse cleanCart(Long userId) {
        Cart cart = cartRepository.findAllByUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("Cart not founded", "id", userId)
        );

        cartProductRepository.deleteAll(cart.getCartProducts());

        return new ApiResponse(true, "Successfully clean up cary");
    }

}
