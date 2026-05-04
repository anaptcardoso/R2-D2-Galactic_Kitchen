package org.exceptions;

import org.errors.ErrorMessage;

public class NutritionNotFoundExcepetion extends R2D2ChefBotException{
    public NutritionNotFoundExcepetion() {
        super(ErrorMessage.NUTRITION_NOT_FOUND);
    }
}
