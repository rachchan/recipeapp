package com.qa.recipe.selenium;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage {

	@FindBy(id = "name")
	private WebElement inputName;
	
	
	@FindBy(id = "ingredients")
	private WebElement inputIngredient;
	
	@FindBy(id = "time")
	private WebElement inputTime;
	
	@FindBy(id = "recipeForm")
	private WebElement submitForm;
	
	@FindBy(id = "recipe-list")
	private WebElement listRecipes;
	
	@FindBy(id = "updateForm")
	private WebElement updateRecipe;
	
	@FindBy(id= "updatedName")
	private WebElement inputUpdate;
	
	public void createRecipe(String name, String ingredients, CharSequence[] time) {
		this.inputName.sendKeys(name);
		this.inputIngredient.sendKeys(ingredients);
		this.inputTime.sendKeys(time);
	}
}
