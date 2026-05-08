package org.services;

import org.dtos.NutritionDTO;
import org.dtos.RecipeDTO;
import org.dtos.ChatMessageDTO;
import org.exceptions.UserNotFoundException;

import java.util.List;

public interface NutritionService {

    // Returns the nutritional information of a specific recipe
    RecipeDTO getNutritionByRecipe(int recipeId);

    // Calculates the total calories/macros of several recipes
    RecipeDTO getTotalNutrition(List<Integer> recipeIds);

    // Filters recipes below X calories
    List<RecipeDTO> getRecipesBelowCalories(int maxCalories);

    // Analyses food items using AI — used by NutritionController
    ChatMessageDTO analyse(ChatMessageDTO message) throws Exception;

    // Returns the user's nutritional profile — used by NutritionController
    NutritionDTO findByUser(int userId) throws UserNotFoundException;

    // Updates the user's nutritional profile — used by NutritionController
    NutritionDTO update(int userId, NutritionDTO nutritionDTO) throws UserNotFoundException;
}