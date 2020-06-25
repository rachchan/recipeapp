package com.qa.recipe.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Recipe {

		@Id
		@GeneratedValue
		Long id;
		
		@Column(unique = true, nullable = false)
		String name;
		
		String ingredients;
		
		double time;
		
		public Recipe() {
			
		}
		
		public Recipe(Long id, String name, String ingredients, double time) {
			super();
			this.id = id;
			this.name = name;
			this.ingredients = ingredients;
			this.time = time;
		}
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
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

		public Double getTime() {
			return time;
		}
		
		public void setTime(Double time) {
			this.time = time;
		}
		
		
}
