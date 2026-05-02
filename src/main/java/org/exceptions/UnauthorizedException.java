package org.exceptions;

import org.errors.ErrorMessage;

public class UnauthorizedException extends R2D2ChefBotException{
    public UnauthorizedException() {
        super(ErrorMessage.USER_UNAUTHORIZED);
    }
}
