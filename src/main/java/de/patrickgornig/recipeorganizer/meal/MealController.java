package de.patrickgornig.recipeorganizer.meal;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.patrickgornig.recipeorganizer.recipe.Recipe;
import de.patrickgornig.recipeorganizer.recipe.RecipeRepository;

@Controller
public class MealController {

    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    
    @GetMapping(value = "/meals/{id}")
    public String getMeal(
            @RequestParam UUID id,
            Model model) {
        Meal meal = null;
        String recipeImage = "/images/no-image.png";

        Optional<Meal> optionalMeal = mealRepository.findById(id);
        meal = optionalMeal.orElse(new Meal());
        Optional<Recipe> optionalRecipe = recipeRepository.findById(meal.getRecipeId());
        Recipe recipe = optionalRecipe.orElse(null);
        model.addAttribute("recipe", recipe);
        if (recipe.getThumbnail() != null) {
            recipeImage = recipe.getThumbnail();
        }
        model.addAttribute("recipeImage", recipeImage);
        model.addAttribute("meal", meal);
        return "meal";
    }
}
