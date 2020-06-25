package com.qa.recipe.persistence.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.recipe.persistence.entity.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>{

	public Optional<Recipe> findRecipeByName(String name);
	
}