package org.services;

import org.dtos.PlanDTO;

import java.util.List;

public interface PlanService {

    // Busca todos os planos de um utilizador
    List<PlanDTO> findByUser(int userId);

    // Busca o plano de uma semana específica
    PlanDTO findByUserAndWeek(int userId, String weekStart);

    // Cria um plano novo
    PlanDTO create(PlanDTO planDTO);

    // Adiciona uma receita ao plano
    PlanDTO addRecipe(int planId, int recipeId);

    // Remove uma receita do plano
    PlanDTO removeRecipe(int planId, int recipeId);

    // Apaga um plano
    void delete(int planId);
}
