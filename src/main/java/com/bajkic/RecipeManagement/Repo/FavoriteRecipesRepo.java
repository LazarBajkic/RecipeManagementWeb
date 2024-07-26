package com.bajkic.RecipeManagement.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bajkic.RecipeManagement.model.FavoriteRecipe;

public interface FavoriteRecipesRepo extends JpaRepository<FavoriteRecipe, Long>{

}
