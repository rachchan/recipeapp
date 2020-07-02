package com.qa.recipe.dto;

import com.qa.recipe.persistence.entity.Recipe;

public class IngredientsDTO {

	private long id;
	private String name;
	private Recipe recipe;
	
	public IngredientsDTO(long id, String name, Recipe recipe) {
		super();
		this.setId(id);
		this.setName(name);
		this.setRecipe(recipe);
	}
	
	public IngredientsDTO() {
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
}
