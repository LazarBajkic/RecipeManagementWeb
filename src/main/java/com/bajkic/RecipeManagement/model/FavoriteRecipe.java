package com.bajkic.RecipeManagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="FavoriteRecipes")
public class FavoriteRecipe {
	
	@Id
	private Long Id;
	
	private String name;

	public FavoriteRecipe(Long id, String name) {
		super();
		Id = id;
		this.name = name;
	}

	public FavoriteRecipe() {
		super();
	}

	public Long getId() {
		return Id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	
	
}
