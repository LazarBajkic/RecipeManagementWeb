package com.bajkic.RecipeManagement.controller;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.security.core.Authentication;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bajkic.RecipeManagement.Repo.FavoriteRecipesRepo;
import com.bajkic.RecipeManagement.Repo.Repository;
import com.bajkic.RecipeManagement.Repo.UserRepo;
import com.bajkic.RecipeManagement.model.Comment;
import com.bajkic.RecipeManagement.model.FavoriteRecipe;
import com.bajkic.RecipeManagement.model.Recipe;
import com.bajkic.RecipeManagement.model.RecipeDetails;
import com.bajkic.RecipeManagement.model.Tip;
import com.bajkic.RecipeManagement.model.User;

@Controller
public class RecipeController {

	APIConnection APIc;
	List<String> shoppingList = new ArrayList<>();
	Optional<Comment> comments;
	Optional<FavoriteRecipe> favouriteRecipes;
	
	@Autowired
	FavoriteRecipesRepo FRP;
	
	@Autowired
	Repository repo;
	
	@Autowired
	UserRepo uRep;
	
	@GetMapping("/")
	public ModelAndView testFunc() throws IOException, InterruptedException, ParseException {
		ModelAndView mav = new ModelAndView("index");
		APIc = new APIConnection();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); 
		List<Recipe> r = APIc.getRecipes(5,"breakfast","Muffin");
		List<String> tagList = APIc.getTags();
		mav.addObject("recipeList", r);
		mav.addObject("tagList",tagList);
		mav.addObject("username", username);
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
		comments = repo.findById(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); 
		mav.addObject("recipeDetails", rd);
		mav.addObject("tipsList",tipsList);
		mav.addObject("ingredients", ingredients);
		mav.addObject("recipeId", id);
		mav.addObject("username", username);
		mav.addObject("commentList",comments);
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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); 
		User user = uRep.findByUsername(username);
		favouriteRecipes=FRP.findById(user.getId());
		mav.addObject("favorites", favouriteRecipes);		
		mav.addObject("shoppingList", shoppingList);
		return mav;
	}
	
	@GetMapping("/removeFromShoppingList")
	public ModelAndView removeFromList(@RequestParam ("item") String item) {
		shoppingList.remove(item);
		ModelAndView mav = goToAccount();
		return mav;
	}
	
	@PostMapping("/addComment")
	public ModelAndView addComment(@RequestParam ("commentBody") String commentBody,@RequestParam ("id") Long id) throws IOException, InterruptedException, ParseException, ExecutionException {
		ModelAndView mav = printRecipeDetails(id);
		Comment c = new Comment(id,"Name",commentBody);
		repo.save(c);
		return mav;
	}
	
	@GetMapping("/addToFavorites")
	public ModelAndView addToFavorites(@RequestParam ("recipe") String rd,@RequestParam("id") Long id) throws IOException, InterruptedException, ParseException, ExecutionException {
		ModelAndView mav = printRecipeDetails(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); 
		User user = uRep.findByUsername(username);
		FavoriteRecipe fr = new FavoriteRecipe(user.getId(),rd);
		FRP.save(fr);
		return mav;
	}
	
}
