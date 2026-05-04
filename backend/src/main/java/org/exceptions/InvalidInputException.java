package org.exceptions;

import org.errors.ErrorMessage;

public class InvalidInputException extends R2D2ChefBotException{
    public InvalidInputException() {
        super(ErrorMessage.INVALID_INPUT);
    }
}
