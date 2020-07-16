package com.qa.recipe.dto;

import java.util.List;

public class RecipeDTO {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((ingredients == null) ? 0 : ingredients.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(time);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecipeDTO other = (RecipeDTO) obj;
		if (id != other.id)
			return false;
		if (ingredients == null) {
			if (other.ingredients != null)
				return false;
		} else if (!ingredients.equals(other.ingredients))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(time) != Double.doubleToLongBits(other.time))
			return false;
		return true;
	}

	private long id;
	
	private String name;
	
	private List <IngredientsDTO> ingredients;
	
	private double time;
	
	public RecipeDTO(long id, String name, List<IngredientsDTO> ingredients, double time) {
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

	public List<IngredientsDTO> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<IngredientsDTO> ingredients) {
		this.ingredients = ingredients;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}
	
	
}
