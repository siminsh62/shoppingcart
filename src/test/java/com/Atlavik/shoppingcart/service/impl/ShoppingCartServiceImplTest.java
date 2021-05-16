package com.Atlavik.shoppingcart.service.impl;

import com.Atlavik.shoppingcart.exception.ElementNotFoundException;
import com.Atlavik.shoppingcart.model.Product;
import com.Atlavik.shoppingcart.model.ShoppingCart;
import com.Atlavik.shoppingcart.repository.ProductRep;
import com.Atlavik.shoppingcart.repository.ShoppingCartRep;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
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
    @Autowired
    ProductRep productRep;


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

    Long createShoppingCartAgain(Long productId) {
        List<Product> products = new ArrayList<>();
        Product product = new Product();

        product.setId(productId);
        products.add(product);

        //create ner cart
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCountryCode("US");
        shoppingCart.setCurrency("USD");
        shoppingCart.setProducts(products);
        shoppingCart.setCreated("2021-05-07T20:30:00");
        shoppingCart.setUpdated("2021-08-07T20:30:00");
        // Test
        return shoppingCartRep.save(shoppingCart).getId();
    }

    private Long getProductAgain() {
        //creat new Product
        Product product = new Product();
        product.setProductId("1135621-5605-4d75-8317-db282c498c7f");
        product.setDescription("Apple iphone 12");
        product.setCategory("ELECTRONICS");
        product.setPrice(BigDecimal.valueOf(7325.05));
        product.setCreated("2021-05-07T20:30:00");
        product.setUpdated("2021-08-07T20:30:00");


        Product newProduct = productRep.save(product);
        return newProduct.getId();
    }

    @Test
    void getShoppingCart() {
        Long productId = getProductAgain();
        Long cartId = createShoppingCartAgain(productId);
        assertNotNull(shoppingCartRep
                .findById(cartId)
                .orElseThrow(() -> new ElementNotFoundException("Could not find CART with ID provided")));
    }

    @Test
    void getAll() {
        Long productId = getProductAgain();
         createShoppingCartAgain(productId);
        List<ShoppingCart> carts = shoppingCartRep.findAll();
        assertThat(carts).size().isGreaterThan(0);
    }

    @Test
    void deleteShoppingCart() {
        Long productId = getProductAgain();
        Long cartId = createShoppingCartAgain(productId);
        shoppingCartRep.deleteById(cartId);
        assertEquals(shoppingCartRep.findById(cartId), Optional.empty());

    }

    @Test
    void updateShoppingCart() {
        Long productId = getProductAgain();
        Long cartId = createShoppingCartAgain(productId);

        List<Product> products = new ArrayList<>();
        Product product2 = new Product();
        Product product1 = new Product();

        //find cart by Id for adding products
        ShoppingCart cart = shoppingCartRep
                .findById(cartId)
                .orElseThrow(() -> new ElementNotFoundException("Could not find cart with ID provided"));

        //set products to cart (one or more)
        product1.setId(getProductAgain());
        products.add(product1);

        cart.setProducts(products);

        //Test
        ShoppingCart newCart = shoppingCartRep.save(cart);

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
        Long productId = getProductAgain();
        Long cartId = createShoppingCartAgain(productId);
        assertNotNull(shoppingCartRep
                .findById(cartId)
                .orElseThrow(() -> new ElementNotFoundException("Could not find with ID provided")).
                        getProducts().get(0));
    }

    @Test
    void getShoppingCartProducts() {
        Long productId = getProductAgain();
        Long cartId = createShoppingCartAgain(productId);
        ShoppingCart shoppingCart = shoppingCartRep
                .findById(cartId)
                .orElseThrow(() -> new ElementNotFoundException("Could not find with ID provided"));
        assertNotNull(shoppingCart.getProducts());

    }

    @Test
    void deleteProductFromShoppingCart() {
        Long productId = getProductAgain();
        Long cartId = createShoppingCartAgain(productId);
        ShoppingCart shoppingCart = shoppingCartRep
                .findById(cartId)
                .orElseThrow(() -> new ElementNotFoundException("Could not find with ID provided"));
        shoppingCart.getProducts().removeIf(x -> x.getId() == productId);
        assertNotNull(shoppingCartRep.save(shoppingCart));

    }
}