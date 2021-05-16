package com.Atlavik.shoppingcart.service.impl;

import com.Atlavik.shoppingcart.exception.BadRequestException;
import com.Atlavik.shoppingcart.exception.ElementNotFoundException;
import com.Atlavik.shoppingcart.model.Product;
import com.Atlavik.shoppingcart.repository.ProductRep;
import com.Atlavik.shoppingcart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Simin
 * @created 11/05/2021 - 11:46 PM
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRep productRep;

    @Autowired
    public ProductServiceImpl(ProductRep productRep) {
        this.productRep = productRep;
    }


    /**
     * This method is used to add Product .
     *
     * @param product This is the object to add
     * @return Product This returns created Product.
     */
    @Override
    public Product createProduct(Product product) {
        if (product.getId() != null) {
            throw new BadRequestException("The ID must not be provided when creating a new product");
        }
        return productRep.save(product);
    }

    /**
     * This method is used to delete Product by id.
     *
     * @param id This is the parameter to delete.
     * @return void
     */
    @Override
    public void deleteProduct(Long id) {
        productRep.deleteById(id);
    }

    /**
     * This method is used to find a Product by id.
     *
     * @param id This is the parameter to get.
     * @return Product This returns Product.
     */
    @Override
    public Product getProductByID(Long id) {
        return productRep
                .findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find Product with ID provided"));
    }

    /**
     * This method is used to find all Products.
     *
     * @return List<Product> This returns Products.
     */
    @Override
    public List<Product> getProducts() {
        return productRep.findAll();
    }

}
