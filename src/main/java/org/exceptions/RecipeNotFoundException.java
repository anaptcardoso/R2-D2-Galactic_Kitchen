package org.exceptions;

import org.errors.ErrorMessage;

public class RecipeNotFoundException extends R2D2ChefBotException{
    public RecipeNotFoundException() {
        super(ErrorMessage.RECIPE_NOT_FOUND);
    }
}
