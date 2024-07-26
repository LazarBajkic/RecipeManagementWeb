package com.bajkic.RecipeManagement.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bajkic.RecipeManagement.model.User;

public interface UserRepo extends JpaRepository<User, Long>{

	@Query(value="Select * from users u where u.username=:username",nativeQuery=true)
	User findByUsername(@Param("username")String username);
	
}
