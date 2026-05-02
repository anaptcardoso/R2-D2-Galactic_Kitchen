package org.services;

import org.dtos.RecipeDTO;

import java.util.List;

public interface RecipeService {


    // Busca uma receita pelo ID e devolve um DTO para o frontend
    RecipeDTO findById(int id);

    List<RecipeDTO> findAll();

    // Busca todas as receitas de uma categoria (ex: "VEGAN", "KETO")
    List<RecipeDTO> findByCategory(String category);

    // Pesquisa receitas pelo nome (ex: "pasta")
    List<RecipeDTO> searchByName(String name);

    // Cria uma receita nova — recebe DTO do frontend, guarda na BD, devolve o DTO criado
    RecipeDTO save(RecipeDTO recipeDTO);

    // Atualiza uma receita existente pelo ID
    RecipeDTO update(int id, RecipeDTO recipeDTO);

    // Apaga uma receita pelo ID
    void delete(int id);
}
