package com.Atlavik.shoppingcart.service;

import com.Atlavik.shoppingcart.model.Product;

import java.util.List;

/**
 * @author Simin
 * @created 11/05/2021 - 11:37 PM
 */
public interface ProductService {
    Product createProduct(Product product);

    void deleteProduct(Long id);

    Product getProductByID(Long id);

    List<Product> getProducts();
}
