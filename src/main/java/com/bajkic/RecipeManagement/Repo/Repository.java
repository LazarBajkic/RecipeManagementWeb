package com.bajkic.RecipeManagement.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bajkic.RecipeManagement.model.Comment;

public interface Repository extends JpaRepository<Comment, Long>{

}
