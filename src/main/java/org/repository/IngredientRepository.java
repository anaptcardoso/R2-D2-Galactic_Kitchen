package org.repository;

import org.model.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, String> {

    // Buscar ingredientes de uma receita específica
    List<Ingredient> findByRecipeId(String recipeId);

    // Buscar ingredientes por nome
    List<Ingredient> findByNameContainingIgnoreCase(String name);
}
