package org.services;

import org.dtos.ChatMessageDTO;
import org.dtos.NutritionDTO;

public interface NutritionistService {

    //Consulta geral com o nutricionista- resposta personaçizada com o perfil do utilizador
    ChatMessageDTO consult(ChatMessageDTO message, int userId) throws Exception;

    //Analisa alimentos e devolve informação nutricional detalhada
    ChatMessageDTO analyseFood(ChatMessageDTO message) throws Exception;

    //Sugere um plano alimentar personalizado com base no perfil do utilizador
    ChatMessageDTO suggestMealPlan(NutritionDTO nutritionProfile) throws Exception;

    //Avalia se uma receita é adequada para o perfil nutricional do utilizador
    ChatMessageDTO evaluateRecipe(int recipeId, int userId) throws Exception;

}
