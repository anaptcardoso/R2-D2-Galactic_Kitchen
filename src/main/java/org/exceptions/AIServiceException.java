package org.exceptions;

import org.errors.ErrorMessage;

public class AIServiceException extends R2D2ChefBotException {
    public AIServiceException() {
        super(ErrorMessage.AI_SERVICE_ERROR);
    }
}
