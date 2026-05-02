package org.controllers.rest;

import org.dtos.ChatMessageDTO;
import org.dtos.NutritionDTO;
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
    public ResponseEntity<NutritionDTO> getProfile(@PathVariable int userId) {
        return ResponseEntity.ok(nutritionService.findByUser(userId));
    }

    // PUT /api/nutrition/{userId}
    // Atualiza objetivos e preferências nutricionais
    @PutMapping("/{userId}")
    public ResponseEntity<NutritionDTO> update(
            @PathVariable int userId,
            @RequestBody NutritionDTO nutritionDTO) {
        return ResponseEntity.ok(nutritionService.update(userId, nutritionDTO));
    }

    // POST /api/nutrition/analyse
    // Analisa os alimentos via AI e devolve valores nutricionais
    @PostMapping("/analyse")
    public ResponseEntity<ChatMessageDTO> analyse(@RequestBody ChatMessageDTO message) throws Exception {
        return ResponseEntity.ok(nutritionService.analyse(message));
    }

}
