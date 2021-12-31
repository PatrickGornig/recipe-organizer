package de.patrickgornig.recipeorganizer.meal;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends CassandraRepository<Meal,UUID> {
    public List<Meal> findAllByPlanningYearWeek(String planningYearWeek);
}
