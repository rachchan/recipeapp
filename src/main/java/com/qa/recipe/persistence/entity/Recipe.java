package com.qa.recipe.persistence.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Recipe {

		@Id
		@GeneratedValue
		private long id;
		
		@Column(unique = true, nullable = false)
		private String name;
		
		@OneToMany(mappedBy = "recipe")
		private List<Ingredients> ingredients;
		
		private double time;
		
		public Recipe() {
			
		}
		
		public Recipe(Long id, String name, List<Ingredients> ingredients, double time) {
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

		public List<Ingredients> getIngredients() {
			return ingredients;
		}

		public void setIngredients(List<Ingredients> ingredients) {
			this.ingredients = ingredients;
		}

		public Double getTime() {
			return time;
		}
		
		public void setTime(Double time) {
			this.time = time;
		}

		

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
			Recipe other = (Recipe) obj;
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

		@Override
		public String toString() {
			return "Recipe [id=" + id + ", name=" + name + ", ingredients=" + ingredients + ", time=" + time + "]";
		}
		
		
}
