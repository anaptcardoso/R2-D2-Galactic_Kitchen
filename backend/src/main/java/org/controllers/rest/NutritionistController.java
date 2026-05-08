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
    // Personalized consultation using the user's profile
    @PostMapping("/consult/{userId}")
    public ResponseEntity<ChatMessageDTO> consult(
            @PathVariable int userId,
            @RequestBody ChatMessageDTO message) throws Exception {
        return ResponseEntity.ok(nutritionistService.consult(message, userId));
    }

    // POST /api/nutritionist/analyse
    // Analyses nutritional values of food items
    @PostMapping("/analyse")
    public ResponseEntity<ChatMessageDTO> analyseFood(
            @RequestBody ChatMessageDTO message) throws Exception {
        return ResponseEntity.ok(nutritionistService.analyseFood(message));
    }

    // POST /api/nutritionist/meal-plan
    // Generates a personalized meal plan
    @PostMapping("/meal-plan")
    public ResponseEntity<ChatMessageDTO> suggestMealPlan(
            @RequestBody NutritionDTO nutritionProfile) throws Exception {
        return ResponseEntity.ok(nutritionistService.suggestMealPlan(nutritionProfile));
    }

    // POST /api/nutritionist/evaluate/{recipeId}/{userId}
    // Evaluates whether a recipe is suitable for the user
    @PostMapping("/evaluate/{recipeId}/{userId}")
    public ResponseEntity<ChatMessageDTO> evaluateRecipe(
            @PathVariable int recipeId,
            @PathVariable int userId) throws Exception {
        return ResponseEntity.ok(nutritionistService.evaluateRecipe(recipeId, userId));
    }
}
