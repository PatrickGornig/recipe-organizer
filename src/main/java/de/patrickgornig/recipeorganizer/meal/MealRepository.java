package de.patrickgornig.recipeorganizer.meal;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends CassandraRepository<Meal,UUID> {
    public List<Meal> findAllByPlanningYearWeek(String planningYearWeek);
    @Query("SELECT m FROM meal_by_id WHERE m.meal_date > dateof(now()) ORDER BY m.meal_date")
    public List<Meal> findAllOrderByMealDateGreaterOrEqualToday();
    public List<Meal> findAllOrderByMealDateDesc();
}
