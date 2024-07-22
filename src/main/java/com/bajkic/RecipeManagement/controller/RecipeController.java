package com.bajkic.RecipeManagement.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bajkic.RecipeManagement.model.Recipe;
import com.bajkic.RecipeManagement.model.RecipeDetails;
import com.bajkic.RecipeManagement.model.Tip;

@Controller
public class RecipeController {

	APIConnection APIc;
	List<String> shoppingList = new ArrayList<>();
	List<String> comments = new ArrayList<>();
	
	@GetMapping("/")
	public ModelAndView testFunc() throws IOException, InterruptedException, ParseException {
		ModelAndView mav = new ModelAndView("index");
		APIc = new APIConnection();
		List<Recipe> r = APIc.getRecipes(5,"breakfast","Muffin");
		List<String> tagList = APIc.getTags();
		mav.addObject("recipeList", r);
		mav.addObject("tagList",tagList);
		return mav;
	}
	
	@GetMapping("/printMoreRecipes")
	public ModelAndView printMoreRecipes(@RequestParam ("numOfRecipes") int numOfRecipes,@RequestParam ("tags") String tags,@RequestParam ("recipeName") String recipeName) throws IOException, InterruptedException, ParseException {
		ModelAndView mav = new ModelAndView("index");
		APIc = new APIConnection();
		List<Recipe> r = APIc.getRecipes(numOfRecipes,tags,recipeName);
		List<String> tagList = APIc.getTags();
		mav.addObject("recipeList", r);
		mav.addObject("tagList",tagList);
		return mav;
	}
	
	@GetMapping("/recipeInfo")
	public ModelAndView printRecipeDetails(@RequestParam ("id") Long id) throws IOException, InterruptedException, ParseException, ExecutionException {
		ModelAndView mav = new ModelAndView("RecipeInfoPage");
		RecipeDetails rd = APIc.getRecipeInfo(id);
		List<Tip> tipsList = APIc.getTips(id);
		List<String> ingredients = rd.getIngredients();
		mav.addObject("recipeDetails", rd);
		mav.addObject("tipsList",tipsList);
		mav.addObject("ingredients", ingredients);
		mav.addObject("recipeId", id);
		return mav;
	}
	
	@GetMapping("/addToShoppingList")
	public ModelAndView check(@RequestParam ("ingredient") String ingredient,@RequestParam ("id") Long id) throws IOException, InterruptedException, ParseException, ExecutionException {
		ModelAndView mav = printRecipeDetails(id);
		shoppingList.add(ingredient);
		return mav;
	}
	
	@GetMapping("/Account")
	public ModelAndView goToAccount() {
		ModelAndView mav = new ModelAndView("AccountPage");
		mav.addObject("shoppingList", shoppingList);
		return mav;
	}
	
	@GetMapping("/removeFromShoppingList")
	public ModelAndView removeFromList(@RequestParam ("item") String item) {
		shoppingList.remove(item);
		ModelAndView mav = goToAccount();
		return mav;
	}
	
	@GetMapping("/addComment")
	public ModelAndView addComment(@RequestParam ("commentBody") String commentBody,@RequestParam ("id") Long id) throws IOException, InterruptedException, ParseException, ExecutionException {
		ModelAndView mav = printRecipeDetails(id);
		comments.add(commentBody);
		System.out.println(comments);
		mav.addObject("commentList",comments);
		return mav;
	}
}
