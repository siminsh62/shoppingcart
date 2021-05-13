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
//
//    @Autowired
//    private ShoppingCartService cartService;

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


    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCart getShoppingCart(@PathVariable Long id) {
        return cartService.getShoppingCart(id);
    }

    @GetMapping(path = "/{cartId}/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public Product getShoppingCartProducts(@PathVariable Long cartId, @PathVariable Long productId) {
        return cartService.getShoppingCartProducts(cartId, productId);
    }

    @PutMapping(path = "/{id}/product")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCart updateShoppingCart(@PathVariable Long id, @RequestBody List<Product> product) {
        return cartService.updateShoppingCart(id, product);
    }

    @DeleteMapping(path = "/{cartId}/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCart deleteProductShoppingCart(@PathVariable Long cartId, @PathVariable Long productId) {
        return cartService.deleteProductFromShoppingCart(cartId, productId);
    }


    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteShoppingCart(@PathVariable Long id) {
        cartService.deleteShoppingCart(id);
    }
}
