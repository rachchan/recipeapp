package com.qa.recipe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qa.recipe.persistence.entity.*;
import com.qa.recipe.persistence.repository.RecipeRepository;
import com.qa.recipe.exceptions.*;


@Service
public class RecipeService {

	@Autowired
	private RecipeRepository repo;
	
	public RecipeService(RecipeRepository repo) {
		super();
		this.repo = repo;
	}
	
	/**
	 * Returns all recipes in the database
	 */
	public List <Recipe> getAllRecipes() {
		return repo.findAll();
	}
	
	/**
	 * Returns a single recipe from the name entered
	 */
	public Recipe getRecipe(String name) {
		return repo.findRecipeByName(name).orElseThrow(() -> new NotFoundException());
	}
	/**
	 * Inserts recipes into the database
	 */
	public Recipe create (Recipe recipe) {
		this.repo.save(recipe);
		return null;
	}
	
	
	
}
