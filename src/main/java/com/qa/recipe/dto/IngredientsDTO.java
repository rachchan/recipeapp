package com.qa.recipe.dto;



public class IngredientsDTO {

	private long id;
	private String name;
	
	
	public IngredientsDTO(long id, String name) {
		super();
		this.setId(id);
		this.setName(name);
	
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
	
}
