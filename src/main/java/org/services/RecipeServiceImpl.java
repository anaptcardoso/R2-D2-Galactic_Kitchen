package org.services;

import org.dtos.RecipeDTO;
import org.model.entity.Recipe;
import org.model.enums.DietType;
import org.persistence.daos.RecipeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    // O repository é o nosso acesso à base de dados em vez de escrever SQL, usamos métodos como .findById(), .save(), etc.
    private final RecipeRepository recipeRepository;

    // Constructor injection — o Spring injeta o repository automaticamente
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }


    // findById — busca uma receita por ID
    @Override
    public RecipeDTO findById(int id) {
        // .findById() devolve um Optional<Recipe> (pode existir ou não)
        // .orElseThrow() — se não existir, lança uma excepção
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));

        // Convertemos a entidade Recipe para RecipeDTO antes de devolver
        return toDTO(recipe);
    }


    // findByCategory — filtra receitas por categoria/dieta
    @Override
    public List<RecipeDTO> findByCategory(String category) {
        // Convertemos a String para o enum DietType.
        DietType dietType = DietType.valueOf(category.toUpperCase());

        // Buscamos na BD todas as receitas com esse tipo de dieta
        // e convertemos cada Recipe para RecipeDTO com stream + map
        return recipeRepository.findByDietTypesContaining(dietType)
                .stream()
                .map(this::toDTO)   // para cada Recipe, chama toDTO()
                .toList();
    }

    // searchByName — pesquisa pelo nome (parcial)
    @Override
    public List<RecipeDTO> searchByName(String name) {
        // findByNameContainingIgnoreCase("pasta") encontra "Pasta Carbonara", "pasta e feijão", etc.
        return recipeRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public List<RecipeDTO> findAll() {
        return recipeRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // save — cria uma receita nova
    @Transactional
    @Override
    public RecipeDTO save(RecipeDTO recipeDTO) {
        // O frontend envia um DTO → convertemos para entidade para guardar na BD
        Recipe recipe = toEntity(recipeDTO);

        // .save() guarda na BD e devolve a entidade já com o ID gerado
        Recipe saved = recipeRepository.save(recipe);

        // Convertemos de volta para DTO para devolver ao frontend
        return toDTO(saved);
    }

    // update — atualiza uma receita existente
    @Transactional
    @Override
    public RecipeDTO update(int id, RecipeDTO recipeDTO) {
        // Primeiro verificamos se a receita existe
        Recipe existing = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));

        // Atualizamos os campos da entidade com os valores do DTO
        existing.setName(recipeDTO.getName());
        existing.setDescription(recipeDTO.getDescription());
        existing.setPreparationTime(recipeDTO.getPreparationTime());
        existing.setDifficultyLevel(recipeDTO.getDifficulty());
        existing.setMealType(recipeDTO.getMealType());

        // Guardamos as alterações na BD
        Recipe updated = recipeRepository.save(existing);

        return toDTO(updated);
    }

    // delete — apaga uma receita
    @Transactional
    @Override
    public void delete(int id) {
        // Verificamos se existe antes de apagar (boa prática)
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
        dto.setPreparationTime(recipe.getPreparationTime()); // preparationTime = durationMinutes
        dto.setDifficulty(recipe.getDifficultyLevel());
        dto.setMealType(recipe.getMealType());
        // ingredients no model são objectos Ingredient, no DTO são List<String>
        dto.setIngredients(
                recipe.getIngredients()
                        .stream()
                        .map(i -> i.getName()) // só o nome do ingrediente
                        .toList()
        );
        return dto;
    }

    // Converte RecipeDTO → entidade Recipe (para guardar na BD)
    private Recipe toEntity(RecipeDTO dto) {
        Recipe recipe = new Recipe();
        recipe.setName(dto.getName());
        recipe.setDescription(dto.getDescription());
        recipe.setPreparationTime(dto.getPreparationTime()); // durationMinutes = preparationTime
        recipe.setDifficultyLevel(dto.getDifficulty());
        recipe.setMealType(dto.getMealType());
        return recipe;
    }
}
