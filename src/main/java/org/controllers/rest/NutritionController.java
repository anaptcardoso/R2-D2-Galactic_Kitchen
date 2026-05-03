package org.controllers.rest;

import org.dtos.ChatMessageDTO;
import org.dtos.RecipeDTO;
import org.services.NutritionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<RecipeDTO> getProfile(@PathVariable int userId) {
        return ResponseEntity.ok(nutritionService.findByUser(userId));
    }

    // PUT /api/nutrition/{userId}
    // Atualiza objetivos nutricionais
    @PutMapping("/{userId}")
    public ResponseEntity<RecipeDTO> update(
            @PathVariable int userId,
            @RequestBody RecipeDTO recipeDTO) {
        return ResponseEntity.ok(nutritionService.update(userId, recipeDTO));
    }

    // POST /api/nutrition/analyse
    // Analisa os alimentos via AI e devolve valores nutricionais
    @PostMapping("/analyse")
    public ResponseEntity<ChatMessageDTO> analyse(@RequestBody ChatMessageDTO message) throws Exception {
        return ResponseEntity.ok(nutritionService.analyse(message));
    }
}