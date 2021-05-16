package com.Atlavik.shoppingcart.controller;

import com.Atlavik.shoppingcart.ShoppingcartApplication;
import com.Atlavik.shoppingcart.model.Product;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Simin
 * @created 15/05/2021 - 12:06 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ShoppingcartApplication.class)
@WebAppConfiguration
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;


    //    protected void setUp() {
//        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }
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
    void createProduct() throws Exception {

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

        Long proId = (Long) json.get("id");
        String productId = (String) json.get("productId");
        String description = (String) json.get("description");
        String category = (String) json.get("category");
        String created = (String) json.get("created");
        String updated = (String) json.get("updated");

        //assertEquals(content, "{\"id\":10,\"productId\":null,\"description\":null," +
        //          "\"category\":\"Ginger\",\"price\":5.55,\"created\":\"Ginger\",\"updated\":null}");


        assertEquals(productId, "1135621-5605-4d75-8317-db282c498c7f");
        assertEquals(category, "ELECTRONICS");
        assertEquals(description, "Apple iphone 12");
        assertEquals(created, "2021-05-07T20:30:00");
        assertEquals(updated, "2021-08-07T20:30:00");

    }

    Long createProductAgain() throws Exception {

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

        return (Long) json.get("id");
    }


    @Test
    void deleteProduct() throws Exception {
        Long proId = createProductAgain();
        String uri = "/product/" + proId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    void getProducts() throws Exception {
        createProductAgain();
        String uri = "/product";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Product[] products = mapFromJson(content, Product[].class);
        assertTrue(products.length > 0);

    }

    @Test
    void getProductByID() throws Exception {
        Long proId = createProductAgain();
        String uri = "/product/" + proId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Product product = mapFromJson(content, Product.class);
        assertNotNull(product);
    }
}