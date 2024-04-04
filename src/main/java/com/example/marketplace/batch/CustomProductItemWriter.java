package com.example.marketplace.batch;

import com.example.marketplace.model.Product;
import com.example.marketplace.payload.ProductBatch;
import com.example.marketplace.repository.CategoryRepository;
import org.checkerframework.checker.units.qual.C;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class CustomProductItemWriter implements ItemWriter<ProductBatch> {
    private final JdbcTemplate jdbcTemplate;

    private static final String CATEGORY_INSERT_QUERY = "INSERT INTO CATEGORY(title) VALUES (?)";
    private static final String PRODUCT_INSERT_QUERY = "INSERT INTO PRODUCT(id, title, description, price, category_id, brand, stock) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String CATEGORY_ID_QUERY = "SELECT c.id FROM CATEGORY c WHERE title=?";
    private static final String IMAGE_INSERT_QUERY = "INSERT INTO IMAGE(url, product_id) VALUES(?,?)";

    public CustomProductItemWriter(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void write(Chunk<? extends ProductBatch> chunk) throws Exception {
        for (ProductBatch item : chunk) {
            Long categoryId = insertCategory(item.getCategory());
            insertProduct(item, categoryId);

            for (String url : item.getImages()) {
                insertImage(url, item.getId());
            }

        }
    }

    private Long insertCategory(String categoryTitle) {
        List<Long> categoryIds = jdbcTemplate.queryForList(CATEGORY_ID_QUERY, Long.class, categoryTitle);
        if (categoryIds.isEmpty()) {
            jdbcTemplate.update(CATEGORY_INSERT_QUERY, categoryTitle);
            return jdbcTemplate.queryForObject("SELECT last_insert_id()", Long.class);
        } else {
            return categoryIds.get(0);
        }
    }

    private void insertProduct(ProductBatch product, Long categoryId) {
        jdbcTemplate.update(PRODUCT_INSERT_QUERY,
                product.getId(), product.getTitle(), product.getDescription(), product.getPrice(), categoryId, product.getBrand(), product.getStock());
    }

    private void insertImage(String url, Long product_id) {
        jdbcTemplate.update(IMAGE_INSERT_QUERY, url, product_id);
    }


}
