package com.Atlavik.shoppingcart.service.impl;

import com.Atlavik.shoppingcart.model.Product;
import com.Atlavik.shoppingcart.model.ShoppingCart;
import com.Atlavik.shoppingcart.repository.ProductRep;
import com.Atlavik.shoppingcart.repository.ShoppingCartRep;
import com.Atlavik.shoppingcart.service.ProductService;
import com.Atlavik.shoppingcart.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Simin
 * @created 11/05/2021 - 7:58 AM
 */
@Service

public class ShoppingCartServiceImpl implements ShoppingCartService {


    @Autowired
    private ShoppingCartRep cartRep;
    @Autowired
    private ProductService productService;

    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart) {
        return cartRep.save(shoppingCart);
    }

    public ShoppingCart getShoppingCart(Long id) {
        ShoppingCart cart = new ShoppingCart();
        Optional<ShoppingCart> shoppingCart = cartRep.findById(id);
        if (shoppingCart.isPresent()) {
            cart = shoppingCart.get();
        }
        return cart;
    }

    public ShoppingCart updateShoppingCart(Long id, List<Product> product) {
        ShoppingCart cart = getShoppingCart(id);
        cart.setProducts(product);
        return cartRep.save(cart);
    }

    public List<ShoppingCart> getAll() {
        return cartRep.findAll();
    }

    public void deleteShoppingCart(Long id) {
        cartRep.deleteById(id);
    }

    @Override
    public Product getShoppingCartProducts(Long id, Long productId) {
        List<Product> Products = getShoppingCart(id).getProducts()
                .stream()
                .filter(x -> x.getId() == productId)
                .collect(Collectors.toList());
        return Products.get(0);
    }

    @Override
    public ShoppingCart deleteProductFromShoppingCart(Long cartId, Long productId) {
        ShoppingCart cart = getShoppingCart(cartId);
        cart.getProducts().removeIf(x -> x.getId() == productId);
        return cartRep.save(cart);
    }

}
