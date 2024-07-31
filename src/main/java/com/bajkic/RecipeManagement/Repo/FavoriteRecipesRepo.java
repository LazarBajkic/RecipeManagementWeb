package com.bajkic.RecipeManagement.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bajkic.RecipeManagement.model.FavoriteRecipe;

public interface FavoriteRecipesRepo extends JpaRepository<FavoriteRecipe, Long>{
 
	@Query(value="Select * from favorite_recipes fr where fr.user_id=:userId",nativeQuery=true)
	List<FavoriteRecipe> getByUserID(@Param("userId") Long userId);
	
}
