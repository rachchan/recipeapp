package com.qa.recipe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.modelmapper.ModelMapper;
import com.qa.recipe.persistence.entity.*;
import com.qa.recipe.persistence.repository.RecipeRepository;
import com.qa.recipe.dto.RecipeDTO;
import com.qa.recipe.exceptions.*;


@Service
public class RecipeService {
	
	@Autowired
	private RecipeRepository repo;
	private ModelMapper mapper;
	
	public RecipeService(RecipeRepository repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}
	/**
	 * Scrolls through RecipeDTO and gets different objects
	 */
	private RecipeDTO mapToDTO(Recipe recipe) {
		return this.mapper.map(recipe, RecipeDTO.class);
	}
	
	/**
	 * Returns all recipes in the database
	 */
	public List <RecipeDTO> read() {
		List<RecipeDTO> dtos = new ArrayList<>();
		for (Recipe recipe : this.repo.findAll()) {
			dtos.add(this.mapToDTO(recipe));
		}
		return dtos;
	}
	
	/**
	 * Returns a single recipe from the name entered
	 */
	public RecipeDTO read(String name) {
		Recipe found = this.repo.findRecipeByName(name).orElseThrow(() -> new NotFoundException());
		return this.mapToDTO(found);
	}
	
	/**
	 * Inserts recipes into the database
	 */
	public RecipeDTO create (Recipe recipe) {
		Recipe saved = this.repo.save(recipe);
		return this.mapToDTO(saved);
	}
	
	/**
	 *Updates recipes in database
	 */
	public RecipeDTO update(Recipe recipe, long id) {
		
		Optional<Recipe> optRecipe = this.repo.findById(id);
		
		Recipe toUpdate = optRecipe.orElseThrow(() -> new NotFoundException());
		
		toUpdate.setIngredients(recipe.getIngredients());
		toUpdate.setName(recipe.getName());
		toUpdate.setTime(recipe.getTime());
		
		Recipe updated = this.repo.save(toUpdate);
		
		return this.mapToDTO(updated);
	}
	
	/**
	 * Deletes recipe from id entered
	 */
	
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return this.repo.existsById(id);
	}
	
	
}
