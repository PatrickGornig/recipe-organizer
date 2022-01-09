package de.patrickgornig.recipeorganizer.recipe;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class RecipeController {
    
    @Autowired
    RecipeRepository recipeRepository;

    @GetMapping(value = "/recipes/{recipeId}")
    public String getRecipe(@PathVariable String recipeId, Model model){
        Optional<Recipe> optionalRecipe = recipeRepository.findById(UUID.fromString(recipeId));
        if(optionalRecipe.isPresent()){
            Recipe recipe = optionalRecipe.get();
            model.addAttribute("recipe", recipe);
            //return "recipe";
            return "recipes/recipeDetails";
        }
        return "recipe-not-found";
    }

    @GetMapping(value = {"","/","index"})
        public String index(Model model){
            model.addAttribute("activePage", "index");
            return "index";
        }


    @GetMapping(value = {"/recipes"})
        public String getRecipes(
            @RequestParam(required = false) Optional<String> search,
            Model model
            ){
                List<Recipe> recipes = null;
            if(search.isPresent()){
                recipes = recipeRepository.findByName(search.get());
            }else{
                recipes = recipeRepository.findAll();
            }
            model.addAttribute("recipes", recipes);
            model.addAttribute("activePage", "recipes");
            return "recipes/recipeList";
            //return "recipes";
        }
}

