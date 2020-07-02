package com.qa.recipe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.recipe.dto.IngredientsDTO;
import com.qa.recipe.exceptions.NotFoundException;
import com.qa.recipe.persistence.entity.Ingredients;
import com.qa.recipe.persistence.repository.IngredientsRepository;


@Service
public class IngredientsService {
	
	@Autowired
	private IngredientsRepository repo;
	private ModelMapper mapper;
	
	public IngredientsService(IngredientsRepository repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}
	/**
	 * Scrolls through IngredientsDTO and gets different objects
	 */
	private IngredientsDTO mapToDTO(Ingredients ingredient) {
		return this.mapper.map(ingredient, IngredientsDTO.class);
	}
	
	/**
	 * Returns all recipes in the database
	 */
	public List <IngredientsDTO> read() {
		List<IngredientsDTO> dtos = new ArrayList<>();
		for (Ingredients ingredient : this.repo.findAll()) {
			dtos.add(this.mapToDTO(ingredient));
		}
		return dtos;
	}
	
	/**
	 * Returns a single recipe from the name entered
	 */
	public IngredientsDTO read(String name) {
		Ingredients found = this.repo.findIngredientsByName(name).orElseThrow(() -> new NotFoundException());
		return this.mapToDTO(found);
	}
	
	/**
	 * Inserts recipes into the database
	 */
	public IngredientsDTO create (Ingredients ingredient) {
		Ingredients saved = this.repo.save(ingredient);
		return this.mapToDTO(saved);
	}
	
	/**
	 *Updates recipes in database
	 */
	public IngredientsDTO update(Ingredients ingredient, long id) {
		
		Optional<Ingredients> optIngredients = this.repo.findById(id);
		
		Ingredients toUpdate = optIngredients.orElseThrow(() -> new NotFoundException());
		
		toUpdate.setName(ingredient.getName());
		toUpdate.setRecipe(ingredient.getRecipe());
		
		Ingredients updated = this.repo.save(toUpdate);
		
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
