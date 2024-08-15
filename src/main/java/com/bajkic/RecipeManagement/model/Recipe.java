package com.bajkic.RecipeManagement.model;

public class Recipe {
	
	private Long id;
	private String name;
	private Long numOfServings;
	private Long cookTimeMinutes;
	private Long prepTimeMinutes;
	
	public Recipe(String name,Long numOfServings,Long cookTimeMinutes,Long prepTimeMinutes) {
		this.name=name;
		this.numOfServings=numOfServings;
		this.cookTimeMinutes=cookTimeMinutes;
		this.prepTimeMinutes=prepTimeMinutes;
	}
	
	
	
	public Recipe(Long id, String name, String slug, Long numOfServings, Long cookTimeMinutes, Long prepTimeMinutes) {
		super();
		this.id = id;
		this.name = name;
		this.numOfServings = numOfServings;
		this.cookTimeMinutes = cookTimeMinutes;
		this.prepTimeMinutes = prepTimeMinutes;
	}



	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return name;
	}
	
	public void setNumOfServings(Long numOfServings) {
		this.numOfServings=numOfServings;
	}
	public Long getNumOfServings() {
		return numOfServings;
	}
	
	public void setCookTime(Long cookTimeMinutes) {
		this.cookTimeMinutes=cookTimeMinutes;
	}
	public Long getCookTime() {
		return cookTimeMinutes;
	}
	
	public void setPrepTime(Long prepTimeMinutes) {
		this.prepTimeMinutes=prepTimeMinutes;
	}
	public Long getPrepTime() {
		return prepTimeMinutes;
	}
	public Long getId() {
		return id;
	}
		
}
