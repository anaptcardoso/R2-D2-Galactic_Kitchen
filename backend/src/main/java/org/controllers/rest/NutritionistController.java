package org.controllers.rest;

import org.dtos.ChatMessageDTO;
import org.dtos.NutritionDTO;
import org.services.NutritionistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nutritionist")
public class NutritionistController  {

    private final NutritionistService nutritionistService;

    public NutritionistController(NutritionistService nutritionistService) {
        this.nutritionistService = nutritionistService;
    }

    // POST /api/nutritionist/consult/{userId}
    // Consulta personalizada com o perfil do utilizador
    @PostMapping("/consult/{userId}")
    public ResponseEntity<ChatMessageDTO> consult(
            @PathVariable int userId,
            @RequestBody ChatMessageDTO message) throws Exception {
        return ResponseEntity.ok(nutritionistService.consult(message, userId));
    }

    // POST /api/nutritionist/analyse
    // Analisa valores nutricionais de alimentos
    @PostMapping("/analyse")
    public ResponseEntity<ChatMessageDTO> analyseFood(
            @RequestBody ChatMessageDTO message) throws Exception {
        return ResponseEntity.ok(nutritionistService.analyseFood(message));
    }

    // POST /api/nutritionist/meal-plan
    // Gera plano alimentar personalizado
    @PostMapping("/meal-plan")
    public ResponseEntity<ChatMessageDTO> suggestMealPlan(
            @RequestBody NutritionDTO nutritionProfile) throws Exception {
        return ResponseEntity.ok(nutritionistService.suggestMealPlan(nutritionProfile));
    }

    // POST /api/nutritionist/evaluate/{recipeId}/{userId}
    // Avalia se uma receita é adequada para o utilizador
    @PostMapping("/evaluate/{recipeId}/{userId}")
    public ResponseEntity<ChatMessageDTO> evaluateRecipe(
            @PathVariable int recipeId,
            @PathVariable int userId) throws Exception {
        return ResponseEntity.ok(nutritionistService.evaluateRecipe(recipeId, userId));
    }
}
