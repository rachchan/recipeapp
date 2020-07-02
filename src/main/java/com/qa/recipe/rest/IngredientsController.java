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

import com.qa.recipe.dto.IngredientsDTO;
import com.qa.recipe.persistence.entity.Ingredients;
import com.qa.recipe.service.IngredientsService;



@RestController
@CrossOrigin
@RequestMapping("/ingredient")
public class IngredientsController {

	private IngredientsService service;
	
	public IngredientsController(IngredientsService service) {
		super();
		this.service= service;
	}
	
	@PostMapping("/create")
	public ResponseEntity<IngredientsDTO> create(@RequestBody Ingredients ingredient) {
		return new ResponseEntity<IngredientsDTO>(this.service.create(ingredient), HttpStatus.CREATED);
	}
	
	@GetMapping("/read/{name}")
	public ResponseEntity<IngredientsDTO> readOne(@PathVariable String name) {
		return ResponseEntity.ok(this.service.read(name));
	}
	
	@GetMapping("/read")
	public ResponseEntity<List<IngredientsDTO>> read() {
		return new ResponseEntity<List<IngredientsDTO>>(this.service.read(), HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<IngredientsDTO> update(@PathVariable Long id, @RequestBody Ingredients ingredient) {
		return new ResponseEntity<IngredientsDTO>(this.service.update(ingredient, id), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return(this.service.delete(id) ? new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<>(HttpStatus.NO_CONTENT));
		
	}
}
