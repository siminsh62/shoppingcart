package com.Atlavik.shoppingcart.repository;

import com.Atlavik.shoppingcart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Simin
 * @created 11/05/2021 - 7:57 AM
 */
@Repository
public interface ProductRep extends JpaRepository<Product,Long> {

}
