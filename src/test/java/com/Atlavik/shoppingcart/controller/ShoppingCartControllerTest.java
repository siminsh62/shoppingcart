package com.Atlavik.shoppingcart.controller;

import com.Atlavik.shoppingcart.ShoppingcartApplication;
import com.Atlavik.shoppingcart.model.Product;
import com.Atlavik.shoppingcart.model.ShoppingCart;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Simin
 * @created 14/05/2021 - 8:22 PM
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ShoppingcartApplication.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ShoppingCartControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;


    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    @Test
    @Order(1)
    void createShoppingCart() throws Exception {
        createProduct();
        String uri = "/api";
        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.setCountryCode("US");
        shoppingCart.setCurrency("USD");
        //  shoppingCart.setProducts(null);
        shoppingCart.setCreated("2021-05-07T20:30:00");
        shoppingCart.setUpdated("2021-08-07T20:30:00");

        String inputJson = mapToJson(shoppingCart);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(content);

        Long cartId = (Long) json.get("id");
        String currency = (String) json.get("currency");
        String countryCode = (String) json.get("countryCode");
        // String products = (String) json.get(products);
        String created = (String) json.get("created");
        String updated = (String) json.get("updated");


        assertEquals(currency, "USD");
        assertEquals(countryCode, "US");
        // assertEquals(products, null);
        assertEquals(created, "2021-05-07T20:30:00");
        assertEquals(updated, "2021-08-07T20:30:00");


    }

    //creat New product

    Long createProduct() throws Exception {

        String uri = "/product";
        Product product = new Product();

        product.setProductId("1135621-5605-4d75-8317-db282c498c7f");
        product.setDescription("Apple iphone 12");
        product.setCategory("ELECTRONICS");
        product.setPrice(BigDecimal.valueOf(7325.05));
        product.setCreated("2021-05-07T20:30:00");
        product.setUpdated("2021-08-07T20:30:00");

        String inputJson = mapToJson(product);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(content);

        Long productId = (Long) json.get("id");
        return productId;

    }

    Long createShoppingCartAgain(Long productId) throws Exception {

        String uri = "/api";
        ShoppingCart shoppingCart = new ShoppingCart();
        List<Product> products = new ArrayList<>();

        Product product1 = new Product();
        product1.setId(productId);
        products.add(product1);
        shoppingCart.setCountryCode("US");
        shoppingCart.setCurrency("USD");
        shoppingCart.setProducts(products);
        shoppingCart.setCreated("2021-05-07T20:30:00");
        shoppingCart.setUpdated("2021-08-07T20:30:00");

        String inputJson = mapToJson(shoppingCart);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(content);

        Long cartId = (Long) json.get("id");
        return cartId;
    }

    @Test
    @Order(2)
    void updateShoppingCart() throws Exception {
        Long productId1 = createProduct();
        Long cartId = createShoppingCartAgain(productId1);

        String uri = "/api/" + cartId + "/product";

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(productId1);
        products.add(product1);

        String inputJson = mapToJson(products);

        System.out.println(inputJson);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        //  assertEquals(content, "Product is updated successsfully");
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(content);

        String currency = (String) json.get("currency");
        String countryCode = (String) json.get("countryCode");
        List<Product> p = (List<Product>) json.get("products");
        String created = (String) json.get("created");
        String updated = (String) json.get("updated");


        assertEquals(countryCode, "US");
        assertEquals(currency, "USD");
        assertNotNull(p);
        assertEquals(created, "2021-05-07T20:30:00");
        assertEquals(updated, "2021-08-07T20:30:00");

    }

    @Test
    @Order(3)
    void getAllShoppingCart() throws Exception {

        String uri = "/api";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ShoppingCart[] carts = mapFromJson(content, ShoppingCart[].class);
        assertTrue(carts.length > 0);
    }

    @Test
    @Order(4)
    void getShoppingCart() throws Exception {
        Long productId1 = createProduct();
        Long cartId = createShoppingCartAgain(productId1);

        String uri = "/api/" + cartId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ShoppingCart cart = mapFromJson(content, ShoppingCart.class);
        assertNotNull(cart);
    }


    @Test
    @Order(5)
    void getShoppingCartProduct() throws Exception {
        Long productId1 = createProduct();
        Long cartId = createShoppingCartAgain(productId1);

        String uri = "/api/" + cartId + "/product/" + productId1;

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Product product = mapFromJson(content, Product.class);
        assertNotNull(product);
    }

    @Test
    @Order(6)
    void deleteProductShoppingCart() throws Exception {

        Long productId1 = createProduct();
        Long cartId = createShoppingCartAgain(productId1);

        // String uri = "/api/17/product/3";
        String uri = "/api/" + cartId + "/product/" + productId1;

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(content);

        String currency = (String) json.get("currency");
        String countryCode = (String) json.get("countryCode");
        List<Product> p = (List<Product>) json.get("products");
        String created = (String) json.get("created");
        String updated = (String) json.get("updated");


        assertEquals(countryCode, "US");
        assertEquals(currency, "USD");
        assertNotNull(p);
        assertEquals(created, "2021-05-07T20:30:00");
        assertEquals(updated, "2021-08-07T20:30:00");
    }

    @Test
    @Order(7)
    void deleteShoppingCart() throws Exception {

        Long productId1 = createProduct();
        Long cartId = createShoppingCartAgain(productId1);
        String uri = "/api/" + cartId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }


}