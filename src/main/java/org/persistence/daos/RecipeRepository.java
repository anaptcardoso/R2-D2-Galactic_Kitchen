package org.persistence.daos;

import org.model.entity.Recipe;
import org.model.enums.DietType;
import org.model.enums.DifficultyLevel;
import org.model.enums.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

     // Procurar receitas por tipo de refeição (ex: massas, sopas, ...)
   List<Recipe>findByCategory(String category);

    // Procurar receitas por nome (ignora maiúsculas/minúsculas)
    List<Recipe> findByNameContainingIgnoreCase(String name);

    // Procurar receitas por nível de dificuldade
    List<Recipe> findByDifficultyLevel(DifficultyLevel difficultyLevel);

    // Procurar receitas por tipo de refeição (ex: pequeno-almoço, almoço ...)
    List<Recipe> findByMealType(MealType mealType);

    // Procurar receitas por tipo de dieta
    List<Recipe> findByDietTypesContaining(DietType dietType);

    // Procurar receitas com tempo de preparação menor ou igual ao indicado
    List<Recipe> findByPreparationTimeLessThanEqual(int maxTime);
}
