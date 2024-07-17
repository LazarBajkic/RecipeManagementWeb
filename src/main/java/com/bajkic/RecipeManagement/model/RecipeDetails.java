package com.bajkic.RecipeManagement.model;

import java.util.HashMap;

public class RecipeDetails {
	
	private Recipe recipe;
	private Nutrition nutrition;
	private HashMap<Long,String> instructions;
	
	public RecipeDetails(Recipe recipe, Nutrition nutrition, HashMap<Long, String> instructions) {
		super();
		this.recipe = recipe;
		this.nutrition = nutrition;
		this.instructions = instructions;
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

	@Override
	public String toString() {
		return "RecipeDetails [recipe=" + recipe + ", nutrition=" + nutrition + ", instructions=" + instructions + "]";
	}
	
}
