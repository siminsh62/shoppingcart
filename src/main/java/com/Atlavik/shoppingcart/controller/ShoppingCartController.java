package com.Atlavik.shoppingcart.controller;

import com.Atlavik.shoppingcart.service.ShoppingCartService;
import com.Atlavik.shoppingcart.model.Product;
import com.Atlavik.shoppingcart.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Simin
 * @created 11/05/2021 - 12:09 AM
 */

@RestController
@RequestMapping("/api")
public class ShoppingCartController {

    private final ShoppingCartService cartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCart createShoppingCart(@RequestBody ShoppingCart cart) {
        return cartService.createShoppingCart(cart);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ShoppingCart> getAllShoppingCart() {
        return cartService.getAll();
    }


    @GetMapping(path = "/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCart getShoppingCart(@PathVariable Long cartId) {
        return cartService.getShoppingCart(cartId);
    }

    @GetMapping(path = "/{cartId}/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public Product getShoppingCartProduct(@PathVariable Long cartId, @PathVariable Long productId) {
        return cartService.getShoppingCartProduct(cartId, productId);
    }

    @PutMapping(path = "/{cartId}/product")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCart updateShoppingCart(@PathVariable Long cartId, @RequestBody List<Product> products) {
        return cartService.updateShoppingCart(cartId, products);
    }

    @DeleteMapping(path = "/{cartId}/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCart deleteProductShoppingCart(@PathVariable Long cartId, @PathVariable Long productId) {
        return cartService.deleteProductFromShoppingCart(cartId, productId);
    }

    @DeleteMapping(path = "/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteShoppingCart(@PathVariable Long cartId) {
        cartService.deleteShoppingCart(cartId);
    }
}
