package org.services;

import org.dtos.RecipeDTO;
import org.exceptions.RecipeNotFoundException;

import java.util.List;

public interface RecipeService {


    // Finds a recipe by ID and returns a DTO for the frontend
    RecipeDTO findById(int id) throws RecipeNotFoundException;

    List<RecipeDTO> findAll();

    // Finds all recipes from a category, e.g. "VEGAN", "KETO"
    List<RecipeDTO> findByCategory(String category);

    // Searches recipes by name, e.g. "pasta"
    List<RecipeDTO> searchByName(String name);

    // Creates a new recipe — receives a DTO from the frontend, saves it in the database, and returns the created DTO
    RecipeDTO save(RecipeDTO recipeDTO) throws RecipeNotFoundException;

    // Updates an existing recipe by ID
    RecipeDTO update(int id, RecipeDTO recipeDTO) throws RecipeNotFoundException;

    // Deletes a recipe by ID
    void delete(int id) throws RecipeNotFoundException;
}
