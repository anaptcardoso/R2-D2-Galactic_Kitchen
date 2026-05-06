package org.services;

import org.dtos.ChatMessageDTO;
import org.dtos.NutritionDTO;
import org.exceptions.RecipeNotFoundException;
import org.exceptions.UserNotFoundException;
import org.model.entity.Recipe;
import org.model.entity.UserProfile;
import org.model.valueObject.NutritionProfile;
import org.persistence.daos.RecipeRepository;
import org.persistence.daos.UserProfileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

@Service
public class NutritionistServiceImpl implements NutritionistService{

    @Value("${anthropic.api.key}")
    private String apiKey;

    private final UserProfileRepository userProfileRepository;
    private final RecipeRepository recipeRepository;

    // URL e modelo Anthropic
    private static final String ANTHROPIC_URL = "https://api.anthropic.com/v1/messages";
    private static final String MODEL = "claude-sonnet-4-20250514";

    // Personalidade do nutricionista
    private static final String SYSTEM_PROMPT = """
            You are a professional nutritionist and dietitian with expertise in personalised nutrition.
            You are empathetic, evidence-based, and always prioritise the user's health and wellbeing.
            You specialise in nutritional analysis, dietary planning, weight management, and healthy eating.
            You provide practical, science-backed advice tailored to the user's goals and preferences.
            Always respond in the same language the user writes in.
            Never diagnose medical conditions — always recommend consulting a doctor for medical concerns.
            Be encouraging and supportive, never judgemental.
            """;

    public NutritionistServiceImpl(UserProfileRepository userProfileRepository, RecipeRepository recipeRepository) {
        this.userProfileRepository = userProfileRepository;
        this.recipeRepository = recipeRepository;
    }

    // consult — consulta personalizada com perfil do utilizador
    @Override
    public ChatMessageDTO consult(ChatMessageDTO message, int userId) throws Exception {

        //Procura o perfil nutricional do utilizador para personalizar a resposta
        String userContext = buildUserContext(userId);

        // Constrói o prompt com o contexto do utilizador
        String prompt = userContext + "\n\nUser question: " + message.getMessage();

        String responseText = callAnthropic(prompt);


        return new ChatMessageDTO("assistant", responseText, "nutrition", LocalDateTime.now());
    }


    // analyseFood — analisa valores nutricionais de alimentos
    @Override
    public ChatMessageDTO analyseFood(ChatMessageDTO message) throws Exception {
        String prompt = """
                Analyse the nutritional value of the following food or meal: %s
                
                Please provide:
                - Estimated calories per serving
                - Macronutrients (protein, carbohydrates, fat) in grams
                - Key micronutrients
                - Health benefits
                - Any nutritional concerns
                """.formatted(message.getMessage());

        String responseText = callAnthropic(prompt);

        return new ChatMessageDTO("assistant", responseText, "nutrition", LocalDateTime.now());

    }

    // suggestMealPlan — plano alimentar personalizado
    @Override
    public ChatMessageDTO suggestMealPlan(NutritionDTO nutritionProfile) throws Exception {
        String prompt = """
                Create a personalised weekly meal plan for a user with the following profile:
                - Goal: %s
                - Activity level: %s
                - Diet type: %s
                - Allergies/intolerances: %s
                - Weight: %s kg
                - Height: %s cm
                
                Include breakfast, lunch, dinner and snacks for each day.
                Make it practical, balanced and aligned with the user's goals.
                """.formatted(
                nutritionProfile.getGoal(),
                nutritionProfile.getActivityLevel(),
                nutritionProfile.getDietPreferences() != null ? nutritionProfile.getDietPreferences().toString() : "No preference",
                nutritionProfile.getAllergies() != null ? nutritionProfile.getAllergies() : "None",
                nutritionProfile.getWeight(),
                nutritionProfile.getHeight()
        );

        String responseText = callAnthropic(prompt);

        return new ChatMessageDTO("assistant", responseText, "nutrition", LocalDateTime.now());

    }

    // evaluateRecipe — avalia se uma receita é adequada para o utilizador
    @Override
    public ChatMessageDTO evaluateRecipe(int recipeId, int userId) throws Exception {

        //Procura a receita
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(RecipeNotFoundException::new);

        //Procura o perfil do utilizador
        String userContext = buildUserContext(userId);

        String prompt = """
                %s
                
                Please evaluate if the following recipe is suitable for this user:
                
                Recipe: %s
                Calories: %d kcal
                Protein: %.1f g
                Carbs: %.1f g
                Fat: %.1f g
                Diet types: %s
                
                Provide:
                - Suitability assessment (yes/no/with modifications)
                - Nutritional highlights
                - Any concerns based on the user's profile
                - Suggested modifications if needed
                """.formatted(
                userContext,
                recipe.getName(),
                recipe.getCalories(),
                recipe.getProtein(),
                recipe.getCarbs(),
                recipe.getFat(),
                recipe.getDietTypes() != null ? recipe.getDietTypes().toString() : "Not specified"
        );

        String responseText = callAnthropic(prompt);

        return new ChatMessageDTO("assistant", responseText, "nutrition", LocalDateTime.now());

    }

    // Constrói o contexto do utilizador para personalizar as respostas
    private String buildUserContext(int userId) {
        try {
            UserProfile user = userProfileRepository.findById(userId)
                    .orElseThrow(UserNotFoundException::new);

            NutritionProfile nutrition = user.getNutritionProfile();

            if (nutrition == null) {
                return "User profile: No nutritional data available.";
            }

            return """
                    User profile:
                    - Name: %s %s
                    - Goal: %s
                    - Activity level: %s
                    - Diet preferences: %s
                    - Allergies: %s
                    - Weight: %s kg
                    - Height: %s cm
                    """.formatted(
                    user.getFirstName(),
                    user.getLastName(),
                    nutrition.getGoal() != null ? nutrition.getGoal() : "Not specified",
                    nutrition.getActivityLevel() != null ? nutrition.getActivityLevel() : "Not specified",
                    nutrition.getDietPreferences() != null ? nutrition.getDietPreferences().toString() : "No preference",
                    nutrition.getAllergies() != null ? nutrition.getAllergies() : "None",
                    nutrition.getWeight() != null ? nutrition.getWeight().toString() : "Not specified",
                    nutrition.getHeight() != null ? nutrition.getHeight().toString() : "Not specified"
            );

        } catch (Exception e) {
            return "User profile: Unable to load user data.";
        }
    }

    // Chamada HTTP à API da Anthropic
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
            return "I'm sorry, I was unable to process your request. Please try again.";
        }

        return responseBody.substring(start, end).replace("\\n", "\n");
    }

}
