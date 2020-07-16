package com.qa.recipe.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import com.qa.recipe.dto.IngredientsDTO;
import com.qa.recipe.persistence.entity.Ingredients;
import com.qa.recipe.persistence.repository.IngredientsRepository;
import com.qa.recipe.service.IngredientsService;

@RunWith(MockitoJUnitRunner.class)
public class IngredientServiceUnitTest {

	private final Ingredients ingredient = new Ingredients("Flour, Water, Tomato");
	private Ingredients savedIngredient;
	private IngredientsDTO savedIngredienttoDTO;

	private IngredientsDTO mapToDTO(Ingredients ingredient) {
		return new ModelMapper().map(ingredient, IngredientsDTO.class);
	}

	@Mock
	private IngredientsRepository repo;

	@Mock
	private ModelMapper mapper;

	@InjectMocks
	private IngredientsService service;

	@Before
	public void init() {
		savedIngredient = new Ingredients(ingredient.getName());
		savedIngredient.setId(1L);
		this.savedIngredienttoDTO = this.mapToDTO(savedIngredient);
	}

	@Test
	public void testCreate() {
		when(this.repo.save(ingredient)).thenReturn(savedIngredient);
		when(this.mapper.map(savedIngredient, IngredientsDTO.class)).thenReturn(savedIngredienttoDTO);
		assertEquals(savedIngredienttoDTO, service.create(ingredient));
		
		verify(this.repo, Mockito.times(1)).save(ingredient);
	}
	
	@Test
	public void testUpdate() {
		Mockito.when(this.repo.findById(savedIngredient.getId())).thenReturn(Optional.of(savedIngredient));

		Ingredients newIngredient = new Ingredients();
		newIngredient.setName("Pizza");

		Ingredients newIngredientWithID = new Ingredients();
		newIngredientWithID.setName(newIngredient.getName());
		newIngredientWithID.setId(savedIngredient.getId());

		Mockito.when(this.repo.save(newIngredientWithID)).thenReturn(newIngredientWithID);
		when(this.mapper.map(newIngredientWithID, IngredientsDTO.class)).thenReturn(this.mapToDTO(newIngredientWithID));
		assertEquals(this.mapToDTO(newIngredientWithID), this.service.update(newIngredient, savedIngredient.getId()));

		Mockito.verify(this.repo, Mockito.times(1)).findById(savedIngredient.getId());
		Mockito.verify(this.repo, Mockito.times(1)).save(newIngredientWithID);
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
