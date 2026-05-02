package org.services;

import org.dtos.WeeklyPlanDTO;

import java.util.List;

public interface PlanService {

    // Busca todos os planos de um utilizador
    List<WeeklyPlanDTO> findByUser(int userId);

    // Busca o plano de uma semana específica
    WeeklyPlanDTO findByUserAndWeek(int userId, String weekStart);

    // Cria um plano novo
    WeeklyPlanDTO create(WeeklyPlanDTO weeklyPlanDTO);

    // Adiciona uma receita ao plano
    WeeklyPlanDTO addRecipe(int planId, int recipeId);

    // Remove uma receita do plano
    WeeklyPlanDTO removeRecipe(int planId, int recipeId);

    // Apaga um plano
    void delete(int planId);
}
