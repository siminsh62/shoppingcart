package com.Atlavik.shoppingcart.service.impl;

import com.Atlavik.shoppingcart.model.Product;
import com.Atlavik.shoppingcart.repository.ProductRep;
import com.Atlavik.shoppingcart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Simin
 * @created 11/05/2021 - 11:46 PM
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRep productRep;
    // private final ProductRep productRep;

    public ProductServiceImpl(ProductRep productRep) {
        this.productRep = productRep;
    }

    @Override
    public Product createProduct(Product product) {
        return productRep.save(product);
    }
    @Override
    public void deleteProduct(Long id) {
       productRep.deleteById(id);
    }


    public Product getProductByID(Long id) {
        Product product = new Product();
        Optional<Product> p = productRep.findById(id);
        if (p.isPresent()) {
            product = p.get();
        }
        return product;
    }


}
