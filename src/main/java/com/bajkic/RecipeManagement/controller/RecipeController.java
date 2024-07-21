package com.bajkic.RecipeManagement.controller;

import java.io.IOException;
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
		return mav;
	}
	@GetMapping("/addToShoppingList")
	public void check(@RequestParam ("ingredient") String ingredient) {
		System.out.println(ingredient);
	}
}
