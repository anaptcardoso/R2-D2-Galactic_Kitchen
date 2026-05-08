package org.services;

import org.dtos.WeeklyPlanDTO;
import org.exceptions.PlanNotFoundException;
import org.exceptions.RecipeNotFoundException;
import org.exceptions.UserNotFoundException;

import java.util.List;

public interface PlanService {

    // Finds all plans for a user
    List<WeeklyPlanDTO> findByUser(int userId) throws PlanNotFoundException, UserNotFoundException;

    // Finds the plan for a specific week
    WeeklyPlanDTO findByUserAndWeek(int userId, String weekStart) throws PlanNotFoundException, UserNotFoundException;

    // Creates a new plan
    WeeklyPlanDTO create(WeeklyPlanDTO weeklyPlanDTO) throws PlanNotFoundException, UserNotFoundException;

    // Adds a recipe to the plan
    WeeklyPlanDTO addRecipe(int planId, int recipeId) throws PlanNotFoundException, RecipeNotFoundException;

    // Removes a recipe from the plan
    WeeklyPlanDTO removeRecipe(int planId, int recipeId) throws PlanNotFoundException;

    // Deletes a plan
    void delete(int planId) throws PlanNotFoundException;
}


