package com.qa.recipe.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
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

import com.qa.recipe.dto.RecipeDTO;

import com.qa.recipe.persistence.entity.Recipe;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RecipeControllerIntegrationTest {

	@Autowired
	private MockMvc mockMVC;
	
	private Recipe recipe;
	
	private RecipeDTO savedRecipe;
	
	private RecipeDTO mapToDTO(Recipe recipe) {
		return new ModelMapper().map(recipe, RecipeDTO.class);
	}
	

	@Autowired
	private ObjectMapper mapper;
	
	@Before
	public void init() {
		this.recipe = new Recipe("Pizza", 2);
		Recipe savedRecipe = new Recipe(recipe.getName(), recipe.getTime());
		savedRecipe.setId(1L);
		this.savedRecipe= this.mapToDTO(savedRecipe);
	}
	
	@Test
	public void testCreate() throws Exception {
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.post("/recipe/create");
		reqBuilder.accept(MediaType.APPLICATION_JSON);
		reqBuilder.contentType(MediaType.APPLICATION_JSON);
		reqBuilder.content(this.mapper.writeValueAsString(recipe));
		
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isCreated();
		ResultMatcher matchContent = MockMvcResultMatchers.content().json(this.mapper.writeValueAsString(savedRecipe));
	
		this.mockMVC.perform(reqBuilder).andExpect(matchStatus).andExpect(matchContent);
	}
	
	
}
