package com.Atlavik.shoppingcart.repository;

import com.Atlavik.shoppingcart.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Simin
 * @created 11/05/2021 - 7:56 AM
 */
@Repository
public interface ShoppingCartRep extends JpaRepository<ShoppingCart,Long> {

}
