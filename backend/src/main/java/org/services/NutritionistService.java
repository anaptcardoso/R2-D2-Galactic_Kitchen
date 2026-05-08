package org.services;

import org.dtos.ChatMessageDTO;
import org.dtos.NutritionDTO;

public interface NutritionistService {

    // General consultation with the nutritionist — personalized response using the user's profile
    ChatMessageDTO consult(ChatMessageDTO message, int userId) throws Exception;

    // Analyses food items and returns detailed nutritional information
    ChatMessageDTO analyseFood(ChatMessageDTO message) throws Exception;

    // Suggests a personalized meal plan based on the user's profile
    ChatMessageDTO suggestMealPlan(NutritionDTO nutritionProfile) throws Exception;

    // Evaluates whether a recipe is suitable for the user's nutritional profile
    ChatMessageDTO evaluateRecipe(int recipeId, int userId) throws Exception;

}
