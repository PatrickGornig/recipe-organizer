package de.patrickgornig.recipeorganizer.recipe;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends CassandraRepository<Recipe, UUID> {
    
}
