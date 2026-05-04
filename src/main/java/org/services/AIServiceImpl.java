package org.services;

import org.dtos.ChatMessageDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

@Service
public class AIServiceImpl implements AIService {

    @Value("${anthropic.api.key}")
    private String apiKey;

    // URL da API da Anthropic
    private static final String ANTHROPIC_URL = "https://api.anthropic.com/v1/messages";

    // URL da SWAPI
    private static final String SWAPI_URL = "https://swapi.dev/api";

    // Modelo da Anthropic
    private static final String MODEL = "claude-sonnet-4-20250514";

    // Personalidade do R2-D2 ChefBot
    private static final String SYSTEM_PROMPT = """
            You are R2-D2, reimagined as a culinary chef droid from the Star Wars universe.
            You are helpful, friendly, and occasionally make Star Wars references.
            You specialise in recipes, nutrition advice, and meal planning.
            Always respond in the same language the user writes in.
            Keep answers concise and practical.
            """;

    // chat — responde a uma mensagem geral
    @Override
    public ChatMessageDTO chat(ChatMessageDTO message) throws Exception{

        // Usamos o contexto para enriquecer a pergunta se existir
        String prompt = buildPrompt(message.getMessage(), message.getContext());

        // Chamamos a Anthropic e obtemos a resposta
        String responseText = callAnthropic(prompt);

        // Devolvemos a resposta com role "assistant"
        return new ChatMessageDTO("assistant", responseText, message.getContext(), LocalDateTime.now());
    }

    // suggestRecipes — sugestão de receitas
    @Override
    public ChatMessageDTO suggestRecipes(ChatMessageDTO message) throws Exception{
        //Prompt para sugestão de receitas
        String prompt = "Based on the following request, suggest 2-3 recipes with a brief description: "
                + message.getMessage();

        String responseText = callAnthropic(prompt);

        return new ChatMessageDTO("assistant", responseText, "recipe", LocalDateTime.now());
    }

    // recipeFromPlanet — combina SWAPI + Anthropic!
    @Override
    public ChatMessageDTO recipeFromPlanet(String planetName) throws Exception{
        //Vamos procurar info sobre o planeta
        String planetInfo = fetchPlanetFromSWAPI(planetName);

        //Se o planeta não existir
        if(planetInfo.contains("\"count\":0")){
            return new ChatMessageDTO(
                    "assistant",
                    "Beeo boop!! Planet" + planetName +" not found in my star charts!",
                    "recipe",
                    LocalDateTime.now()
            );
        }

        // Usar info do planeta para criar um prompt rico
        String prompt = """
                Using this Star Wars planet data from the official Star Wars API: %s
                Create a creative recipe inspired by this planet.
                Consider its climate, terrain, and inhabitants.
                Give the recipe a Star Wars themed name and include ingredients and steps.
                """.formatted(planetInfo);
        //Anthropic gera a receita temática com base na info do planeta
        String recipe = callAnthropic(prompt);

        return new ChatMessageDTO("assistant", recipe, "recipe", LocalDateTime.now());
    }

    // MÉTODOS PRIVADOS
    // Constrói o prompt com contexto opcional
    private String buildPrompt(String userMessage, String context) {
        if (context == null || context.isEmpty()) {
            return userMessage;
        }

        return switch (context.toLowerCase()) {
            case "nutrition" -> "Focus on nutritional information. " + userMessage;
            case "recipe" -> "Suggest or explain a recipe for: " + userMessage;
            case "plan" -> "Help with weekly meal planning for: " + userMessage;
            case "planet" -> "Create a Star Wars themed recipe inspired by: " + userMessage;
            default -> userMessage;
        };
    }
    // Procura informação de um planeta na SWAPI
    // Ex: fetchPlanetFromSWAPI("Tatooine") → JSON com clima, terreno, população, etc.
    private String fetchPlanetFromSWAPI(String planetName) throws Exception {
        // Substituímos espaços por %20 para o URL funcionar correctamente
        String encodedName = planetName.replace(" ", "%20");

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SWAPI_URL + "/planets/?search=" + encodedName))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    // Faz a chamada HTTP à API da Anthropic e devolve o texto da resposta
    private String callAnthropic(String userMessage) throws Exception {
        String requestBody = """
                {
                  "model": "%s",
                  "max_tokens": 1024,
                  "system": "%s",
                  "messages": [
                    {"role": "user", "content": "%s"}
                  ]
                }
                """.formatted(
                MODEL,
                SYSTEM_PROMPT.replace("\"", "\\\"").replace("\n", "\\n"),
                userMessage.replace("\"", "\\\"").replace("\n", "\\n")
        );

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ANTHROPIC_URL))
                .header("Content-Type", "application/json")
                .header("x-api-key", apiKey)
                .header("anthropic-version", "2023-06-01")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        return extractText(response.body());
    }

    // Extrai o texto da resposta JSON da Anthropic
    private String extractText(String responseBody) {
        int start = responseBody.indexOf("\"text\":\"") + 8;
        int end = responseBody.indexOf("\"", start);

        if (start < 8 || end < 0) {
            return "Beep boop... R2-D2 short-circuited! Please try again.";
        }

        return responseBody.substring(start, end).replace("\\n", "\n");
    }
}
