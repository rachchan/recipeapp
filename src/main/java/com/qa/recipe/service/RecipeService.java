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
		return this.repo.save(recipe);	
	}
	
	/**
	 *Updates recipes in database
	 */
	public Recipe update(Recipe recipe, long id) {
		Recipe toUpdate = this.repo.findById(id).orElseThrow(() -> new NotFoundException());
		
		toUpdate.setIngredients(recipe.getIngredients());
		toUpdate.setName(recipe.getName());
		toUpdate.setTime(recipe.getTime());
		
		return this.repo.save(toUpdate);
	}
	
	/**
	 * Deletes recipe from id entered
	 */
	public boolean delete (Long id) {
		this.repo.deleteById(id);
		return this.repo.existsById(id);
	}
	
	
}
