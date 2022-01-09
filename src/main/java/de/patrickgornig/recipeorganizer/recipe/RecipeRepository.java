package de.patrickgornig.recipeorganizer.recipe;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends CassandraRepository<Recipe, UUID> {
    public List<Recipe> findByName(String name);
}
