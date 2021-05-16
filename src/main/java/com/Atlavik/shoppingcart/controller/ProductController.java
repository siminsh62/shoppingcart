package com.Atlavik.shoppingcart.controller;

import com.Atlavik.shoppingcart.service.ProductService;
import com.Atlavik.shoppingcart.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Simin
 * @created 11/05/2021 - 10:59 PM
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProduct(@PathVariable Long id) {
        return productService.getProductByID(id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
