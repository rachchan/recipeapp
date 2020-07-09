package com.qa.recipe.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.recipe.persistence.entity.Ingredients;


@Repository
public interface IngredientsRepository extends JpaRepository<Ingredients, Long>{

	public Optional<Ingredients> findIngredientsByName(String name);
}
