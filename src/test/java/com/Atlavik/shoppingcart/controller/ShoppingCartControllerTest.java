package com.Atlavik.shoppingcart.controller;

import com.Atlavik.shoppingcart.ShoppingcartApplication;
import com.Atlavik.shoppingcart.model.Product;
import com.Atlavik.shoppingcart.model.ShoppingCart;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
class ShoppingCartControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    private Long cartId;
    private Long productId;

    @Autowired
    private ProductController productController;


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
    void createShoppingCart() throws Exception {
        //creat new product
        productId = creatNewProduct().getId();
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

        cartId = (Long) json.get("id");
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
    private Product creatNewProduct() {
        Product product = new Product();
        product.setProductId("1135621-5605-4d75-8317-db282c498c7f");
        product.setDescription("Apple iphone 12");
        product.setCategory("ELECTRONICS");
        product.setPrice(BigDecimal.valueOf(7325.05));
        product.setCreated("2021-05-07T20:30:00");
        product.setUpdated("2021-08-07T20:30:00");
        return productController.createProduct(product);
    }


    @Test
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
    void getShoppingCart() throws Exception {
        String uri = "/api/5";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ShoppingCart cart = mapFromJson(content, ShoppingCart.class);
        assertNotNull(cart);
    }


    @Test
    void getShoppingCartProduct() throws Exception {
        //HttpUriRequest request = new HttpGet("http://localhost:8080/api/{cartId}/product/{productId}");

        // String uri = "/api/"+cartId+"/product/11";
        String uri = "/api/17/product/21";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Product product = mapFromJson(content, Product.class);
        assertNotNull(product);
    }


    @Test
    void updateShoppingCart() throws Exception {
        // {cartId}/product"
        //  String uri = "/api/"+cartId+"/product";
        String uri = "/api/17/product";

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        Product product2 = new Product();
        product1.setId(21L);
        product2.setId(3L);
        products.add(product1);
        products.add(product2);

        //shoppingCart.setProducts(products);
//        Product product=new Product();
//        product.setId(10L);
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
    void deleteProductShoppingCart() throws Exception {
        String uri = "/api/17/product/3";
        ShoppingCart shoppingCart = new ShoppingCart();
        List<Product> products = new ArrayList<>();
//        Product product = new Product();
//        product.setId(3L);
//        products.add(product);
//        shoppingCart.setProducts(products);
//
//        String inputJson = mapToJson(products);
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
    void deleteShoppingCart() throws Exception {
        String uri = "/api/13";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
}