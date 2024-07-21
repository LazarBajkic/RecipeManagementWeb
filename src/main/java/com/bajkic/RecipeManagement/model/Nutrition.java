package com.bajkic.RecipeManagement.model;

public class Nutrition {
		
		private Long calories;
	    private Long carbohydrates;
	    private Long fat;
	    private Long fiber;
	    private Long protein;
	    private Long sugar;

	    // Constructor
	    public Nutrition(Long calories2, Long carbs, Long fat2, Long fiber2, Long protein2, Long sugar2) {
	        this.calories = calories2;
	        this.carbohydrates = carbs;
	        this.fat = fat2;
	        this.fiber = fiber2;
	        this.protein = protein2;
	        this.sugar = sugar2;
	    }

	    // Getters and Setters
	    public Long getCalories() {
	        return calories;
	    }

	    public Long getCarbohydrates() {
	        return carbohydrates;
	    }

	    public Long getFat() {
	        return fat;
	    }

	    public Long getFiber() {
	        return fiber;
	    }

	    public Long getProtein() {
	        return protein;
	    }

	    public Long getSugar() {
	        return sugar;
	    }

		@Override
		public String toString() {
			return "Calories=" + calories + "\n Carbohydrates=" + carbohydrates + "\n Fat=" + fat + "\n Fiber="
					+ fiber + "\n Protein=" + protein + "\n Sugar=" + sugar;
		}
	    
	    
	    
	}
