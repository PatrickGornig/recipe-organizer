package de.patrickgornig.recipeorganizer.meal;

import java.time.LocalDate;
import java.util.UUID;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;

import de.patrickgornig.recipeorganizer.recipe.Recipe.ServingUnit;
import de.patrickgornig.recipeorganizer.util.YearWeek;


@Table(value = "meal_by_id")
public class Meal {

    public enum MealCategory{
        BREAKFAST,LUNCH,DINNER
    }

    @Id 
    @PrimaryKeyColumn(name = "meal_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID id;

    @Indexed
    @Column("planning_year_week")
    @CassandraType(type = Name.TEXT)
    private String planningYearWeek;

    @Column("meal_category")
    private MealCategory mealCategory;

    @Indexed
    @Column("meal_date")
    @CassandraType(type = Name.DATE)
    private LocalDate mealDate;

    @Column("recipe_name")
    private String recipeName;

    @Column("recipe_id")
    @CassandraType(type = Name.TEXT)
    private UUID recipeId;

    @Column("meal_servings")
    @CassandraType(type = Name.INT)
    private int servings;

    @Column("meal_serving_unit")
    @CassandraType(type = Name.TEXT)
    private ServingUnit servingUnit;

    public Meal(){
        this.id = Uuids.timeBased();
    }

    public Meal(MealCategory mealCategory, LocalDate mealDate) {
        this.id = Uuids.timeBased();
        this.mealCategory = mealCategory;
        this.mealDate = mealDate;
        this.planningYearWeek = new YearWeek(mealDate).toString();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public MealCategory getMealCategory() {
        return mealCategory;
    }

    public void setMealCategory(MealCategory mealCategory) {
        this.mealCategory = mealCategory;
    }

    public LocalDate getMealDate() {
        return mealDate;
    }

    public void setMealDate(LocalDate mealDate) {
        this.planningYearWeek = new YearWeek(mealDate).toString();
        this.mealDate = mealDate;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public UUID getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(UUID recipeId) {
        this.recipeId = recipeId;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public ServingUnit getServingUnit() {
        return servingUnit;
    }

    public void setServingUnit(ServingUnit servingUnit) {
        this.servingUnit = servingUnit;
    }

    public String getPlanningYearWeek() {
        return planningYearWeek;
    }

    public void setPlanningYearWeek(YearWeek planningYearWeek) {
        this.planningYearWeek = planningYearWeek.toString();
    }
    
}
