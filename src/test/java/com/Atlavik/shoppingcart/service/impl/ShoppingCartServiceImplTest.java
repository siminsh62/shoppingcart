package com.Atlavik.shoppingcart.service.impl;

import com.Atlavik.shoppingcart.exception.ElementNotFoundException;
import com.Atlavik.shoppingcart.model.Product;
import com.Atlavik.shoppingcart.model.ShoppingCart;
import com.Atlavik.shoppingcart.repository.ShoppingCartRep;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Simin
 * @created 14/05/2021 - 8:40 PM
 */
@SpringBootTest
class ShoppingCartServiceImplTest {


    @Autowired
    ShoppingCartRep shoppingCartRep;


    @Test
    void createShoppingCart() {
        //create ner cart
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCountryCode("US");
        shoppingCart.setCurrency("USD");
        shoppingCart.setCreated("2021-05-07T20:30:00");
        shoppingCart.setUpdated("2021-08-07T20:30:00");
        // Test
        ShoppingCart newCart = shoppingCartRep.save(shoppingCart);
        //verify
        assertNotNull(newCart);
        assertNotNull(newCart.getId());
        assertEquals(newCart.getCountryCode(), "US");
        assertEquals(newCart.getCurrency(), "USD");
        assertEquals(newCart.getCreated(), "2021-05-07T20:30:00");
        assertEquals(newCart.getUpdated(), "2021-08-07T20:30:00");
    }


    @Test
    void getShoppingCart() {
        assertNotNull(shoppingCartRep
                .findById(18L)
                .orElseThrow(() -> new ElementNotFoundException("Could not find CART with ID provided")));
    }

    @Test
    void getAll() {
        List<ShoppingCart> carts = shoppingCartRep.findAll();
        assertThat(carts).size().isGreaterThan(0);
    }

    @Test
    void deleteShoppingCart() {
        shoppingCartRep.deleteById(15L);
        assertEquals(shoppingCartRep.findById(15L),Optional.empty());

    }

    @Test
    void updateShoppingCart() {

        List<Product> products = new ArrayList<>();
        Product product2 = new Product();
        Product product1 = new Product();

        //find cart by Id for adding products
        ShoppingCart cart = shoppingCartRep
                .findById(18L)
                .orElseThrow(() -> new ElementNotFoundException("Could not find with ID provided"));

        //set products to cart (one or more)
        product1.setId(23L);
        product2.setId(24L);
        products.add(product1);
        products.add(product2);
        cart.setProducts(products);
        //Test
        ShoppingCart newCart=shoppingCartRep.save(cart);

        //verify
        assertNotNull(newCart);
        assertNotNull(newCart.getId());
        assertEquals(newCart.getCountryCode(), "US");
        assertEquals(newCart.getCurrency(), "USD");
        assertNotNull(newCart.getProducts());
        assertEquals(newCart.getCreated(), "2021-05-07T20:30:00");
        assertEquals(newCart.getUpdated(), "2021-08-07T20:30:00");

    }

    @Test
    void getShoppingCartProduct() {
        assertNotNull(shoppingCartRep
                .findById(18L)
                .orElseThrow(() -> new ElementNotFoundException("Could not find with ID provided")).
                        getProducts().get(0));
    }

    @Test
    void getShoppingCartProducts() {
        ShoppingCart shoppingCart = shoppingCartRep
                .findById(18L)
                .orElseThrow(() -> new ElementNotFoundException("Could not find with ID provided"));
        assertNotNull(shoppingCart.getProducts());

    }

    @Test
    void deleteProductFromShoppingCart() {
        ShoppingCart shoppingCart = shoppingCartRep
                .findById(18L)
                .orElseThrow(() -> new ElementNotFoundException("Could not find with ID provided"));
        shoppingCart.getProducts().removeIf(x -> x.getId() == 24L);
        assertNotNull(shoppingCartRep.save(shoppingCart));

    }
}