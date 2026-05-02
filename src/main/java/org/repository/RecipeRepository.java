package org.repository;

import org.model.entity.Recipe;
import org.model.enums.DietType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RecipeRepository extends JpaRepository<Recipe, String> {

    List<Recipe> findByMealType(String mealType);

    List<Recipe> findByDifficultyLevel(String difficultyLevel);

    List<Recipe> findByDietTypesContaining(DietType dietType);
}
