package com.example.marketplace.service;

import com.example.marketplace.exception.AppException;
import com.example.marketplace.exception.ResourceNotFoundException;
import com.example.marketplace.model.Cart;
import com.example.marketplace.model.CartProduct;
import com.example.marketplace.model.Product;
import com.example.marketplace.model.User;
import com.example.marketplace.payload.AddToCartInput;
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

    public Cart getCartByUserId(Long userId) {
        return cartRepository.findCartByUserId(userId).orElse(null);
    }


    @Transactional
    public CartProduct addProductToCart(User user, AddToCartInput input) {

        Cart cart = cartRepository.findCartByUserId(user.getId())
                .orElseGet(() -> cartRepository.save(new Cart(user)));

        CartProduct cartProduct = cartProductRepository.findAllByUserIdAndProductId(user.getId(), input.productId())
                .orElse(null);

        if (cartProduct == null) {
            cartProduct = new CartProduct();

            Product product = productRepository.findById(input.productId()).orElseThrow(
                    () -> new ResourceNotFoundException("Product not found", "id", input.productId())
            );

            cartProduct.setProduct(product);
            cartProduct.setCart(cart);
            cartProduct.setQuantity(input.quantity());

            checkQuantity(input, cartProduct);
            return cartProductRepository.save(cartProduct);

        } else {
            int quantity = cartProduct.getQuantity();
            quantity += input.quantity();
            cartProduct.setQuantity(quantity);

            checkQuantity(input, cartProduct);
            return cartProductRepository.save(cartProduct);
        }
    }


    private static void checkQuantity(AddToCartInput input, CartProduct cartProduct) {
        if (cartProduct.getProduct().getStock() < input.quantity()) {
            throw new AppException("Cart quantity exceeds stock");
        }
    }


    @Transactional
    public Cart purchaseCart(User user) {
        Cart cart = cartRepository.findCartByUserId(user.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Cart not founded", "id", user.getId())
        );

        for (CartProduct cartProduct : cart.getCartProducts()) {
            productService.decreaseStock(cartProduct.getProduct().getId(), cartProduct.getQuantity());
        }
        return cart;
    }


    public void cleanCart(Long userId) {
        Cart cart = cartRepository.findCartByUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("Cart not founded", "id", userId)
        );

        try {
            cartProductRepository.deleteAll(cart.getCartProducts());
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public void cleanCart(Cart cart) {
        cartProductRepository.deleteAll(cart.getCartProducts());
    }
}
