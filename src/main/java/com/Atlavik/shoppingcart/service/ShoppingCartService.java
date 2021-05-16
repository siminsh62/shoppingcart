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

    ShoppingCart getShoppingCart(Long cartId);

    ShoppingCart updateShoppingCart(Long cartId, List<Product> products);

    List<ShoppingCart> getAll();

    void deleteShoppingCart(Long cartId);

    Product getShoppingCartProduct(Long cartId, Long productId);

    List<Product> getShoppingCartProducts(Long cartId);

    ShoppingCart deleteProductFromShoppingCart(Long cartId, Long productId);
}
