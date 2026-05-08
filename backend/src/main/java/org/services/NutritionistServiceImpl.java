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
public class NutritionistServiceImpl implements NutritionistService {

    @Value("${groq.api.key}")
    private String apiKey;

    private final UserProfileRepository userProfileRepository;
    private final RecipeRepository recipeRepository;

    // Groq API URL — OpenAI format
    private static final String GROQ_URL = "https://api.groq.com/openai/v1/chat/completions";
    private static final String MODEL = "llama-3.1-8b-instant";

    // Nutritionist personality
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

    @Override
    public ChatMessageDTO consult(ChatMessageDTO message, int userId) throws Exception {
        String userContext = buildUserContext(userId);
        String prompt = userContext + "\n\nUser question: " + message.getMessage();
        String responseText = callGroq(prompt);
        return new ChatMessageDTO("assistant", responseText, "nutrition", LocalDateTime.now());
    }

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

        String responseText = callGroq(prompt);
        return new ChatMessageDTO("assistant", responseText, "nutrition", LocalDateTime.now());
    }

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

        String responseText = callGroq(prompt);
        return new ChatMessageDTO("assistant", responseText, "nutrition", LocalDateTime.now());
    }

    @Override
    public ChatMessageDTO evaluateRecipe(int recipeId, int userId) throws Exception {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(RecipeNotFoundException::new);

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

        String responseText = callGroq(prompt);
        return new ChatMessageDTO("assistant", responseText, "nutrition", LocalDateTime.now());
    }

    // Private methods

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

    // Calls the Groq API — OpenAI Chat Completions format
    private String callGroq(String userMessage) throws Exception {
        System.out.println("API KEY: [" + apiKey + "]");
        String safeSystem = SYSTEM_PROMPT.replace("\"", "\\\"").replace("\n", "\\n");
        String safeMessage = userMessage.replace("\"", "\\\"").replace("\n", "\\n");

        String requestBody = """
                {
                  "model": "%s",
                  "messages": [
                    {"role": "system", "content": "%s"},
                    {"role": "user",   "content": "%s"}
                  ],
                  "max_tokens": 1024,
                  "temperature": 0.7
                }
                """.formatted(MODEL, safeSystem, safeMessage);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GROQ_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("GROQ RESPONSE: " + response.body());

        return extractText(response.body());
    }

    // Extracts the text from the Groq JSON response (OpenAI format)
    private String extractText(String responseBody) {
        try {
            // Searches for the "content": pattern followed by a value
            int marker = responseBody.indexOf("\"content\":");
            if (marker < 0) {
                System.err.println("Groq response without content: " + responseBody);
                return "I'm sorry, I was unable to process your request. Please try again.";
            }

            // Moves forward to after "content":
            int start = marker + 10;
            // Skips spaces
            while (start < responseBody.length() && responseBody.charAt(start) == ' ') start++;
            // Skips the opening quotation mark
            if (responseBody.charAt(start) == '"') start++;

            // Rebuilds the text while respecting escape characters
            StringBuilder sb = new StringBuilder();
            while (start < responseBody.length()) {
                char c = responseBody.charAt(start);
                if (c == '\\' && start + 1 < responseBody.length()) {
                    char next = responseBody.charAt(start + 1);
                    if (next == '"') { sb.append('"'); start += 2; continue; }
                    if (next == 'n') { sb.append('\n'); start += 2; continue; }
                    if (next == '\\') { sb.append('\\'); start += 2; continue; }
                    if (next == 't') { sb.append('\t'); start += 2; continue; }
                } else if (c == '"') {
                    break; // end of the Content
                }
                sb.append(c);
                start++;
            }
            return sb.toString();
        } catch (Exception e) {
            System.err.println("Error extract text: " + e.getMessage());
            return "I'm sorry, I was unable to process your request. Please try again.";
        }
    }
}