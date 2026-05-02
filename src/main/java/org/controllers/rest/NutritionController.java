package org.controllers.rest;

import org.dtos.ChatMessageDTO;
import org.dtos.UserProfileDTO;
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
    public ResponseEntity<UserProfileDTO> getProfile(@PathVariable int userId) {
        return ResponseEntity.ok(nutritionService.findByUser(userId));
    }

    // POST /api/nutrition/analyse
    // Analisa os alimentos e devolve valores nutricionais via IA
    @PostMapping("/analyse")
    public ResponseEntity<ChatMessageDTO> analyse(@RequestBody ChatMessageDTO message) throws Exception {
        ChatMessageDTO result = nutritionService.analyse(message);
        return ResponseEntity.ok(result);
    }

    // PUT /api/nutrition/{userId}
    // Atualiza objetivos e preferências nutricionais
    @PutMapping("/{userId}")
    public ResponseEntity<UserProfileDTO> update(
            @PathVariable Long userId,
            @RequestBody UserProfileDTO profileDTO) {
        return ResponseEntity.ok(nutritionService.update(userId, profileDTO));
    }

}
