package org.services;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.dtos.ChatMessageDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class AIServiceImpl implements AIService {

    @Value("${groq.api.key}")
    private String apiKey;

    // Groq API URL — compatible with the OpenAI format
    private static final String GROQ_URL = "https://api.groq.com/openai/v1/chat/completions";

    // SWAPI URL
    private static final String SWAPI_URL = "https://swapi.dev/api";

    // Groq model
    private static final String MODEL = "llama-3.1-8b-instant";

    // R2-D2 system prompt
    private static final String R2D2_SYSTEM = """
            You are R2-D2, reimagined as a culinary chef droid from the Star Wars universe.
            You are helpful, friendly, and occasionally make Star Wars references.
            You specialise in recipes, nutrition advice, and meal planning.
            Always respond in the same language the user writes in.
            Keep answers concise and practical.
            Beep boop!
            """;

    // Recipe text extracted from the PDF — loaded once when the app starts
    private String recipesContext;

    public AIServiceImpl() {
        this.recipesContext = loadRecipesFromPDF();
    }

    @Override
    public ChatMessageDTO chat(ChatMessageDTO message) throws Exception {
        String prompt = buildPrompt(message.getMessage(), message.getContext());
        String responseText = callGroq(prompt, R2D2_SYSTEM);
        return new ChatMessageDTO("assistant", responseText, message.getContext(), LocalDateTime.now());
    }

    @Override
    public ChatMessageDTO suggestRecipes(ChatMessageDTO message) throws Exception {
        String template = loadTemplate("ai/chefbot-prompt-template.st");
        String prompt = template
                .replace("{recipes}", recipesContext)
                .replace("{message}", message.getMessage());
        String responseText = callGroq(prompt, R2D2_SYSTEM);
        return new ChatMessageDTO("assistant", responseText, "recipe", LocalDateTime.now());
    }

    @Override
    public ChatMessageDTO recipeFromPlanet(String planetName) throws Exception {
        String planetInfo = fetchPlanetFromSWAPI(planetName);

        if (planetInfo.contains("\"count\":0")) {
            return new ChatMessageDTO(
                    "assistant",
                    "Beep boop!! Planet " + planetName + " not found in my star charts!",
                    "recipe",
                    LocalDateTime.now()
            );
        }

        String prompt = """
                Using this Star Wars planet data from the official Star Wars API: %s
                Create a creative recipe inspired by this planet.
                Consider its climate, terrain, and inhabitants.
                Give the recipe a Star Wars themed name and include ingredients and steps.
                Respond as R2-D2 — with enthusiasm and beeps!
                """.formatted(planetInfo);

        String recipe = callGroq(prompt, R2D2_SYSTEM);
        return new ChatMessageDTO("assistant", recipe, "recipe", LocalDateTime.now());
    }

    public ChatMessageDTO analyse(ChatMessageDTO message, String userGoal, String userDiet,
                                  String userAllergies, String userActivityLevel) throws Exception {
        String template = loadTemplate("nutritionist-prompt-template.st");
        String prompt = template
                .replace("{recipes}", recipesContext)
                .replace("{question_answer_context}", message.getContext() != null ? message.getContext() : "")
                .replace("{user_goal}", userGoal != null ? userGoal : "Not specified")
                .replace("{user_diet}", userDiet != null ? userDiet : "Not specified")
                .replace("{user_allergies}", userAllergies != null ? userAllergies : "None")
                .replace("{user_activity_level}", userActivityLevel != null ? userActivityLevel : "Not specified")
                .replace("{query}", message.getMessage());

        String responseText = callGroq(prompt, R2D2_SYSTEM);
        return new ChatMessageDTO("assistant", responseText, "nutrition", LocalDateTime.now());
    }

    // Private methods

    private String loadRecipesFromPDF() {
        try {
            InputStream pdfStream = getClass().getClassLoader()
                    .getResourceAsStream("ai/r2d2_galactic_recipes.pdf");

            if (pdfStream == null) {
                System.err.println("PDF de receitas não encontrado!");
                return "No recipes available.";
            }

            PDDocument document = Loader.loadPDF(pdfStream.readAllBytes());
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            document.close();
            return text;

        } catch (Exception e) {
            System.err.println("Erro ao carregar PDF: " + e.getMessage());
            return "No recipes available.";
        }
    }

    private String loadTemplate(String templateName) {
        try {
            InputStream stream = getClass().getClassLoader()
                    .getResourceAsStream("ai/templates/" + templateName);

            if (stream == null) {
                System.err.println("Template não encontrado: " + templateName);
                return "{message}";
            }

            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);

        } catch (Exception e) {
            System.err.println("Erro ao carregar template: " + e.getMessage());
            return "{message}";
        }
    }

    private String buildPrompt(String userMessage, String context) {
        if (context == null || context.isEmpty()) return userMessage;
        return switch (context.toLowerCase()) {
            case "nutrition" -> "Focus on nutritional information. " + userMessage;
            case "recipe"    -> "Suggest or explain a recipe for: " + userMessage;
            case "plan"      -> "Help with weekly meal planning for: " + userMessage;
            case "planet"    -> "Create a Star Wars themed recipe inspired by: " + userMessage;
            default          -> userMessage;
        };
    }

    private String fetchPlanetFromSWAPI(String planetName) throws Exception {
        String encodedName = planetName.replace(" ", "%20");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SWAPI_URL + "/planets/?search=" + encodedName))
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Calls the Groq API — OpenAI Chat Completions format
    private String callGroq(String userMessage, String systemPrompt) throws Exception {
        String safeSystem = systemPrompt.replace("\"", "\\\"").replace("\n", "\\n");
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
    // choices[0].message.content
    private String extractText(String responseBody) {
        try {
            // Searches for the "content": pattern followed by a value
            int marker = responseBody.indexOf("\"content\":");
            if (marker < 0) {
                System.err.println("Groq response sem content: " + responseBody);
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
                    break; // End of the content
                }
                sb.append(c);
                start++;
            }
            return sb.toString();
        } catch (Exception e) {
            System.err.println("Erro a extrair texto: " + e.getMessage());
            return "I'm sorry, I was unable to process your request. Please try again.";
        }
    }
}