package org.services;

import org.dtos.PlanDTO;
import org.dtos.RecipeDTO;
import org.model.entity.Recipe;
import org.model.entity.UserProfile;
import org.model.entity.WeeklyPlan;
import org.repository.RecipeRepository;
import org.repository.UserProfileRepository;
import org.repository.WeeklyPlanRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlanServiceImpl implements PlanService {

    private final WeeklyPlanRepository weeklyPlanRepository;
    private final RecipeRepository recipeRepository;
    private final UserProfileRepository userProfileRepository;

    public PlanServiceImpl(WeeklyPlanRepository weeklyPlanRepository,
                           RecipeRepository recipeRepository,
                           UserProfileRepository userProfileRepository) {
        this.weeklyPlanRepository = weeklyPlanRepository;
        this.recipeRepository = recipeRepository;
        this.userProfileRepository = userProfileRepository;
    }

    // findByUser - todos os planos de um utilizador
    @Override
    public List<PlanDTO> findByUser(int userId) {
        return weeklyPlanRepository.findByUserId(userId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // findByUserAndWeek — plano de uma semana específica
    @Override
    public PlanDTO findByUserAndWeek(int userId, String weekStart) {
        LocalDate date = LocalDate.parse(weekStart); // "2025-01-06" → LocalDate
        WeeklyPlan plan = weeklyPlanRepository.findByUserIdAndWeekStart(userId, date)
                .orElseThrow(() -> new RuntimeException("Plan not found for user " + userId));
        return toDTO(plan);
    }

    // create — cria um plano novo
    @Transactional
    @Override
    public PlanDTO create(PlanDTO planDTO){
        //Ver se o user existe
        UserProfile user = userProfileRepository.findById(planDTO.getUserId())
                .orElseThrow(()-> new RuntimeException("User not found" + planDTO.getUserId()));

        WeeklyPlan plan = new WeeklyPlan();
        plan.setUser(user);
        plan.setWeekStart(planDTO.getWeekStart());
        plan.setWeekEnd(planDTO.getWeekEnd());
        plan.setRecipes(new ArrayList<>()); // começa vazio, receitas adicionam-se depois

        WeeklyPlan saved = weeklyPlanRepository.save(plan);
        return toDTO(saved);
    }

    // addRecipe — adiciona uma receita ao plano
    @Transactional
    @Override
    public PlanDTO addRecipe(int planId, int recipeId){
        WeeklyPlan plan = weeklyPlanRepository.findById(planId)
                .orElseThrow(()-> new RuntimeException(("Plan not found" + planId));

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(()-> new RuntimeException("Recipe not found" + recipeId));

        plan.getRecipes().add(recipe);
        WeeklyPlan updated = weeklyPlanRepository.save(plan);
        return toDTO(updated);
    }

    // removeRecipe — remove uma receita do plano
    @Transactional
    @Override
    public PlanDTO removeRecipe(int planId, int recipeId) {
        WeeklyPlan plan = weeklyPlanRepository.findById(planId)
                .orElseThrow(()-> new RuntimeException("Plan not found" + planId));

        plan.getRecipes().removeIf(r-> r.getId() == recipeId);
        WeeklyPlan updated = weeklyPlanRepository.save(plan);
        return toDTO(updated);
    }

    // delete — apaga um plano
    @Transactional
    @Override
    public void delete(int planId){
        if(!weeklyPlanRepository.existsById(planId)){
            throw  new RuntimeException("Plan not found" + planId);
        }
        weeklyPlanRepository.deleteById(planId);
    }

    private PlanDTO toDTO(WeeklyPlan plan) {
        PlanDTO dto = new PlanDTO();
        dto.setId(plan.getId());
        dto.setUserId(plan.getUser().getId());
        dto.setWeekStart(plan.getWeekStart());
        dto.setWeekEnd(plan.getWeekEnd());

        // Organiza as receitas por dia da semana num Map
        // Chave: "MONDAY", "TUESDAY", etc.
        // Valor: lista de RecipeDTOs desse dia
        Map<String, List<RecipeDTO>> meals = new LinkedHashMap<>();
        String[] days = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};

        for (String day : days) {
            meals.put(day, new ArrayList<>()); // começa vazio para cada dia
        }

        // Distribui as receitas pelos dias (uma por dia, por ordem)
        List<Recipe> recipes = plan.getRecipes();
        for (int i = 0; i < recipes.size() && i < days.length; i++) {
            meals.get(days[i]).add(recipeToDTO(recipes.get(i)));
        }

        dto.setMeals(meals);
        dto.setShoppingList(generateShoppingList(plan.getRecipes()));
        return dto;
    }

    // Gera a lista de compras a partir das receitas do plano
    private List<String> generateShoppingList(List<Recipe> recipes) {
        Map<String, Double> aggregated = new LinkedHashMap<>();

        for (Recipe recipe : recipes) {
            recipe.getIngredients().forEach(ingredient -> {
                String key = ingredient.getName() + " (" + ingredient.getUnit() + ")";
                aggregated.merge(key, ingredient.getQuantity(), Double::sum);
            });
        }

        // Converte o Map para List<String> — ex: "flour (g): 350.0"
        return aggregated.entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .toList();
    }

    // Converte Recipe → RecipeDTO (reutiliza a lógica do RecipeService)
    private RecipeDTO recipeToDTO(Recipe recipe) {
        RecipeDTO dto = new RecipeDTO();
        dto.setId(recipe.getId());
        dto.setName(recipe.getName());
        dto.setDescription(recipe.getDescription());
        dto.setDurationMinutes(recipe.getPreparationTime());
        dto.setDifficulty(recipe.getDifficultyLevel());
        dto.setMealType(recipe.getMealType());
        dto.setIngredients(
                recipe.getIngredients()
                        .stream()
                        .map(i -> i.getName())
                        .toList()
        );
        return dto;
    }
}
