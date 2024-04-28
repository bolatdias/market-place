package com.example.marketplace.service;


import com.example.marketplace.exception.AppException;
import com.example.marketplace.exception.ResourceNotFoundException;
import com.example.marketplace.model.Category;
import com.example.marketplace.model.Product;
import com.example.marketplace.model.ProductRating;
import com.example.marketplace.model.User;
import com.example.marketplace.payload.CreateProductInput;
import com.example.marketplace.payload.ProductRatingInput;
import com.example.marketplace.payload.SearchProductInput;
import com.example.marketplace.repository.CategoryRepository;
import com.example.marketplace.repository.ProductRatingRepository;
import com.example.marketplace.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ProductService {
    private Logger logger = Logger.getLogger(ProductService.class.getName());

    @Autowired
    private ModelMapper modelMapper;

    private EntityManager entityManager;
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

    public List<Integer> getRatingsByUserIdAndProductIds(Long userId, List<Long> productIds) {
        List<Object[]> list = productRatingRepository.getRatingsByUserIdAndProductIds(userId, productIds);
        Integer[] ratings = new Integer[productIds.size()];
        Arrays.fill(ratings, 0);


        for (Object[] o : list) {
            Long productId = (Long) o[1];
            Integer rating = (Integer) o[0];
            int index = productIds.indexOf(productId); // Find the index of the productId in the original list
            ratings[index] = rating; // Assign the rating at the corresponding index in the ratings array
        }

        logger.info(String.valueOf(ratings.length));

        return Arrays.asList(ratings);
    }

    public Product createProduct(CreateProductInput input, User user) {
        Product product = new Product();

        Category category = categoryRepository.findById((long) input.getCategory()).orElseThrow(
                () -> new ResourceNotFoundException("ProductService", "id", input.getCategory())
        );

        product.setCategory(category);
        product.setCompany(user.getCompany());


        product.setTitle(input.getTitle());
        product.setDescription(input.getDescription());
        product.setPrice(input.getPrice());
        product.setStock(input.getStock());
        return productRepository.save(product);
    }
}
