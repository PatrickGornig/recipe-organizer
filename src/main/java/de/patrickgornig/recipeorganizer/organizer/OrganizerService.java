package de.patrickgornig.recipeorganizer.organizer;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import de.patrickgornig.recipeorganizer.meal.Meal;
import de.patrickgornig.recipeorganizer.meal.MealRepository;
import de.patrickgornig.recipeorganizer.meal.Meal.MealCategory;
import de.patrickgornig.recipeorganizer.recipe.RecipeRepository;
import de.patrickgornig.recipeorganizer.util.YearWeek;

@Controller
public class OrganizerService {

    private final static String DATE_FORMAT = "dd.MM.yyyy";
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    
@Autowired
MealRepository mealRepository;
@Autowired
RecipeRepository recipeRepository;

@GetMapping(value = "/organizer")
    public String getOrganizerForCurrentWeek(Model model) {
        LocalDate localDate = LocalDate.now();
        return "redirect:organizer/" + localDate.getYear() + "/" + localDate.get(WeekFields.ISO.weekOfWeekBasedYear());
    }

@GetMapping(value = "/organizer/{year}/{week}")
    public String getOrganizer(@PathVariable int year,@PathVariable int week, Model model) {
        YearWeek yearWeek = new YearWeek(year,week);
        System.out.println("Selected year " + year + " and week " + week);
        List<Meal> meals = mealRepository.findAllByPlanningYearWeek(yearWeek.toString());
        Map<String,Meal> mealsHashMap = new HashMap<>();
        for(Meal meal : meals){
            System.out.println(meal);
            mealsHashMap.put(meal.getMealCategory() + "_" + meal.getMealDate(), meal);
        }
        System.out.println("Found " + meals.size() + " already assigned meals for the week");
        LocalDate selectedWeek = LocalDate.parse(year+"-01-01").with(ChronoField.ALIGNED_WEEK_OF_YEAR, week+1);
        LocalDate start = selectedWeek.with(DayOfWeek.MONDAY);
        LocalDate end = selectedWeek.with(DayOfWeek.SUNDAY);
        List<String> currentWeekdays = new ArrayList<>();
        Map<MealCategory,TreeMap<LocalDate,Meal>> mealsOutput = new HashMap<>();
        for(MealCategory mealCategory : MealCategory.values()){
            mealsOutput.put(mealCategory, new TreeMap<>());
        }
        for (DayOfWeek day : DayOfWeek.values()) {
            LocalDate localDay = selectedWeek.with(day);
            currentWeekdays.add(localDay.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.GERMANY) + " "
                    + localDay.format(DateTimeFormatter.ofPattern("dd.MM")));
            for(MealCategory mealCategory : MealCategory.values()){
                Meal searchedMeal = mealsHashMap.get(mealCategory + "_" + localDay);
                if(searchedMeal != null){
                    System.out.println("Meal found: " + searchedMeal.getId());
                    mealsOutput.get(mealCategory).put(localDay,searchedMeal);
                }else{
                    mealsOutput.get(mealCategory).put(localDay,new Meal(mealCategory,localDay));
                }
            }
        }
        for(Entry entry : mealsOutput.entrySet()){
            System.out.println(entry.getKey());
            model.addAttribute(entry.getKey()+"Meals", entry.getValue());
        }
        model.addAttribute("currentWeekdays", currentWeekdays);
        model.addAttribute("currentWeek", "Meal Plan: "+  start.format(DATE_TIME_FORMATTER) + " - " + end.format(DATE_TIME_FORMATTER));
        System.out.println(selectedWeek + " " +selectedWeek.minusDays(7L));
        model.addAttribute("previousWeek", getOrganizerEndpointFromWeekAndYear(year,week-1));
        model.addAttribute("nextWeek", getOrganizerEndpointFromWeekAndYear(year,week+1));
        model.addAttribute("mealsOutput", mealsOutput);
        model.addAttribute("mealCategories", MealCategory.values());
        model.addAttribute("activePage", "organizer");

        return "organizer";
/*
        List<LocalDate> currentWeek = new ArrayList<>();
        List<String> currentWeekdays = new ArrayList<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            LocalDate localDay = selectedWeek.with(day);
            currentWeek.add(localDay);
            currentWeekdays.add(localDay.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.GERMANY) + " "
                    + localDay.format(DateTimeFormatter.ofPattern("dd.MM")));
        }
        List<Meal> meals = mealRepository.findAll();
        Map<MealCategory, List<Meal>> mealsToBeDisplayed = new HashMap();
        List<Meal> breakfastMeals = new ArrayList<>();
        for (LocalDate day : currentWeek) {
            Meal dayMeal = new Meal();
            for (Meal meal : meals) {
                if (meal.getMealCategory().equals(MealCategory.BREAKFAST) && meal.getMealDate().equals(day)) {
                    dayMeal = meal;
                }
            }
            breakfastMeals.add(dayMeal);
        }
        List<Meal> lunchMeals = new ArrayList<>();
        for (LocalDate day : currentWeek) {
            Meal dayMeal = new Meal();
            for (Meal meal : meals) {
                if (meal.getMealCategory().equals(MealCategory.LUNCH) && meal.getMealDate().equals(day)) {
                    dayMeal = meal;
                }
            }
            lunchMeals.add(dayMeal);
        }
        List<Meal> dinnerMeals = new ArrayList<>();
        for (LocalDate day : currentWeek) {
            Meal dayMeal = new Meal(day, MealCategory.DINNER);
            for (Meal meal : meals) {
                if (meal.getMealCategory().equals(MealCategory.DINNER) && meal.getMealDate().equals(day)) {
                    dayMeal = meal;
                }
            }
            dinnerMeals.add(dayMeal);
        }
        System.out.println(meals.size());
        model.addAttribute("breakfastMeals", breakfastMeals);
        model.addAttribute("lunchMeals", lunchMeals);
        model.addAttribute("dinnerMeals", dinnerMeals);
        model.addAttribute("previousWeek", selectedWeek.plusDays(-7));
        model.addAttribute("nextWeek", selectedWeek.plusDays(7));
        model.addAttribute("currentWeekdays", currentWeekdays);
        model.addAttribute("currentWeek", currentWeek);
        model.addAttribute("mealCategories", MealCategory.values());
        model.addAttribute("meals", meals);
        return "organizer";
        */
    }

    private String getOrganizerEndpointFromWeekAndYear(int currentYear, int currentWeek){
        if(currentWeek == 0){
            return currentYear-1 + "/52";
        }else if(currentWeek == 53){
            return currentYear+1 + "/1";
        }
        return currentYear + "/" + currentWeek;
    }


}
