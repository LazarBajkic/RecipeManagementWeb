package com.bajkic.RecipeManagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="FavoriteRecipes")
public class FavoriteRecipe {
	
	@Id
	private Long id;
	
	private Long userId;
	private String name;

	public FavoriteRecipe(Long id,Long userId, String name) {
		super();
		this.id=id;
		this.userId = userId;
		this.name = name;
	}

	public FavoriteRecipe() {
		super();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	public Long getUserId() {
		return userId;
	}
	
	
	
}
