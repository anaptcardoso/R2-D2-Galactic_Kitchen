package org.controllers.rest;

import org.dtos.UserProfileDTO;
import org.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET /api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    // POST /api/users/register
    @PostMapping("/register")
    public ResponseEntity<UserProfileDTO> register(@RequestBody UserProfileDTO userProfileDTO) {
        UserProfileDTO created = userService.register(userProfileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT /api/users/{id}
    // Atualiza dados pessoais do perfil
    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDTO> update(
            @PathVariable Long id,
            @RequestBody UserProfileDTO userProfileDTO) {
        return ResponseEntity.ok(userService.update(id, userProfileDTO));
    }

    // DELETE /api/users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
