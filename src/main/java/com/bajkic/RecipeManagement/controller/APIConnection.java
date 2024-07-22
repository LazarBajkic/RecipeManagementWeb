package com.bajkic.RecipeManagement.controller;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.bajkic.RecipeManagement.model.Nutrition;
import com.bajkic.RecipeManagement.model.Recipe;
import com.bajkic.RecipeManagement.model.RecipeDetails;
import com.bajkic.RecipeManagement.model.Tip;

public class APIConnection {
	
	String yourKey = System.getenv("YourKey");
	
	public JSONObject connect(int numOfRecipes, String tags, String recipeName) throws IOException, InterruptedException, ParseException {
		  StringBuilder urlBuilder = new StringBuilder("https://tasty.p.rapidapi.com/recipes/list?from=0&size=");
		  urlBuilder.append(numOfRecipes);
		  if (tags != null && !tags.isEmpty()) {
		    urlBuilder.append("&tags=").append(tags);
		  }
		  if (recipeName != null && !recipeName.isEmpty()) {
		    urlBuilder.append("&q=").append(recipeName);
		  }
		  URI uri = URI.create(urlBuilder.toString());

		  HttpRequest request = HttpRequest.newBuilder()
		          .uri(uri)
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
		
	public List<Recipe> getRecipes(int numOfRecipes,String tags,String recipeName) throws IOException, InterruptedException, ParseException {
		JSONArray results = (JSONArray) connect(numOfRecipes,tags,recipeName).get("results");
		List<Recipe> recipeList = new ArrayList<>();
		for(int i = 0;i<results.size();i++) {
			JSONObject obj1 = (JSONObject) results.get(i);
			Long id = (Long) obj1.get("id");
			String name = (String) obj1.get("name");
			String slug = (String) obj1.get("slug");
			Long numOfServings = (Long) obj1.get("num_servings");
			Long cookTimeMinutes = (Long) obj1.get("cook_time_minutes");
			Long prepTimeMinutes = (Long) obj1.get("prep_time_minutes");
			Recipe r = new Recipe(id,name,slug,numOfServings,cookTimeMinutes,prepTimeMinutes);
			recipeList.add(r);
		}
			return recipeList;
	
	}
	
	public List<String> getTags() throws IOException, InterruptedException, ParseException{
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://tasty.p.rapidapi.com/tags/list"))
				.header("x-rapidapi-key", yourKey)
				.header("x-rapidapi-host", "tasty.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		String tagsBody = response.body();
		JSONParser parser = new JSONParser();
		JSONObject tagBodyObj = (JSONObject) parser.parse(tagsBody);
		JSONArray results = (JSONArray) tagBodyObj.get("results");
		List<String> tagList = new ArrayList<>();
		for(int i = 0;i<results.size();i++) {
			JSONObject tag = (JSONObject) results.get(i);
			String strTag = (String) tag.get("name");
			tagList.add(strTag);
		}
		
		return tagList;
		
	}
	
	public RecipeDetails getRecipeInfo(Long id) throws IOException, InterruptedException, ParseException {
		
		JSONObject bodyObj = getRecipeDetailsAPI(id);
		
		String name = (String) bodyObj.get("name");
		Long cookTimeMinutes=(Long) bodyObj.get("cook_time_minutes");
		String description = (String) bodyObj.get("description");
		Long numOfServings = (Long) bodyObj.get("num_servings");
		Long prepTimeMinutes = (Long) bodyObj.get("prep_time_minutes");
		
		Recipe r = new Recipe(name,description,numOfServings,cookTimeMinutes,prepTimeMinutes);
		
		JSONArray instructions = (JSONArray) bodyObj.get("instructions");
		HashMap<Long,String> instructionsMap = new HashMap<>(); 
		
		for(int i = 0;i<instructions.size();i++) {
			JSONObject currInstruction = (JSONObject) instructions.get(i);
			Long position = (Long) currInstruction.get("position");
			String displayText = (String) currInstruction.get("display_text");
			instructionsMap.put(position, displayText);
		}
	
		JSONObject nutritionValues = (JSONObject) bodyObj.get("nutrition");
		
		Long calories = (Long) nutritionValues.get("calories");
		Long carbs = (Long) nutritionValues.get("carbohydrates");
		Long fat = (Long) nutritionValues.get("fat");
		Long fiber = (Long) nutritionValues.get("fiber");
		Long protein = (Long) nutritionValues.get("protein");
		Long sugar = (Long) nutritionValues.get("sugar");
	
		
		Nutrition nutrition = new Nutrition(calories,carbs,fat,fiber,protein,sugar);;
		
		List<String> ingredients = getIngredients(id);
		
		RecipeDetails rd = new RecipeDetails(r.getId(),r,nutrition,instructionsMap,ingredients);
		return rd;
	}
	
	public JSONObject getRecipeDetailsAPI(Long id) throws IOException, InterruptedException, ParseException {
			
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("https://tasty.p.rapidapi.com/recipes/get-more-info?id="+id))
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
	
	public List<String> getIngredients(Long id) throws IOException, InterruptedException, ParseException{
		JSONObject bodyObj = getRecipeDetailsAPI(id);
		
		JSONArray sections = (JSONArray) bodyObj.get("sections");
		
		List<String> ingredients = new ArrayList<>();
		
		for(int i = 0;i<sections.size();i++) {
			JSONObject section = (JSONObject) sections.get(i);
			JSONArray components = (JSONArray) section.get("components");
			for(int j = 0;j<components.size();j++) {
				JSONObject component = (JSONObject) components.get(j);
				String rawText = (String) component.get("raw_text");
				ingredients.add(rawText);
			}
			
		}
		return ingredients;
	}
	
	public List<Tip> getTips(Long id) throws IOException, InterruptedException, ParseException{
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://tasty.p.rapidapi.com/tips/list?from=0&size=30&id=3562"))
				.header("x-rapidapi-key", yourKey)
				.header("x-rapidapi-host", "tasty.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

		String body = response.body();
		JSONParser parser = new JSONParser();
		JSONObject bodyObj = (JSONObject) parser.parse(body);
		JSONArray results = (JSONArray) bodyObj.get("results");
		
		List<Tip> tipsList = new ArrayList<>();
		
		for(int i = 0;i<results.size();i++) {
			JSONObject result = (JSONObject) results.get(i);
			String authorName = (String) result.get("author_name");
			String text = (String) result.get("tip_body");
			Long upvotes = (Long) result.get("upvotes_total");
			
			Tip tip = new Tip(authorName,text,upvotes);
			tipsList.add(tip);
		}
		return tipsList;
	}

}
