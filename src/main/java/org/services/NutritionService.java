package org.services;

import org.dtos.NutritionDTO;
import org.dtos.RecipeDTO;
import org.dtos.ChatMessageDTO;
import org.exceptions.UserNotFoundException;

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
    NutritionDTO findByUser(int userId) throws UserNotFoundException;

    // Atualiza perfil nutricional do utilizador — usado pelo NutritionController
    NutritionDTO update(int userId, NutritionDTO nutritionDTO) throws UserNotFoundException;
}