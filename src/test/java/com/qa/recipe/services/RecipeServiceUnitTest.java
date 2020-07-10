package com.qa.recipe.services;

import java.util.stream.Stream;

import org.assertj.core.util.Arrays;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.recipe.persistence.entity.Recipe;
import com.qa.recipe.persistence.repository.RecipeRepository;
import com.qa.recipe.service.RecipeService;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceUnitTest {
	private Recipe recipe;
	
	private Recipe savedRecipe;
	
	@Mock
	private RecipeRepository repo;
	
	@InjectMocks
	private RecipeService service;
	
	@Before
	
	
	
}
