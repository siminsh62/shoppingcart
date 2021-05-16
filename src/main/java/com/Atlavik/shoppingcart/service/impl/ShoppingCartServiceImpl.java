package com.Atlavik.shoppingcart.service.impl;

import com.Atlavik.shoppingcart.exception.BadRequestException;
import com.Atlavik.shoppingcart.exception.ElementNotFoundException;
import com.Atlavik.shoppingcart.model.Product;
import com.Atlavik.shoppingcart.model.ShoppingCart;
import com.Atlavik.shoppingcart.repository.ShoppingCartRep;
import com.Atlavik.shoppingcart.service.ProductService;
import com.Atlavik.shoppingcart.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Simin
 * @created 11/05/2021 - 7:58 AM
 */
@Service

public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRep cartRep;

    @Autowired
    public ShoppingCartServiceImpl(ShoppingCartRep cartRep) {
        this.cartRep = cartRep;
    }

    /**
     * This method is used to create a shoppingCart .
     *
     * @param shoppingCart This is the object to add
     * @return ShoppingCart This returns created ShoppingCart.
     */
    @Override
    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart) {
        if (shoppingCart.getId() != null) {
            throw new BadRequestException("The ID must not be provided when creating a new shoppingCart");
        }
        return cartRep.save(shoppingCart);
    }

    /**
     * This method is used to find a shoppingCart.
     *
     * @param cartId This is the parameter to get.
     * @return ShoppingCart This returns ShoppingCart.
     */
    @Override
    public ShoppingCart getShoppingCart(Long cartId) {
        return cartRep
                .findById(cartId)
                .orElseThrow(() -> new ElementNotFoundException("Could not find ShoppingCart with ID provided"));
    }

    /**
     * This method is used to find a all shoppingCart.
     *
     * @return ShoppingCart This returns all ShoppingCart.
     */
    @Override
    public List<ShoppingCart> getAll() {
        return cartRep.findAll();
    }


    /**
     * This method is used to delete a shoppingCart.
     *
     * @param cartId This is the parameter to delete.
     * @return void .
     */
    @Override
    public void deleteShoppingCart(Long cartId) {
        cartRep.deleteById(cartId);
    }

    /**
     * This method add one or many product to shoppingCart.
     * first get  a shoppingCart by id so add product to shoppingCart.
     *
     * @param cartId   This is the parameter to get shoppingCart.
     * @param products This is the parameter to add to shoppingCart.
     * @return ShoppingCart This returns ShoppingCart.
     */
    @Override
    public ShoppingCart updateShoppingCart(Long cartId, List<Product> products) {
        ShoppingCart cart = getShoppingCart(cartId);
        cart.setProducts(products);
        return cartRep.save(cart);
    }

    /**
     * This method find shoppingCart 's product .
     * first get a shoppingCart by id so get product by productId.
     *
     * @param cartId    This is the first parameter to get shoppingCart.
     * @param productId This is the second  parameter to get Product.
     * @return ShoppingCart This returns ShoppingCart.
     */
    @Override
    public Product getShoppingCartProduct(Long cartId, Long productId) {
        List<Product> Products = getShoppingCart(cartId).getProducts()
                .stream()
                .filter(x -> x.getId() == productId)
                .collect(Collectors.toList());
        return Products.get(0);
    }

    /**
     * This method find shoppingCart's products .
     * first get a shoppingCart by id so gets all products.
     *
     * @param cartId This is the parameter to get shoppingCart.
     * @return List<Product> This returns all products in shoppingCart.
     */
    @Override
    public List<Product> getShoppingCartProducts(Long cartId) {
        return getShoppingCart(cartId).getProducts();

    }

    /**
     * This method delete product from shoppingCart.
     * first get a shoppingCart by id so delete product by productId.
     *
     * @param cartId    This is the first parameter to get shoppingCart.
     * @param productId This is the second  parameter to delete Product.
     * @return ShoppingCart This returns ShoppingCart.
     */
    @Override
    public ShoppingCart deleteProductFromShoppingCart(Long cartId, Long productId) {
        ShoppingCart cart = getShoppingCart(cartId);
        cart.getProducts().removeIf(x -> x.getId() == productId);
        return cartRep.save(cart);
    }


}
