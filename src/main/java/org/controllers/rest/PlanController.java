package org.controllers.rest;

import org.dtos.WeeklyPlanDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.services.PlanService;

import java.util.List;

@RestController
@RequestMapping("/api/plan")
public class PlanController {
    private final PlanService planService;

    public PlanController(PlanService planService){
        this.planService = planService;
    }

    //GET /api/plan/{userId}
    @GetMapping("/{userId}")
    public ResponseEntity<List<WeeklyPlanDTO>> getByUser(@PathVariable int userId){
        return ResponseEntity.ok(planService.findByUser(userId));
    }

    //GET /api/plan/{userId}/current
    // Devolve o plano da seamana atual
    @GetMapping("/{userId}/current")
    public ResponseEntity<WeeklyPlanDTO> getCurrentPlan(@PathVariable int userId){
        return ResponseEntity.ok(planService.findCurrentPlan(userId));
    }

    //POST /api/plan
    @PostMapping
    public ResponseEntity<WeeklyPlanDTO> create(@RequestBody WeeklyPlanDTO weeklyPlanDTO) {
        WeeklyPlanDTO created = planService.save(weeklyPlanDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    //PUT /api/plan/{id}
    @PutMapping("/{id}")
    public ResponseEntity<WeeklyPlanDTO> update(@PathVariable int id, @RequestBody WeeklyPlanDTO weeklyPlanDTO){
        return ResponseEntity.ok(planService.update(id, weeklyPlanDTO));
    }

    // DELETE /api/plan/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        planService.delete(id);
        return ResponseEntity.noContent().build();
    }


    // GET /api/plan/{userId}/shopping-list
    // Gera lista de compras a partir do plano atual
    @GetMapping("/{userId}/shopping-list")
    public ResponseEntity<?> getShoppingList(@PathVariable int userId) {
        return ResponseEntity.ok(planService.generateShoppingList(userId));
    }

}
