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

    @Value("${anthropic.api.key}")
    private String apiKey;

    // URL da API da Anthropic
    private static final String ANTHROPIC_URL = "https://api.anthropic.com/v1/messages";

    // URL da SWAPI
    private static final String SWAPI_URL = "https://swapi.dev/api";

    // Modelo da Anthropic
    private static final String MODEL = "claude-sonnet-4-20250514";

    // Texto das receitas extraído do PDF — carregado uma só vez quando o serviço arranca
    private String recipesContext;

    // Construtor — lê o PDF e os templates quando o serviço é criado pelo Spring
    public AIServiceImpl() {
        this.recipesContext = loadRecipesFromPDF();
    }

    // chat — responde a uma mensagem geral com a personalidade do R2-D2
    @Override
    public ChatMessageDTO chat(ChatMessageDTO message) throws Exception {
        String prompt = buildPrompt(message.getMessage(), message.getContext());
        String responseText = callAnthropic(prompt, null);
        return new ChatMessageDTO("assistant", responseText, message.getContext(), LocalDateTime.now());
    }

    // suggestRecipes — usa o PDF de receitas para sugerir receitas reais
    // se não encontrar nenhuma adequada, o R2-D2 inventa uma nova
    @Override
    public ChatMessageDTO suggestRecipes(ChatMessageDTO message) throws Exception {
        // carrega o template do chefbot
        String template = loadTemplate("ai/chefbot-prompt-template.st");

        // preenche o template com as receitas do PDF e a mensagem do utilizador
        String prompt = template
                .replace("{recipes}", recipesContext)
                .replace("{message}", message.getMessage());

        String responseText = callAnthropic(prompt, null);
        return new ChatMessageDTO("assistant", responseText, "recipe", LocalDateTime.now());
    }

    // recipeFromPlanet — combina SWAPI + Anthropic para criar uma receita temática
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

        // usa a info do planeta para criar uma receita temática
        String prompt = """
                Using this Star Wars planet data from the official Star Wars API: %s
                Create a creative recipe inspired by this planet.
                Consider its climate, terrain, and inhabitants.
                Give the recipe a Star Wars themed name and include ingredients and steps.
                Respond as R2-D2 — with enthusiasm and beeps!
                """.formatted(planetInfo);

        String recipe = callAnthropic(prompt, null);
        return new ChatMessageDTO("assistant", recipe, "recipe", LocalDateTime.now());
    }

    // analyse — usado pelo NutritionService para análise nutricional
    // usa o template do nutricionista com o perfil do utilizador
    public ChatMessageDTO analyse(ChatMessageDTO message, String userGoal, String userDiet,
                                  String userAllergies, String userActivityLevel) throws Exception {
        // carrega o template do nutricionista
        String template = loadTemplate("nutritionist-prompt-template.st");

        // preenche todos os campos do template
        String prompt = template
                .replace("{recipes}", recipesContext)
                .replace("{question_answer_context}", message.getContext() != null ? message.getContext() : "")
                .replace("{user_goal}", userGoal != null ? userGoal : "Not specified")
                .replace("{user_diet}", userDiet != null ? userDiet : "Not specified")
                .replace("{user_allergies}", userAllergies != null ? userAllergies : "None")
                .replace("{user_activity_level}", userActivityLevel != null ? userActivityLevel : "Not specified")
                .replace("{query}", message.getMessage());

        String responseText = callAnthropic(prompt, null);
        return new ChatMessageDTO("assistant", responseText, "nutrition", LocalDateTime.now());
    }

    // MÉTODOS PRIVADOS ─────────────────────────────────────────────────────────

    // lê o PDF de receitas e extrai o texto
    // o PDF está em src/main/resources/ai/r2d2_galactic_recipes.pdf
    private String loadRecipesFromPDF() {
        try {
            InputStream pdfStream = getClass().getClassLoader()
                    .getResourceAsStream("ai/r2d2_galactic_recipes.pdf");

            if (pdfStream == null) {
                System.err.println("PDF de receitas não encontrado!");
                return "No recipes available.";
            }

            // usa o pdfbox para extrair o texto do PDF
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

    // lê um ficheiro de template da pasta resources/ai/templates/
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

    // constrói o prompt com contexto opcional para o chat geral
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

    // procura informação de um planeta na SWAPI
    private String fetchPlanetFromSWAPI(String planetName) throws Exception {
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

    // faz a chamada HTTP à API da Anthropic e devolve o texto da resposta
    // o systemPrompt é opcional — se for null usa o prompt padrão do R2-D2
    private String callAnthropic(String userMessage, String systemPrompt) throws Exception {
        // se não vier um system prompt específico usa o do R2-D2
        String system = systemPrompt != null ? systemPrompt : """
                You are R2-D2, reimagined as a culinary chef droid from the Star Wars universe.
                You are helpful, friendly, and occasionally make Star Wars references.
                You specialise in recipes, nutrition advice, and meal planning.
                Always respond in the same language the user writes in.
                Keep answers concise and practical.
                Beep boop!
                """;

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
                system.replace("\"", "\\\"").replace("\n", "\\n"),
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

    // extrai o texto da resposta JSON da Anthropic
    private String extractText(String responseBody) {
        int start = responseBody.indexOf("\"text\":\"") + 8;
        int end = responseBody.indexOf("\"", start);

        if (start < 8 || end < 0) {
            return "Beep boop... R2-D2 short-circuited! Please try again.";
        }

        return responseBody.substring(start, end).replace("\\n", "\n");
    }
}
