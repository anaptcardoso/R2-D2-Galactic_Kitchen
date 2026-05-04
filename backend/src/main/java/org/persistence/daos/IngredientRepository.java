package org.persistence.daos;

import org.model.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    // Procurar ingredientes de uma receita específica
    List<Ingredient> findByRecipeId(int recipeId);

    // Procurar ingredientes por nome
    List<Ingredient> findByNameContainingIgnoreCase(String name);
}
