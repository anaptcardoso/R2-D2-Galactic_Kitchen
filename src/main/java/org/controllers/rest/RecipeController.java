package org.controllers.rest;

import org.dtos.RecipeDTO;
import org.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    //GET /api/recipes
    @GetMapping
    public ResponseEntity<List<RecipeDTO>> getAll() { //dependente da dto
        return ResponseEntity.ok(recipeService.findAll());//serviços
    }

    //GET /api/recipes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getById(@PathVariable int id) { //dependente da dto
        return ResponseEntity.ok(recipeService.findById(id)); //serviços
    }

    //GET /api/recipes/category/{category}
    @GetMapping("/category/{category}")
    public ResponseEntity<List<RecipeDTO>> getByCategory(@PathVariable String category) { //dependente da dto
        return ResponseEntity.ok(recipeService.findByCategory(category));//serviços
    }

    //GET /api/recipes/search?name=recipe
    @GetMapping("/search")
    public ResponseEntity<List<RecipeDTO>> search(@RequestParam String name) { //dependente da dto
        return ResponseEntity.ok(recipeService.searchByName(name));//serviços
    }

    //POST /api/recipes
    @PostMapping
    public ResponseEntity<RecipeDTO> create(@RequestBody RecipeDTO recipeDTO){
        RecipeDTO created = recipeService.save(recipeDTO);//serviços
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    //PUT /api/recipes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> update(@PathVariable int id, @RequestBody RecipeDTO recipeDTO){
        return ResponseEntity.ok(recipeService.update(id, recipeDTO));//serviços
    }

    //DELETE /api/recipes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        recipeService.delete(id);//serviços
        return ResponseEntity.noContent().build();
    }

}
