package com.qa.recipe.dto;

public class RecipeDTO {

	private long id;
	
	private String name;
	
	private String ingredients;
	
	private double time;
	
	public RecipeDTO(long id, String name, String ingredients, double time) {
		super();
		this.setId(id);
		this.setName(name);
		this.setIngredients(ingredients);
		this.setTime(time);
	}
	
	public RecipeDTO() {
		
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

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}
	
	
}
