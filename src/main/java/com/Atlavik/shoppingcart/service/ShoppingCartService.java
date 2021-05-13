package com.Atlavik.shoppingcart.service;

import com.Atlavik.shoppingcart.model.Product;
import com.Atlavik.shoppingcart.model.ShoppingCart;

import java.util.List;

/**
 * @author Simin
 * @created 11/05/2021 - 5:43 PM
 */
public interface ShoppingCartService {
    ShoppingCart createShoppingCart(ShoppingCart shoppingCart);

    ShoppingCart getShoppingCart(Long id);

    ShoppingCart updateShoppingCart(Long id, List<Product> product);

    List<ShoppingCart> getAll();

    void deleteShoppingCart(Long id);


    Product getShoppingCartProducts(Long id, Long productId);

    ShoppingCart deleteProductFromShoppingCart(Long cartId, Long productId);
}
