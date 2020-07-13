package com.qa.recipe.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.recipe.persistence.entity.Ingredients;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class IngredientControllerIntegrationTest {

	@Autowired
	private MockMvc mockMVC;
	
	private Ingredients ingredient;
	
	private Ingredients savedIngredient;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Before
	public void init() {
		this.ingredient = new Ingredients("mozzarella");
		this.savedIngredient = new Ingredients(ingredient.getName());
		this.savedIngredient.setId(1L);
	}
	
	@Test
	public void testCreate() throws Exception {
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.post("/ingredient/create");
		reqBuilder.accept(MediaType.APPLICATION_JSON);
		reqBuilder.contentType(MediaType.APPLICATION_JSON);
		reqBuilder.content(this.mapper.writeValueAsString(ingredient));
		
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isCreated();
		ResultMatcher matchContent = MockMvcResultMatchers.content().json(this.mapper.writeValueAsString(savedIngredient));
		
		this.mockMVC.perform(reqBuilder).andExpect(matchStatus).andExpect(matchContent);
	}
	
	
}
