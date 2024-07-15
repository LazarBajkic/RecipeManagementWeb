package com.bajkic.RecipeManagement.controller;

import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bajkic.RecipeManagement.model.Recipe;

@Controller
public class RecipeController {

	APIConnection APIc;
	
	@GetMapping("/")
	public ModelAndView testFunc() throws IOException, InterruptedException, ParseException {
		ModelAndView mav = new ModelAndView("index");
		APIc = new APIConnection();
		List<Recipe> r = APIc.getRecipes(5,"breakfast");
		mav.addObject("recipeList", r);
		return mav;
	}
	
	@GetMapping("/printMoreRecipes")
	public ModelAndView printMoreRecipes(@RequestParam ("numOfRecipes") int numOfRecipes,@RequestParam ("tags") String tags) throws IOException, InterruptedException, ParseException {
		ModelAndView mav = new ModelAndView("index");
		APIc = new APIConnection();
		List<Recipe> r = APIc.getRecipes(numOfRecipes,tags);
		mav.addObject("recipeList", r);
		mav.addObject("tags", tags);
		return mav;
	}
	
}
