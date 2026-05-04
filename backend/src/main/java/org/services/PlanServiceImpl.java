package org.services;

import org.dtos.IngredientDTO;
import org.dtos.RecipeDTO;
import org.dtos.WeeklyPlanDTO;
import org.exceptions.PlanNotFoundException;
import org.exceptions.RecipeNotFoundException;
import org.exceptions.UserNotFoundException;
import org.model.entity.Recipe;
import org.model.entity.UserProfile;
import org.model.entity.WeeklyPlan;
import org.persistence.daos.RecipeRepository;
import org.persistence.daos.UserProfileRepository;
import org.persistence.daos.WeeklyPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    // findByUser — todos os planos de um utilizador
    @Override
    public List<WeeklyPlanDTO> findByUser(int userId) throws PlanNotFoundException, UserNotFoundException {
        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return weeklyPlanRepository.findByUser(user)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // findByUserAndWeek — plano de uma semana específica
    @Override
    public WeeklyPlanDTO findByUserAndWeek(int userId, String weekStart) throws PlanNotFoundException, UserNotFoundException {
        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        LocalDate date = LocalDate.parse(weekStart);
        WeeklyPlan plan = weeklyPlanRepository.findByUserAndWeekStart(user, date)
                .orElseThrow(PlanNotFoundException::new);
        return toDTO(plan);
    }

    // create — cria um plano novo
    @Transactional
    @Override
    public WeeklyPlanDTO create(WeeklyPlanDTO weeklyPlanDTO) throws PlanNotFoundException, UserNotFoundException {
        UserProfile user = userProfileRepository.findById(weeklyPlanDTO.getUserId())
                .orElseThrow(UserNotFoundException::new);

        WeeklyPlan plan = new WeeklyPlan();
        plan.setUser(user);
        plan.setWeekStart(weeklyPlanDTO.getWeekStart());
        plan.setWeekEnd(weeklyPlanDTO.getWeekEnd());
        plan.setRecipes(new ArrayList<>());

        WeeklyPlan saved = weeklyPlanRepository.save(plan);
        return toDTO(saved);
    }

    // addRecipe — adiciona uma receita ao plano
    @Transactional
    @Override
    public WeeklyPlanDTO addRecipe(int planId, int recipeId) throws PlanNotFoundException, RecipeNotFoundException {
        WeeklyPlan plan = weeklyPlanRepository.findById(planId)
                .orElseThrow(PlanNotFoundException::new);

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(RecipeNotFoundException::new);

        plan.getRecipes().add(recipe);
        WeeklyPlan updated = weeklyPlanRepository.save(plan);
        return toDTO(updated);
    }

    // removeRecipe — remove uma receita do plano
    @Transactional
    @Override
    public WeeklyPlanDTO removeRecipe(int planId, int recipeId) throws PlanNotFoundException {
        WeeklyPlan plan = weeklyPlanRepository.findById(planId)
                .orElseThrow(PlanNotFoundException::new);

        plan.getRecipes().removeIf(r -> r.getId() == recipeId);
        WeeklyPlan updated = weeklyPlanRepository.save(plan);
        return toDTO(updated);
    }

    // delete — apaga um plano
    @Transactional
    @Override
    public void delete(int planId) throws PlanNotFoundException {
        if (!weeklyPlanRepository.existsById(planId)) {
            throw new PlanNotFoundException();
        }
        weeklyPlanRepository.deleteById(planId);
    }

    // ── Métodos privados ──────────────────────────────────────────────────────

    private WeeklyPlanDTO toDTO(WeeklyPlan plan) {
        WeeklyPlanDTO dto = new WeeklyPlanDTO();
        dto.setId(plan.getId());
        dto.setUserId(plan.getUser().getId());
        dto.setWeekStart(plan.getWeekStart());
        dto.setWeekEnd(plan.getWeekEnd());
        dto.setRecipes(plan.getRecipes().stream()
                .map(this::recipeToDTO)
                .toList());
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

        // converte o Map para List<String> — ex: "Farinha (g): 350.0"
        return aggregated.entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .toList();
    }

    // Converte Recipe → RecipeDTO
    private RecipeDTO recipeToDTO(Recipe recipe) {
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

        // steps — converte String para List<String> se necessário
        if (recipe.getSteps() != null) {
            dto.setSteps(Arrays.asList(recipe.getSteps().split("\n")));
        }

        dto.setIngredients(recipe.getIngredients().stream()
                .map(i -> new IngredientDTO(i.getId(), i.getName(), i.getQuantity(), i.getUnit()))
                .toList());
        return dto;
    }
}