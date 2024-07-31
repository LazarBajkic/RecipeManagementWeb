package com.bajkic.RecipeManagement.Repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bajkic.RecipeManagement.model.Comment;

public interface Repository extends JpaRepository<Comment, Long>{
	
	@Query(value="Select * from comments c where c.Id =:id",nativeQuery=true)
	List<Comment> getCommentsById(Long id);
}
