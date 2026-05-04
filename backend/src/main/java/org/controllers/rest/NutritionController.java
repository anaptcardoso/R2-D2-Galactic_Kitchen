package org.controllers.rest;

import org.dtos.ChatMessageDTO;
import org.dtos.NutritionDTO;
import org.dtos.RecipeDTO;
import org.exceptions.UserNotFoundException;
import org.services.NutritionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nutrition")
public class NutritionController {

    private final NutritionService nutritionService;

    public NutritionController(NutritionService nutritionService) {
        this.nutritionService = nutritionService;
    }

    // GET /api/nutrition/{userId}
    // Devolve perfil nutricional do utilizador
    @GetMapping("/{userId}")
    public ResponseEntity<NutritionDTO> getProfile(@PathVariable int userId) throws UserNotFoundException {
        return ResponseEntity.ok(nutritionService.findByUser(userId));
    }

    // PUT /api/nutrition/{userId}
    // Atualiza objetivos nutricionais
    @PutMapping("/{userId}")
    public ResponseEntity<NutritionDTO> update(
            @PathVariable int userId,
            @RequestBody NutritionDTO nutritionDTO) throws UserNotFoundException {
        return ResponseEntity.ok(nutritionService.update(userId, nutritionDTO));
    }

    // POST /api/nutrition/analyse
    // Analisa os alimentos via AI e devolve valores nutricionais
    @PostMapping("/analyse")
    public ResponseEntity<ChatMessageDTO> analyse(@RequestBody ChatMessageDTO message) throws Exception {
        return ResponseEntity.ok(nutritionService.analyse(message));
    }

    // GET /api/nutrition/recipe/{recipeId}
    // Devolve a info nutricional de uma receita
    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<RecipeDTO> getByRecipe(@PathVariable int recipeId) {
        return ResponseEntity.ok(nutritionService.getNutritionByRecipe(recipeId));
    }

    // GET /api/nutrition/below/{maxCalories}
    // Devolve receitas abaixo de X calorias
    @GetMapping("/below/{maxCalories}")
    public ResponseEntity<List<RecipeDTO>> getBelowCalories(@PathVariable int maxCalories) {
        return ResponseEntity.ok(nutritionService.getRecipesBelowCalories(maxCalories));
    }

    // POST /api/nutrition/total
    // Calcula o total de macros de várias receitas
    @PostMapping("/total")
    public ResponseEntity<RecipeDTO> getTotal(@RequestBody List<Integer> recipeIds) {
        return ResponseEntity.ok(nutritionService.getTotalNutrition(recipeIds));
    }

}