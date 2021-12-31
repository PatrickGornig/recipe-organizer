package de.patrickgornig.recipeorganizer;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.temporal.WeekFields;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import de.patrickgornig.recipeorganizer.connection.DataStaxAstraProperties;
import de.patrickgornig.recipeorganizer.meal.Meal;
import de.patrickgornig.recipeorganizer.meal.MealRepository;
import de.patrickgornig.recipeorganizer.meal.Meal.MealCategory;
import de.patrickgornig.recipeorganizer.recipe.Recipe;
import de.patrickgornig.recipeorganizer.recipe.RecipeRepository;
import de.patrickgornig.recipeorganizer.recipe.Recipe.RecipeDifficulty;



@SpringBootApplication
@EnableConfigurationProperties(DataStaxAstraProperties.class)
public class RecipeOrganizerApplication {

	@Autowired 
	RecipeRepository recipeRepository;
	@Autowired
	MealRepository mealRepository;
 
	public static void main(String[] args) {
		SpringApplication.run(RecipeOrganizerApplication.class, args);
	}


	@PostConstruct
	public void start(){
		System.out.println("Application started.");
		Recipe recipe = new Recipe();
        recipe.setName("Recipe name");
        recipe.setDifficulty(RecipeDifficulty.EASY);
		recipe.addInstruction("1. Instruction Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");
		recipe.addInstruction("2. Instruction Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");
		recipe.addInstruction("3. Instruction Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");
		recipe.addInstruction("4. Instruction");
		recipe.removeInstruction("4. Instruction");
        recipe.addIngredient("Potato");
        recipe.addIngredient("Onion");
        recipe.addIngredient("Meat");
        recipe.removeIngredient("Meat");
		recipe.addIngredient("Another ingredient");
		recipe.addIngredient("More ingredients");
		recipe.setUrl("https://eatsmarter.de/rezepte/kokos-shakshuka");
        recipe.setThumbnail("https://images.eatsmarter.de/sites/default/files/styles/300x225-webp/public/grapefruit-litschi-granatapfel-salat-655481.jpg");
        recipeRepository.save(recipe);
		Recipe recipe2 = new Recipe();
		recipe2.setName("Recipe name 2");
		recipeRepository.save(recipe2);
		Meal meal = new Meal();
		meal.setMealDate(LocalDate.now());
		meal.setMealCategory(MealCategory.LUNCH);
		meal.setRecipeId(recipe.getId());
		meal.setRecipeName(recipe.getName());
		mealRepository.save(meal);
		Meal meal2 = new Meal();
		meal2.setMealDate(LocalDate.now().plusDays(1L));
		meal2.setMealCategory(MealCategory.DINNER);
		meal2.setRecipeId(recipe2.getId());
		meal2.setRecipeName(recipe2.getName());
		mealRepository.save(meal2);
		Meal meal3 = new Meal();
		meal3.setMealDate(LocalDate.now().minusDays(1L));
		meal3.setMealCategory(MealCategory.BREAKFAST);
		meal3.setRecipeId(recipe.getId());
		meal3.setRecipeName(recipe.getName());
		mealRepository.save(meal3);
		System.out.println("-------- "+LocalDate.now().get(WeekFields.ISO.weekOfWeekBasedYear()));
	}


	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties){
		Path bundle = astraProperties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}

}
