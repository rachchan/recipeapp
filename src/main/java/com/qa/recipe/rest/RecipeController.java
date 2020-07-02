package com.qa.recipe.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.recipe.dto.RecipeDTO;
import com.qa.recipe.persistence.entity.Recipe;
import com.qa.recipe.service.*;

@RestController
@CrossOrigin
@RequestMapping("/recipe")
public class RecipeController {

	private RecipeService service;
	
	public RecipeController(RecipeService service) {
		super();
		this.service= service;
	}
	
	@PostMapping("/create")
	public ResponseEntity<RecipeDTO> create(@RequestBody Recipe recipe) {
		return new ResponseEntity<RecipeDTO>(this.service.create(recipe), HttpStatus.CREATED);
	}
	
	@GetMapping("/read/{name}")
	public ResponseEntity<RecipeDTO> readOne(@PathVariable String name) {
		return ResponseEntity.ok(this.service.read(name));
	}
	
	@GetMapping("/read")
	public ResponseEntity<List<RecipeDTO>> read() {
		return new ResponseEntity<List<RecipeDTO>>(this.service.read(), HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<RecipeDTO> update(@PathVariable Long id, @RequestBody Recipe recipe) {
		return new ResponseEntity<RecipeDTO>(this.service.update(recipe, id), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return(this.service.delete(id) ? new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<>(HttpStatus.NO_CONTENT));
		
	}
}
