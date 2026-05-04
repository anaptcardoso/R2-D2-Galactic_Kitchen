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

    // O repository dá-nos acesso à base de dados
    private final RecipeRepository recipeRepository;

    // Constructor injection — o Spring injeta o repository automaticamente
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }


    // findById — procura uma receita por ID
    @Override
    public RecipeDTO findById(int id) {
        // Se não existir, lança excepção com mensagem clara
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));
        return toDTO(recipe);
    }


    // findAll — devolve todas as receitas
    @Override
    public List<RecipeDTO> findAll() {
        return recipeRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }


    // findByCategory — filtra por categoria, massa, arroz, ...
    @Override
    public List<RecipeDTO> findByCategory(String category) {
        return recipeRepository.findByCategory(category)
                .stream()
                .map(this::toDTO)
                .toList();
    }


    // searchByName — pesquisa pelo nome (parcial)
    @Override
    public List<RecipeDTO> searchByName(String name) {
        // findByNameContainingIgnoreCase encontra "Pasta Carbonara", "pasta e feijão", etc.
        return recipeRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::toDTO)
                .toList();
    }


    // save — cria uma receita nova
    @Transactional
    @Override
    public RecipeDTO save(RecipeDTO recipeDTO) {
        // Converte o DTO para entidade para guardar na BD
        Recipe recipe = toEntity(recipeDTO);
        // Guarda na BD — o ID é gerado automaticamente
        Recipe saved = recipeRepository.save(recipe);
        // Converte de volta para DTO para devolver ao frontend
        return toDTO(saved);
    }


    // update — atualiza uma receita existente
    @Transactional
    @Override
    public RecipeDTO update(int id, RecipeDTO recipeDTO) {
        // Verificamos se a receita existe antes de atualizar
        Recipe existing = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));

        // Atualizamos todos os campos com os valores do DTO
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

        // Guardamos as alterações na BD
        Recipe updated = recipeRepository.save(existing);
        return toDTO(updated);
    }


    // delete — apaga uma receita
    @Transactional
    @Override
    public void delete(int id) {
        // Verificamos se existe antes de apagar
        if (!recipeRepository.existsById(id)) {
            throw new RuntimeException("Recipe not found with id: " + id);
        }
        recipeRepository.deleteById(id);
    }


    // MÉTODOS PRIVADOS DE CONVERSÃO

    // Converte entidade Recipe → RecipeDTO (para enviar ao frontend)
    private RecipeDTO toDTO(Recipe recipe) {
        RecipeDTO dto = new RecipeDTO();
        dto.setId(recipe.getId());
        dto.setName(recipe.getName());
        dto.setDescription(recipe.getDescription());
        dto.setCategory(recipe.getCategory());
        dto.setPreparationTime(recipe.getPreparationTime());
        dto.setServings(recipe.getServings());
        dto.setCalories(recipe.getCalories());     // ← directo do model
        dto.setProtein(recipe.getProtein());       // ← directo do model
        dto.setCarbs(recipe.getCarbs());           // ← directo do model
        dto.setFat(recipe.getFat());               // ← directo do model
        dto.setDifficultyLevel(recipe.getDifficultyLevel());
        dto.setMealType(recipe.getMealType());
        dto.setDietTypes(recipe.getDietTypes());
        dto.setTip(recipe.getTip());

        // steps — converte String para List<String> dividindo por linhas
        if (recipe.getSteps() != null) {
            dto.setSteps(Arrays.asList(recipe.getSteps().split("\n")));
        }

        // ingredientes — converte cada Ingredient para IngredientDTO
        dto.setIngredients(
                recipe.getIngredients()
                        .stream()
                        .map(i -> new IngredientDTO(i.getId(), i.getName(), i.getQuantity(), i.getUnit()))
                        .toList()
        );
        return dto;
    }

    // Converte RecipeDTO → entidade Recipe (para guardar na BD)
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