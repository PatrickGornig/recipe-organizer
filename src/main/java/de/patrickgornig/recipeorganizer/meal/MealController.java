package de.patrickgornig.recipeorganizer.meal;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import de.patrickgornig.recipeorganizer.recipe.Recipe;
import de.patrickgornig.recipeorganizer.recipe.RecipeRepository;

@Controller
public class MealController {

    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    
    @GetMapping(value = "/meals/{id}")
    public String getMealById(
            @PathVariable UUID id,
            Model model) {
        Meal meal = null;
        String recipeImage = "/images/no-image.png";
        System.out.println(id);
        Optional<Meal> optionalMeal = mealRepository.findById(id);
        meal = optionalMeal.orElse(new Meal());
        if(meal.getRecipeId() != null){
            Optional<Recipe> optionalRecipe = recipeRepository.findById(meal.getRecipeId());
            Recipe recipe = optionalRecipe.orElse(null);
            model.addAttribute("recipe", recipe);
            if (recipe.getThumbnail() != null) {
                recipeImage = recipe.getThumbnail();
            }
            model.addAttribute("recipeImage", recipeImage);
        }else{
            List<Recipe> recipes = recipeRepository.findAll();
            model.addAttribute("recipes", recipes);
        }
        model.addAttribute("showMeal", meal);
        return "showMeal";
    }

    @GetMapping(value = "/meals/{id}/recipe/{recipeId}")
    public String addRecipeToMeal(
        @PathVariable UUID id,
        @PathVariable UUID recipeId,
        Model model
    ){
        Optional<Meal> optionalMeal = mealRepository.findById(id);
        if(optionalMeal.isPresent()){
            String recipeImage = "/images/no-image.png";
            Meal meal = optionalMeal.get();
            Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
            if(optionalRecipe.isPresent()){
                meal.setRecipeId(recipeId);
                mealRepository.save(meal);
                model.addAttribute("recipe", optionalRecipe.get());
                model.addAttribute("meal", meal);
                model.addAttribute("recipeImage", recipeImage);
                return "showMeal";
            }else{
                return "recipe-not-found";
            }
        }else{
            return "meal-not-found";
        }
        
    }

    @GetMapping(value ="/meals")
    public String listMeals(Model model){
        List<Meal> meals = mealRepository.findAllOrderByMealDateDesc();
        model.addAttribute("meals", "meals");
        model.addAttribute("activePage", "meals");
        return "meals/mealList";
    }
}
