package com.qa.recipe.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import java.util.Optional;
import com.qa.recipe.dto.RecipeDTO;
import com.qa.recipe.persistence.entity.Recipe;
import com.qa.recipe.persistence.repository.RecipeRepository;
import com.qa.recipe.service.RecipeService;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceUnitTest {

	private final Recipe recipe = new Recipe("Pizza", 2.0);
	private Recipe savedRecipe;
	private RecipeDTO savedRecipetoDTO;

	private RecipeDTO mapToDTO(Recipe recipe) {
		return new ModelMapper().map(recipe, RecipeDTO.class);
	}

	@Mock
	private RecipeRepository repo;

	@Mock
	private ModelMapper mapper;

	@InjectMocks
	private RecipeService service;

	@Before
	public void init() {
		savedRecipe = new Recipe(recipe.getName(), recipe.getTime());
		savedRecipe.setId(1L);
		this.savedRecipetoDTO = this.mapToDTO(savedRecipe);

	}

	@Test
	public void testCreate() {
		when(this.repo.save(recipe)).thenReturn(savedRecipe);
		when(this.mapper.map(savedRecipe, RecipeDTO.class)).thenReturn(savedRecipetoDTO);
		assertEquals(savedRecipetoDTO, service.create(recipe));

		verify(this.repo, Mockito.times(1)).save(recipe);
	}

	@Test
	public void testUpdate() {
		Mockito.when(this.repo.findById(savedRecipe.getId())).thenReturn(Optional.of(savedRecipe));

		Recipe newRecipe = new Recipe();
		newRecipe.setName("Pizza");
		newRecipe.setTime(2.0);

		Recipe newRecipeWithID = new Recipe();
		newRecipeWithID.setName(newRecipe.getName());
		newRecipeWithID.setTime(savedRecipe.getTime());
		newRecipeWithID.setId(savedRecipe.getId());

		Mockito.when(this.repo.save(newRecipeWithID)).thenReturn(newRecipeWithID);
		when(this.mapper.map(newRecipeWithID, RecipeDTO.class)).thenReturn(this.mapToDTO(newRecipeWithID));
		assertEquals(this.mapToDTO(newRecipeWithID), this.service.update(newRecipe, savedRecipe.getId()));

		Mockito.verify(this.repo, Mockito.times(1)).findById(savedRecipe.getId());
		Mockito.verify(this.repo, Mockito.times(1)).save(newRecipeWithID);
	}

	@Test
	public void testDeleteFails() {
		final long id = 1L;
		final boolean result = true;
		Mockito.when(this.repo.existsById(id)).thenReturn(result);
		assertEquals(result, this.service.delete(id));
		Mockito.verify(this.repo, Mockito.times(1)).existsById(id);
	}

	@Test
	public void testDeleteSucceeds() {
		final long id = 1L;
		final boolean result = false;
		Mockito.when(this.repo.existsById(id)).thenReturn(result);
		assertEquals(result, this.service.delete(id));
		Mockito.verify(this.repo, Mockito.times(1)).existsById(id);
	}

}
