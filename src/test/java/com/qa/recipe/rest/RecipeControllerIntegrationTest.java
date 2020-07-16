package com.qa.recipe.rest;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.qa.recipe.dto.RecipeDTO;
import com.qa.recipe.persistence.entity.Ingredients;
import com.qa.recipe.persistence.entity.Recipe;
import com.qa.recipe.persistence.repository.RecipeRepository;

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
	private RecipeRepository repo;
	

	@Autowired
	private ObjectMapper mapper;
	
	@Before
	public void init() {
		this.repo.deleteAll();
		this.recipe = new Recipe("Pizza", 2);
//		Recipe savedRecipe = new Recipe(recipe.getName(), recipe.getTime());
//		savedRecipe.setId(1L);
		Recipe savedRecipe = this.repo.save(recipe);
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
	
	@Test
	public void testReadOneSuccess() throws Exception {
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/recipe/read/"+ this.savedRecipe.getName());
		reqBuilder.accept(MediaType.APPLICATION_JSON);
		reqBuilder.contentType(MediaType.APPLICATION_JSON);
		reqBuilder.content(this.mapper.writeValueAsString(recipe));
		
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isOk();
		ResultMatcher matchContent = MockMvcResultMatchers.content().json(this.mapper.writeValueAsString(savedRecipe));
	
		this.mockMVC.perform(reqBuilder).andExpect(matchStatus).andExpect(matchContent);

	}
	@Test
	public void testUpdateRecipe() throws Exception {
		Recipe newRecipe = new Recipe("Fried Rice");
		Recipe updatedRecipe = new Recipe(newRecipe.getName(), 2);
		updatedRecipe.setId(this.savedRecipe.getId());
		updatedRecipe.setIngredients(new ArrayList<>());
		
				this.mockMVC
				.perform(request(HttpMethod.PUT, "/recipe/update/" + this.savedRecipe.getId()).accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(newRecipe)))
				.andExpect(status().isAccepted()).andExpect(content().json(this.mapper.writeValueAsString(mapToDTO(updatedRecipe))));
		
	}
	
	@Test
	public void testDeleteRecipe() throws Exception {
		this.mockMVC.perform(request(HttpMethod.DELETE, "/recipe/delete/" + recipe.getId())).andExpect(status().isNoContent());
	}
}
