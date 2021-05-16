package com.Atlavik.shoppingcart.service.impl;

import com.Atlavik.shoppingcart.exception.ElementNotFoundException;
import com.Atlavik.shoppingcart.model.Product;
import com.Atlavik.shoppingcart.model.ShoppingCart;
import com.Atlavik.shoppingcart.repository.ProductRep;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Simin
 * @created 15/05/2021 - 1:03 AM
 */
@SpringBootTest
class ProductServiceImplTest {
    @Autowired
    ProductRep productRep;

    @Test
    void createProduct() {

        //creat new Product
        Product product = new Product();
        product.setProductId("1135621-5605-4d75-8317-db282c498c7f");
        product.setDescription("Apple iphone 12");
        product.setCategory("ELECTRONICS");
        product.setPrice(BigDecimal.valueOf(7325.05));
        product.setCreated("2021-05-07T20:30:00");
        product.setUpdated("2021-08-07T20:30:00");

        //Test
        Product newProduct = productRep.save(product);

        //verify
        assertNotNull(newProduct);
        assertNotNull(newProduct.getId());
        assertEquals(newProduct.getProductId(), "1135621-5605-4d75-8317-db282c498c7f");
        assertEquals(newProduct.getDescription(), "Apple iphone 12");
        assertEquals(newProduct.getCategory(), "ELECTRONICS");
        assertEquals(newProduct.getPrice(), BigDecimal.valueOf(7325.05));
        assertEquals(newProduct.getCreated(), "2021-05-07T20:30:00");
        assertEquals(newProduct.getUpdated(), "2021-08-07T20:30:00");
    }

    @Test
    void deleteProduct() {
        productRep.deleteById(6L);
        assertEquals(productRep.findById(7L),Optional.empty());
    }

    @Test
    void getProductByID() {
        assertNotNull(productRep
                .findById(16L)
                .orElseThrow(() -> new ElementNotFoundException("Could not find Product with ID provided")));
    }

    @Test
    void getProducts() {
        assertThat(productRep.findAll()).size().isGreaterThan(0);
    }
}