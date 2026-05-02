package org.services;

import org.dtos.ChatMessageDTO;

public interface AIService {

    // Responde a uma mensagem geral do utilizador - chat normal com o R2-D2 ChefBot
    ChatMessageDTO chat(ChatMessageDTO message) throws Exception;

    // Sugere receitas com base nos ingredientes ou preferências do utilizador
    ChatMessageDTO suggestRecipes(ChatMessageDTO message) throws Exception;

    // Cria receita inspirada num planeta Star Wars
    ChatMessageDTO recipeFromPlanet(String planetName) throws Exception;
}
