package org.controllers.rest;

import org.dtos.WeeklyPlanDTO;
import org.exceptions.PlanNotFoundException;
import org.exceptions.RecipeNotFoundException;
import org.exceptions.UserNotFoundException;
import org.services.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plan")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    // GET /api/plan/{userId}
    @GetMapping("/{userId}")
    public ResponseEntity<List<WeeklyPlanDTO>> getByUser(@PathVariable int userId)
            throws PlanNotFoundException, UserNotFoundException {
        return ResponseEntity.ok(planService.findByUser(userId));
    }

    // GET /api/plan/{userId}/week/{weekStart}
    @GetMapping("/{userId}/week/{weekStart}")
    public ResponseEntity<WeeklyPlanDTO> getByUserAndWeek(
            @PathVariable int userId,
            @PathVariable String weekStart)
            throws PlanNotFoundException, UserNotFoundException {
        return ResponseEntity.ok(planService.findByUserAndWeek(userId, weekStart));
    }

    // POST /api/plan
    @PostMapping
    public ResponseEntity<WeeklyPlanDTO> create(@RequestBody WeeklyPlanDTO weeklyPlanDTO)
            throws PlanNotFoundException, UserNotFoundException {
        WeeklyPlanDTO created = planService.create(weeklyPlanDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // POST /api/plan/{planId}/recipes/{recipeId}
    @PostMapping("/{planId}/recipes/{recipeId}")
    public ResponseEntity<WeeklyPlanDTO> addRecipe(
            @PathVariable int planId,
            @PathVariable int recipeId)
            throws PlanNotFoundException, RecipeNotFoundException {
        return ResponseEntity.ok(planService.addRecipe(planId, recipeId));
    }

    // DELETE /api/plan/{planId}/recipes/{recipeId}
    @DeleteMapping("/{planId}/recipes/{recipeId}")
    public ResponseEntity<WeeklyPlanDTO> removeRecipe(
            @PathVariable int planId,
            @PathVariable int recipeId)
            throws PlanNotFoundException {
        return ResponseEntity.ok(planService.removeRecipe(planId, recipeId));
    }

    // DELETE /api/plan/{planId}
    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> delete(@PathVariable int planId) throws PlanNotFoundException {
        planService.delete(planId);
        return ResponseEntity.noContent().build();
    }
}