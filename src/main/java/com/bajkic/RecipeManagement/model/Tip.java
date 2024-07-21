package com.bajkic.RecipeManagement.model;

public class Tip {
	
	private String authorName;
	private String text;
	private Long upvotes;
	
	public String getAuthorName() {
		return authorName;
	}
	public String getText() {
		return text;
	}
	public Long getUpvotes() {
		return upvotes;
	}
	
	public Tip(String authorName, String text, Long upvotes) {
		super();
		this.authorName = authorName;
		this.text = text;
		this.upvotes = upvotes;
	}
	
	@Override
	public String toString() {
		return "Tip [authorName=" + authorName + ", text=" + text + ", upvotes=" + upvotes + "]";
	}
	
	
	
}
