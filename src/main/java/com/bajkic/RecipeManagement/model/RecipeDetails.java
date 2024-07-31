package com.bajkic.RecipeManagement.model;

import java.util.HashMap;
import java.util.List;

public class RecipeDetails {
	
	private Long id;
	private Recipe recipe;
	private Nutrition nutrition;
	private HashMap<Long,String> instructions;
	private List<String> ingredients;
	
	public RecipeDetails(Long id,Recipe recipe, Nutrition nutrition, HashMap<Long, String> instructions,List<String> ingredients) {
		super();
		this.id=recipe.getId();
		this.recipe = recipe;
		this.nutrition = nutrition;
		this.instructions = instructions;
		this.ingredients=ingredients;
	}
	
	public String getRecipeName() {
		return this.recipe.getName();
	}
	
	public Recipe getRecipe() {
		return recipe;
	}
	public Nutrition getNutrition() {
		return nutrition;
	}
	public HashMap<Long, String> getInstructions() {
		return instructions;
	}

	public List<String> getIngredients() {
		return ingredients;
	}
	

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "RecipeDetails [recipe=" + recipe + ", nutrition=" + nutrition + ", instructions=" + instructions;
	}
	
}
