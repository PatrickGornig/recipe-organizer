package de.patrickgornig.recipeorganizer.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;

@Table(value = "recipe_by_id")
public class Recipe {

    public enum RecipeDifficulty{
        EASY,MEDIUM,HARD
    }

    public enum ServingUnit{
        PIECE,PORTION
    }

    @Id 
    @PrimaryKeyColumn(name = "recipe_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID id;

    @Column("recipe_name")
    @CassandraType(type = Name.TEXT)
    private String name;

    @Column("recipe_difficulty")
    @CassandraType(type = Name.TEXT)
    private RecipeDifficulty difficulty;

    @Column("recipe_ingredients")
    @CassandraType(type = Name.LIST,typeArguments = Name.TEXT)
    private List<String> ingredients;

    @Column("recipe_instructions")
    @CassandraType(type = Name.LIST,typeArguments = Name.TEXT)
    private List<String> instructions;

    @Column("recipe_thumbnail")
    @CassandraType(type = Name.TEXT)
    private String thumbnail;

    @Column("recipe_preparation_time")
    @CassandraType(type = Name.INT)
    private int preparationTime;
    
    @Column("recipe_calories")
    @CassandraType(type = Name.INT)
    private int calories;

    @Column("recipe_serving")
    @CassandraType(type = Name.INT)
    private int serving;

    @Column("recipe_serving_unit")
    @CassandraType(type = Name.TEXT)
    private ServingUnit servingUnit;

    @Column("recipe_url")
    @CassandraType(type = Name.TEXT)
    private String url;

    public Recipe(){
        id = Uuids.timeBased();
        this.ingredients = new ArrayList<>();
        this.instructions = new ArrayList<>();
    }



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RecipeDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(RecipeDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getServing() {
        return serving;
    }

    public void setServing(int serving) {
        this.serving = serving;
    }

    public ServingUnit getServingUnit() {
        return servingUnit;
    }

    public void setServingUnit(ServingUnit servingUnit) {
        this.servingUnit = servingUnit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public void addIngredient(String s) {
        this.ingredients.add(s);
    }



    public void removeIngredient(String s) {
        this.ingredients.remove(s);
    }



    public void removeInstruction(String s) {
        this.instructions.remove(s);
    }



    public void addInstruction(String s) {
        this.instructions.add(s);
    }




    

}
