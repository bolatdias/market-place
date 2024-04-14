package com.example.marketplace.service;


import com.example.marketplace.exception.AppException;
import com.example.marketplace.exception.ResourceNotFoundException;
import com.example.marketplace.model.Category;
import com.example.marketplace.model.Product;
import com.example.marketplace.model.ProductRating;
import com.example.marketplace.model.User;
import com.example.marketplace.payload.ProductRatingInput;
import com.example.marketplace.payload.SearchProductInput;
import com.example.marketplace.repository.CategoryRepository;
import com.example.marketplace.repository.ProductRatingRepository;
import com.example.marketplace.repository.ProductRepository;
import org.checkerframework.checker.units.qual.A;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ModelMapper modelMapper;

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final ProductRatingRepository productRatingRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ProductRatingRepository productRatingRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productRatingRepository = productRatingRepository;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> findCategories(List<Long> ids) {
        return categoryRepository.findAllById(ids);
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("ProductService", "id", id));
    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("ProductService", "id", id));
    }

    public ProductRating rateProduct(User user, ProductRatingInput input) {
        Product product = productRepository.findById(input.productId()).orElseThrow(
                () -> new ResourceNotFoundException("ProductService", "productId", input.productId())
        );

        ProductRating productRating = new ProductRating();
        productRating.setRating(input.rating());
        productRating.setProduct(product);
        productRating.setUser(user);
        productRating.setComment(input.comment());

        return productRatingRepository.save(productRating);
    }

    public List<Product> searchProduct(SearchProductInput input) {
        return productRepository.searchProducts(input.query());
    }

    public void decreaseStock(Long productId, int amount) {
        Product product = productRepository.findById(productId).orElseThrow();
        int stock = product.getStock();

        if (stock >= amount) {
            stock -= amount;
        } else {
            throw new AppException("Not enough stock");
        }

        product.setStock(stock);
        productRepository.save(product);
    }
}
