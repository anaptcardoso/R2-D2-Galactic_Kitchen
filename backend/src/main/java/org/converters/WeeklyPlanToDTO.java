package org.converters;

import org.dtos.RecipeDTO;
import org.dtos.WeeklyPlanDTO;
import org.model.entity.WeeklyPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeeklyPlanToDTO extends AbstractConverter<WeeklyPlan, WeeklyPlanDTO>{

    @Autowired
    private RecipeToDTO recipeToDTO;

   @Override
    public WeeklyPlanDTO convert(WeeklyPlan plan) {
       List<RecipeDTO> recipeDTOS = recipeToDTO.convert(plan.getRecipes());
        return new WeeklyPlanDTO(
                plan.getId(),
                plan.getUser().getId(),
                plan.getWeekStart(),
                plan.getWeekEnd(),
                recipeDTOS,
                null //shoppingList gerada separadamente pelo PlanService
        );
    }
}
