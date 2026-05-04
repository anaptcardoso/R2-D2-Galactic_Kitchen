package org.converters;

import org.dtos.IngredientDTO;
import org.dtos.RecipeDTO;
import org.model.entity.Recipe;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class RecipeToDTO extends AbstractConverter<Recipe, RecipeDTO> {

    @Autowired
    private IngredientToDTO ingredientToDTO;

    @Override
    public RecipeDTO convert(Recipe recipe) {
        List<IngredientDTO> ingredientDTOs = ingredientToDTO.convert(recipe.getIngredients());
        return new RecipeDTO(recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getCategory(),
                recipe.getPreparationTime(),
                recipe.getServings(),
                recipe.getCalories(),
                recipe.getProtein(),
                recipe.getCarbs(),
                recipe.getFat(),
                recipe.getDifficultyLevel(),
                recipe.getMealType(),
                ingredientDTOs,
                recipe.getDietTypes(),
                recipe.getSteps() == null ? new ArrayList<>() : Arrays.asList(recipe.getSteps().split("\n")),
                recipe.getTip()
                );
    }

}
