package org.services;

import org.dtos.IngredientDTO;
import org.dtos.RecipeDTO;
import org.model.entity.Recipe;
import org.model.enums.DietType;
import org.persistence.daos.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    // The repository gives us access to the database
    private final RecipeRepository recipeRepository;

    // Constructor injection — Spring injects the repository automatically
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }


    // findById — searches for a recipe by ID
    @Override
    public RecipeDTO findById(int id) {
        // If it does not exist, throws an exception with a clear message
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));
        return toDTO(recipe);
    }


    // findAll — returns all recipes
    @Override
    public List<RecipeDTO> findAll() {
        return recipeRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }


    // findByCategory — filters by category, pasta, rice, etc.
    @Override
    public List<RecipeDTO> findByCategory(String category) {
        return recipeRepository.findByCategory(category)
                .stream()
                .map(this::toDTO)
                .toList();
    }


    // searchByName — searches by name (partial)
    @Override
    public List<RecipeDTO> searchByName(String name) {
        // findByNameContainingIgnoreCase finds "Pasta Carbonara", "pasta and beans", etc.
        return recipeRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::toDTO)
                .toList();
    }


    // save — creates a new recipe
    @Transactional
    @Override
    public RecipeDTO save(RecipeDTO recipeDTO) {
        // Converts the DTO to an entity to save it in the database
        Recipe recipe = toEntity(recipeDTO);
        // Saves it in the database — the ID is generated automatically
        Recipe saved = recipeRepository.save(recipe);
        // Converts it back to a DTO to return it to the frontend
        return toDTO(saved);
    }


    // update — updates an existing recipe
    @Transactional
    @Override
    public RecipeDTO update(int id, RecipeDTO recipeDTO) {
        // We check if the recipe exists before updating it
        Recipe existing = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));

        // We update all fields with the DTO values
        existing.setName(recipeDTO.getName());
        existing.setDescription(recipeDTO.getDescription());
        existing.setCategory(recipeDTO.getCategory());
        existing.setPreparationTime(recipeDTO.getPreparationTime());
        existing.setServings(recipeDTO.getServings());
        existing.setCalories(recipeDTO.getCalories());
        existing.setProtein(recipeDTO.getProtein());
        existing.setCarbs(recipeDTO.getCarbs());
        existing.setFat(recipeDTO.getFat());
        existing.setDifficultyLevel(recipeDTO.getDifficultyLevel());
        existing.setMealType(recipeDTO.getMealType());
        existing.setDietTypes(recipeDTO.getDietTypes());
        existing.setTip(recipeDTO.getTip());

        // Saves the changes in the database
        Recipe updated = recipeRepository.save(existing);
        return toDTO(updated);
    }


    // delete — deletes a recipe
    @Transactional
    @Override
    public void delete(int id) {
        // We check if it exists before deleting it
        if (!recipeRepository.existsById(id)) {
            throw new RuntimeException("Recipe not found with id: " + id);
        }
        recipeRepository.deleteById(id);
    }

    // PRIVATE CONVERSION METHODS

    // Converts Recipe entity → RecipeDTO (to send to the frontend)
    private RecipeDTO toDTO(Recipe recipe) {
        RecipeDTO dto = new RecipeDTO();
        dto.setId(recipe.getId());
        dto.setName(recipe.getName());
        dto.setDescription(recipe.getDescription());
        dto.setCategory(recipe.getCategory());
        dto.setPreparationTime(recipe.getPreparationTime());
        dto.setServings(recipe.getServings());
        dto.setCalories(recipe.getCalories());     // Directly from the model
        dto.setProtein(recipe.getProtein());       // Directly from the model
        dto.setCarbs(recipe.getCarbs());           // Directly from the model
        dto.setFat(recipe.getFat());               // Directly from the model
        dto.setDifficultyLevel(recipe.getDifficultyLevel());
        dto.setMealType(recipe.getMealType());
        dto.setDietTypes(recipe.getDietTypes());
        dto.setTip(recipe.getTip());

        // steps — converts String to List<String> by splitting by lines
        if (recipe.getSteps() != null) {
            dto.setSteps(Arrays.asList(recipe.getSteps().split("\n")));
        }

        // Ingredients — converts each Ingredient to IngredientDTO
        dto.setIngredients(
                recipe.getIngredients()
                        .stream()
                        .map(i -> new IngredientDTO(i.getId(), i.getName(), i.getQuantity(), i.getUnit()))
                        .toList()
        );
        return dto;
    }

    // Converts RecipeDTO → Recipe entity (to save in the database)
    private Recipe toEntity(RecipeDTO dto) {
        Recipe recipe = new Recipe();
        recipe.setName(dto.getName());
        recipe.setDescription(dto.getDescription());
        recipe.setCategory(dto.getCategory());
        recipe.setPreparationTime(dto.getPreparationTime());
        recipe.setServings(dto.getServings());
        recipe.setCalories(dto.getCalories());
        recipe.setProtein(dto.getProtein());
        recipe.setCarbs(dto.getCarbs());
        recipe.setFat(dto.getFat());
        recipe.setDifficultyLevel(dto.getDifficultyLevel());
        recipe.setMealType(dto.getMealType());
        recipe.setDietTypes(dto.getDietTypes());
        recipe.setTip(dto.getTip());
        return recipe;
    }
}