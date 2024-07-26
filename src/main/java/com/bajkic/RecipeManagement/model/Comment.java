package com.bajkic.RecipeManagement.model;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment {
	
	@Id
	private Long id;
	private String authorName;
	private String commentBody;
	
	public Long getId() {
		return id;
	}
	public String getAuthorName() {
		return authorName;
	}
	public String getCommentBody() {
		return commentBody;
	}
	
	public Comment(Long id, String authorName, String commentBody) {
		super();
		this.id = id;
		this.authorName = authorName;
		this.commentBody = commentBody;
	}
	public Comment() {
		super();
	}
	@Override
	public String toString() {
		return authorName + " " + commentBody;
	}
	
	
	
}
