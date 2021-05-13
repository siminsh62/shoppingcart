package com.Atlavik.shoppingcart.controller;

import com.Atlavik.shoppingcart.service.ProductService;
import com.Atlavik.shoppingcart.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Simin
 * @created 11/05/2021 - 10:59 PM
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
   // private  ProductService productService;
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @DeleteMapping(path = "/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
