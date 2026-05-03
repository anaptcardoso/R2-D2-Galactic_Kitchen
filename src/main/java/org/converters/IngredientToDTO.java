package org.converters;

import org.dtos.IngredientDTO;
import org.model.entity.Ingredient;
import org.springframework.stereotype.Component;

@Component
public class IngredientToDTO extends AbstractConverter<Ingredient, IngredientDTO> {

    @Override
    public IngredientDTO convert(Ingredient ingredient) {
        return new IngredientDTO(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getQuantity(),
                ingredient.getUnit()
        );
    }
}
