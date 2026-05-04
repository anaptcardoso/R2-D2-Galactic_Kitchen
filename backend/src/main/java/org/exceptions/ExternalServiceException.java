package org.exceptions;

import org.errors.ErrorMessage;

public class ExternalServiceException extends R2D2ChefBotException{
    public ExternalServiceException() {
        super(ErrorMessage.EXTERNAL_SERVICE_ERROR);
    }
}
