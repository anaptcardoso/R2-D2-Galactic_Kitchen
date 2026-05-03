package org.services;

import org.dtos.RecipeDTO;
import org.dtos.ChatMessageDTO;

import java.util.List;

public interface NutritionService {

    // Devolve a info nutricional de uma receita específica
    RecipeDTO getNutritionByRecipe(int recipeId);

    // Calcula o total de calorias/macros de várias receitas
    RecipeDTO getTotalNutrition(List<Integer> recipeIds);

    // Filtra receitas abaixo de X calorias
    List<RecipeDTO> getRecipesBelowCalories(int maxCalories);

    // Analisa alimentos via AI — usado pelo NutritionController
    ChatMessageDTO analyse(ChatMessageDTO message) throws Exception;

    // Devolve perfil nutricional do utilizador — usado pelo NutritionController
    RecipeDTO findByUser(int userId);

    // Atualiza perfil nutricional do utilizador — usado pelo NutritionController
    RecipeDTO update(int userId, RecipeDTO recipeDTO);
}