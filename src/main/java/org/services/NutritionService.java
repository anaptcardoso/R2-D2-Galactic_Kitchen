package org.services;

import org.dtos.NutritionDTO;

import java.util.List;

public interface NutritionService {

    // Devolve a info nutricional de uma receita específica
    NutritionDTO getNutritionByRecipe(int recipeId);

    // Calcula o total nutricional de várias receitas (ex: plano semanal)
    NutritionDTO getTotalNutrition(List<Integer> recipeIds);

    // Filtra receitas que tenham menos do que X calorias
    List<NutritionDTO> getRecipesBelowCalories(int maxCalories);
}