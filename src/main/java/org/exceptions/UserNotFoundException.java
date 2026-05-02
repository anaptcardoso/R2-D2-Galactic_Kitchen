package org.exceptions;

import org.errors.ErrorMessage;

public class UserNotFoundException extends R2D2ChefBotException{
    public UserNotFoundException() {
        super(ErrorMessage.USER_NOT_FOUND);
    }
}
