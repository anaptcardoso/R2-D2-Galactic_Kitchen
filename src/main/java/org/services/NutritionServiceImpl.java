package org.services;

import org.dtos.ChatMessageDTO;
import org.dtos.IngredientDTO;
import org.dtos.NutritionDTO;
import org.dtos.RecipeDTO;
import org.exceptions.UserNotFoundException;
import org.model.entity.Recipe;
import org.model.entity.UserProfile;
import org.model.valueObject.NutritionProfile;
import org.persistence.daos.RecipeRepository;
import org.persistence.daos.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class NutritionServiceImpl implements NutritionService {

    // Precisamos do RecipeRepository para buscar receitas
    // e do UserProfileRepository para buscar utilizadores
    private final RecipeRepository recipeRepository;
    private final UserProfileRepository userProfileRepository;
    private final AIService aiService;

    public NutritionServiceImpl(RecipeRepository recipeRepository,
                                UserProfileRepository userProfileRepository,
                                AIService aiService) {
        this.recipeRepository = recipeRepository;
        this.userProfileRepository = userProfileRepository;
        this.aiService = aiService;
    }


    // getNutritionByRecipe — info nutricional de uma receita
    @Override
    public RecipeDTO getNutritionByRecipe(int recipeId) {
        // Buscamos a receita pelo ID
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found: " + recipeId));
        // Devolvemos o RecipeDTO completo — já tem calories, protein, carbs, fat
        return toDTO(recipe);
    }


    // getTotalNutrition — soma os macros de várias receitas
    @Override
    public RecipeDTO getTotalNutrition(List<Integer> recipeIds) {
        // Buscamos todas as receitas de uma vez — 1 query à BD
        List<Recipe> recipes = recipeRepository.findAllById(recipeIds);

        // Somamos os valores nutricionais de todas as receitas
        int totalCalories = 0;
        double totalProtein = 0;
        double totalCarbs = 0;
        double totalFat = 0;

        for (Recipe recipe : recipes) {
            totalCalories += recipe.getCalories(); // ← directo, sem getNutrition()
            totalProtein  += recipe.getProtein();
            totalCarbs    += recipe.getCarbs();
            totalFat      += recipe.getFat();
        }

        // Devolvemos um RecipeDTO com os totais
        RecipeDTO total = new RecipeDTO();
        total.setName("Weekly Total");
        total.setCalories(totalCalories);
        total.setProtein(totalProtein);
        total.setCarbs(totalCarbs);
        total.setFat(totalFat);
        return total;
    }


    // getRecipesBelowCalories — filtra por limite de calorias
    @Override
    public List<RecipeDTO> getRecipesBelowCalories(int maxCalories) {
        return recipeRepository.findAll()
                .stream()
                // filtra só as receitas com calorias abaixo do limite
                .filter(r -> r.getCalories() <= maxCalories)
                .map(this::toDTO)
                .toList();
    }


    // analyse — analisa alimentos via AI
    // Usa o AIService para perguntar ao Claude informações nutricionais
    @Override
    public ChatMessageDTO analyse(ChatMessageDTO message) throws Exception {
        // Construímos um prompt específico para análise nutricional
        String prompt = "Analyse the nutritional value of: " + message.getMessage()
                + ". Include calories, protein, carbs and fat per serving.";

        // Criamos uma nova mensagem com contexto "nutrition" e enviamos ao Claude
        ChatMessageDTO nutritionMessage = new ChatMessageDTO(
                message.getRole(),
                prompt,
                "nutrition"
        );
        return aiService.chat(nutritionMessage);
    }


    // findByUser — devolve o perfil nutricional do utilizador
    @Override
    public NutritionDTO findByUser(int userId) throws UserNotFoundException {
        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        NutritionProfile nutrition = user.getNutritionProfile();
        if (nutrition == null) {
            return new NutritionDTO(null, null, null, null, null, null);
        }

        return new NutritionDTO(
                nutrition.getWeight(),
                nutrition.getHeight(),
                nutrition.getGoal(),
                nutrition.getActivityLevel(),
                nutrition.getDietPreferences(),
                nutrition.getAllergies()
        );

    }


    // update — actualiza o perfil nutricional do utilizador

    @Override
    public NutritionDTO update(int userId, NutritionDTO nutritionDTO) throws UserNotFoundException {
        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        NutritionProfile nutrition = user.getNutritionProfile();
        if (nutrition == null) {
            nutrition = new NutritionProfile();
            user.setNutritionProfile(nutrition);
        }

        nutrition.setWeight(nutritionDTO.getWeight());
        nutrition.setHeight(nutritionDTO.getHeight());
        nutrition.setGoal(nutritionDTO.getGoal());
        nutrition.setActivityLevel(nutritionDTO.getActivityLevel());
        nutrition.setDietPreferences(nutritionDTO.getDietPreferences());
        nutrition.setAllergies(nutritionDTO.getAllergies());

        userProfileRepository.save(user);
        return nutritionDTO;

    }


    // Converte entidade Recipe → RecipeDTO
    private RecipeDTO toDTO(Recipe recipe) {
        RecipeDTO dto = new RecipeDTO();
        dto.setId(recipe.getId());
        dto.setName(recipe.getName());
        dto.setDescription(recipe.getDescription());
        dto.setCategory(recipe.getCategory());
        dto.setPreparationTime(recipe.getPreparationTime());
        dto.setServings(recipe.getServings());
        dto.setCalories(recipe.getCalories());
        dto.setProtein(recipe.getProtein());
        dto.setCarbs(recipe.getCarbs());
        dto.setFat(recipe.getFat());
        dto.setDifficultyLevel(recipe.getDifficultyLevel());
        dto.setMealType(recipe.getMealType());
        dto.setDietTypes(recipe.getDietTypes());
        dto.setTip(recipe.getTip());

        // steps — converte String para List<String>
        if (recipe.getSteps() != null) {
            dto.setSteps(Arrays.asList(recipe.getSteps().split("\n")));
        }

        // ingredientes
        dto.setIngredients(
                recipe.getIngredients()
                        .stream()
                        .map(i -> new IngredientDTO(i.getId(), i.getName(), i.getQuantity(), i.getUnit()))
                        .toList()
        );
        return dto;
    }
}