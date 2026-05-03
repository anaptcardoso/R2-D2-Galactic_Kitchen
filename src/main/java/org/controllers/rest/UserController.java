package org.controllers.rest;

import org.dtos.NutritionDTO;
import org.dtos.UserProfileDTO;
import org.exceptions.UserNotFoundException;
import org.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET /api/users
    @GetMapping
    public ResponseEntity<List<UserProfileDTO>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    // GET /api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDTO> getById(@PathVariable int id) throws UserNotFoundException {
        return ResponseEntity.ok(userService.findById(id));
    }

    // GET /api/users/search?firstName=Ana&lastName=Silva
    @GetMapping("/search")
    public ResponseEntity<List<UserProfileDTO>> search(
            @RequestParam String firstName,
            @RequestParam String lastName) throws UserNotFoundException {
        return ResponseEntity.ok(userService.searchByName(firstName, lastName));
    }

    // POST /api/users/register
    @PostMapping("/register")
    public ResponseEntity<UserProfileDTO> register(@RequestBody UserProfileDTO userProfileDTO) throws UserNotFoundException {
        UserProfileDTO created = userService.save(userProfileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT /api/users/{id}
    // Atualiza dados pessoais do perfil
    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDTO> update(
            @PathVariable int id,
            @RequestBody UserProfileDTO userProfileDTO) throws UserNotFoundException {
        return ResponseEntity.ok(userService.update(id, userProfileDTO));
    }

    // GET /api/users/{id}/nutrition
    // Devolve apenas os dados nutricionais
    @GetMapping("/{id}/nutrition")
    public ResponseEntity<NutritionDTO> getNutrition(@PathVariable int id) throws UserNotFoundException {
        return ResponseEntity.ok(userService.getNutrition(id));
    }

    // PUT /api/users/{id}/nutrition
    // Atualiza apenas os dados nutricionais
    @PutMapping("/{id}/nutrition")
    public ResponseEntity<NutritionDTO> updateNutrition(
            @PathVariable int id,
            @RequestBody NutritionDTO nutritionDTO) throws UserNotFoundException {
        return ResponseEntity.ok(userService.updateNutrition(id, nutritionDTO));
    }

    // DELETE /api/users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) throws UserNotFoundException {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

