package com.qa.recipe.rest;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.qa.recipe.dto.IngredientsDTO;
import com.qa.recipe.persistence.entity.Ingredients;
import com.qa.recipe.persistence.repository.IngredientsRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class IngredientControllerIntegrationTest {

	@Autowired
	private MockMvc mockMVC;
	
	private Ingredients ingredient;
	
	private IngredientsDTO savedIngredient;
	
	private IngredientsDTO mapToDTO(Ingredients ingredient) {
		return new ModelMapper().map(ingredient, IngredientsDTO.class);
	}
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private IngredientsRepository repo;
	
	@Before
	public void init() {
		this.repo.deleteAll();
		this.ingredient = new Ingredients("mozzarella");
		Ingredients savedIngredient = this.repo.save(ingredient);
		this.savedIngredient= this.mapToDTO(savedIngredient);
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
	
	@Test
	public void testReadOneSuccess() throws Exception {
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/ingredient/read/"+ this.savedIngredient.getName());
		reqBuilder.accept(MediaType.APPLICATION_JSON);
		reqBuilder.contentType(MediaType.APPLICATION_JSON);
		reqBuilder.content(this.mapper.writeValueAsString(ingredient));
		
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isOk();
		ResultMatcher matchContent = MockMvcResultMatchers.content().json(this.mapper.writeValueAsString(savedIngredient));
	
		this.mockMVC.perform(reqBuilder).andExpect(matchStatus).andExpect(matchContent);
	}
	
	@Test
	public void testUpdateIngredient() throws Exception {
		Ingredients newIngredient = new Ingredients("Fried Rice");
		Ingredients updatedIngredient = new Ingredients(newIngredient.getName());
		updatedIngredient.setId(this.savedIngredient.getId());
		
				this.mockMVC
				.perform(request(HttpMethod.PUT, "/ingredient/update/" + this.savedIngredient.getId()).accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(newIngredient)))
				.andExpect(status().isAccepted()).andExpect(content().json(this.mapper.writeValueAsString(mapToDTO(updatedIngredient))));
		
	}
	
	@Test
	public void testDeleteIngredient() throws Exception {
		this.mockMVC.perform(request(HttpMethod.DELETE, "/ingredient/delete/" + this.ingredient.getId())).andExpect(status().isNoContent());
	}
	
}
