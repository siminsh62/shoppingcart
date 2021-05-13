package com.Atlavik.shoppingcart;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class ShoppingcartApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Before
	public void setup() {
	}

	@org.junit.Test
	public void insertTest() throws Exception {
		mockMvc.perform(post("/api")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" +
						"   \"countryCode\": eee,\n" +
						"   \"currency\": \"eee\",\n" +
						"   \"created\": \"1985-01-01\",\n" +
						"   \"updated\": \"1985-01-01\",\n" +
						"}")

				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.countryCode").value("eee"))
				.andExpect(jsonPath("$.currency").value("1985-01-01"))
				.andExpect(jsonPath("$.created").value("1985-01-01"))
				.andExpect(jsonPath("$.updated").value("1985-01-01"));
	}

	@Test
	public void selectTest() throws Exception {

		mockMvc.perform(get("/api")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	public void insertAndSelectByIDTest() throws Exception {
		mockMvc.perform(put("/api")
				.contentType(MediaType.APPLICATION_JSON)
				.content("[" +
						"{\n" +
						"   \"id\": 1,\n" +
						"   \"productId\": \"3333\",\n" +
						"   \"description\": \"Foo\",\n" +
						"   \"category\": \"Bar\",\n" +
						"   \"price\": 3876542098\n" +
						"   \"created\": \"1985-01-01\",\n" +
						"   \"updated\": \"1985-01-01\",\n" +
						"}"+
						"]")

				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.productId").value("333"))
				.andExpect(jsonPath("$.description").value("Foo"))
				.andExpect(jsonPath("$.category").value("Bar"))
				.andExpect(jsonPath("$.price").value("3876542098"))
				.andExpect(jsonPath("$.created").value("1985-01-01"))
				.andExpect(jsonPath("$.updated").value("1985-01-01"));


//        mockMvc.perform(get("/select/1")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.date").value("1985-01-01"))
//                .andExpect(jsonPath("$.firstName").value("Foo"))
//                .andExpect(jsonPath("$.lastName").value("Bar"))
//                .andExpect(jsonPath("$.phoneNumber").value("3876542098"));
	}

	@Test
	public void insertAndDeleteTest() throws Exception {
		mockMvc.perform(post("/api")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" +
						"   \"id\": 1,\n" +
						"   \"date\": \"1985-01-01\",\n" +
						"   \"firstName\": \"Foo\",\n" +
						"   \"lastName\": \"Bar\",\n" +
						"   \"phoneNumber\": 3876542098\n" +
						"}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.date").value("1985-01-01"))
				.andExpect(jsonPath("$.firstName").value("Foo"))
				.andExpect(jsonPath("$.lastName").value("Bar"))
				.andExpect(jsonPath("$.phoneNumber").value("3876542098"));


		mockMvc.perform(delete("/api/4/product/3")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}
