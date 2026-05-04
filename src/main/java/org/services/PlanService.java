package org.services;

import org.dtos.WeeklyPlanDTO;
import org.exceptions.PlanNotFoundException;
import org.exceptions.RecipeNotFoundException;
import org.exceptions.UserNotFoundException;

import java.util.List;

public interface PlanService {

    // Procura todos os planos de um utilizador
    List<WeeklyPlanDTO> findByUser(int userId) throws PlanNotFoundException, UserNotFoundException;

    // Procura o plano de uma semana específica
    WeeklyPlanDTO findByUserAndWeek(int userId, String weekStart) throws PlanNotFoundException, UserNotFoundException;

    // Cria um plano novo
    WeeklyPlanDTO create(WeeklyPlanDTO weeklyPlanDTO) throws PlanNotFoundException, UserNotFoundException;

    // Adiciona uma receita ao plano
    WeeklyPlanDTO addRecipe(int planId, int recipeId) throws PlanNotFoundException, RecipeNotFoundException;

    // Remove uma receita do plano
    WeeklyPlanDTO removeRecipe(int planId, int recipeId) throws PlanNotFoundException;

    // Apaga um plano
    void delete(int planId) throws PlanNotFoundException;
}


