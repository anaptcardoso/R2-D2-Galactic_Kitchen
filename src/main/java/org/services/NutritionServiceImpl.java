package org.services;

import org.dtos.NutritionDTO;
import org.model.entity.Recipe;
import org.model.valueObject.NutritionProfile;
import org.persistence.daos.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NutritionServiceImpl implements NutritionService {

    // Não precisamos de um NutritionRepository separado
    // porque a info nutricional está DENTRO da Recipe
    private final RecipeRepository recipeRepository;

    public NutritionServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    // getNutritionByRecipe — info nutricional de uma receita
    @Override
    public NutritionDTO getNutritionByRecipe(int recipeId) {
        // Buscamos a receita pelo ID
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found: " + recipeId));

        // A info nutricional está dentro da receita como NutritionProfile
        // convertemos para DTO para devolver ao frontend
        return toDTO(recipe.getNutrition(), recipe.getName());
    }


    // getTotalNutrition — soma a nutrição de várias receitas
    @Override
    public NutritionDTO getTotalNutrition(List<Integer> recipeIds) {
        // Buscamos todas as receitas de uma vez só (1 query à BD)
        List<Recipe> recipes = recipeRepository.findAllById(recipeIds);

        // Começamos os totais a zero
        int totalCalories = 0;
        double totalProtein = 0;
        double totalCarbs = 0;
        double totalFat = 0;

        // Percorremos cada receita e somamos os valores
        for (Recipe recipe : recipes) {
            NutritionProfile n = recipe.getNutrition();
            if (n != null) { // protecção caso a receita não tenha nutrição definida
                totalCalories += n.getCalories();
                totalProtein  += n.getProtein();
                totalCarbs    += n.getCarbs();
                totalFat      += n.getFat();
            }
        }

        // Criamos um DTO com os totais e devolvemos
        NutritionDTO total = new NutritionDTO();
        total.setRecipeName("Weekly Total");
        total.setCalories(totalCalories);
        total.setProtein(totalProtein);
        total.setCarbs(totalCarbs);
        total.setFat(totalFat);
        return total;
    }

    // getRecipesBelowCalories — filtra por limite de calorias
    @Override
    public List<NutritionDTO> getRecipesBelowCalories(int maxCalories) {
        return recipeRepository.findAll()
                .stream()
                // filtra só as receitas com calorias abaixo do limite
                .filter(r -> r.getNutrition() != null
                        && r.getNutrition().getCalories() <= maxCalories)
                // converte cada Recipe para NutritionDTO
                .map(r -> toDTO(r.getNutrition(), r.getName()))
                .toList();
    }

    // MÉTODO CONVERSÃO
    private NutritionDTO toDTO(NutritionProfile nutrition, String recipeName) {
        NutritionDTO dto = new NutritionDTO();
        dto.setRecipeName(recipeName);
        dto.setCalories(nutrition.getCalories());
        dto.setProtein(nutrition.getProtein());
        dto.setCarbs(nutrition.getCarbs());
        dto.setFat(nutrition.getFat());
        return dto;
    }
}