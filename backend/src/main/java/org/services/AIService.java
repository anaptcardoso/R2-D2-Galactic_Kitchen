package org.services;

import org.dtos.ChatMessageDTO;

public interface AIService {

    // Responds to a general user message — normal chat with the R2-D2 ChefBot
    ChatMessageDTO chat(ChatMessageDTO message) throws Exception;

    // Suggests recipes based on the user's ingredients or preferences
    ChatMessageDTO suggestRecipes(ChatMessageDTO message) throws Exception;

    // Creates a recipe inspired by a Star Wars planet
    ChatMessageDTO recipeFromPlanet(String planetName) throws Exception;

    // Analyses a nutritional question using the user's profile
    ChatMessageDTO analyse(ChatMessageDTO message, String userGoal, String userDiet,
                           String userAllergies, String userActivityLevel) throws Exception;
}
