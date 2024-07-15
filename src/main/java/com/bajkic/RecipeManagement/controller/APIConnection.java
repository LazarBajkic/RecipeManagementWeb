package com.bajkic.RecipeManagement.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.bajkic.RecipeManagement.model.Recipe;

public class APIConnection {
	
	public JSONObject connect(int numOfRecipes,String tags) throws IOException, InterruptedException, ParseException {
		String yourKey = System.getenv("YourKey");
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://tasty.p.rapidapi.com/recipes/list?from=0&size="+numOfRecipes+"&tags="+tags))
				.header("x-rapidapi-key", yourKey)
				.header("x-rapidapi-host", "tasty.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		
		String body = response.body();
		JSONParser parser = new JSONParser();
		JSONObject bodyObj = (JSONObject) parser.parse(body);
		return bodyObj;
	}
	
	public List<Recipe> getRecipes(int numOfRecipes,String tags) throws IOException, InterruptedException, ParseException {
		JSONArray results = (JSONArray) connect(numOfRecipes,tags).get("results");
		List<Recipe> recipeList = new ArrayList<>();
		for(int i = 0;i<results.size();i++) {
			JSONObject obj1 = (JSONObject) results.get(i);
			String name = (String) obj1.get("name");
			String slug = (String) obj1.get("slug");
			Long numOfServings = (Long) obj1.get("num_servings");
			Long cookTimeMinutes = (Long) obj1.get("cook_time_minutes");
			Long prepTimeMinutes = (Long) obj1.get("prep_time_minutes");
			Recipe r = new Recipe(name,slug,numOfServings,cookTimeMinutes,prepTimeMinutes);
			recipeList.add(r);
		}
			return recipeList;
	
	}

}
