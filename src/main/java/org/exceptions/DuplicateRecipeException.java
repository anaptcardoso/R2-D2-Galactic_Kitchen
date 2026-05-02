package org.exceptions;

import org.errors.ErrorMessage;

public class DuplicateRecipeException extends R2D2ChefBotException{
    public DuplicateRecipeException() {
        super(ErrorMessage.RECIPE_ALREADY_EXISTS);
    }
}
