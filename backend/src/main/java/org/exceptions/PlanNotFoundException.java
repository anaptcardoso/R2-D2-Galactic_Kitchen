package org.exceptions;

import org.errors.ErrorMessage;

public class PlanNotFoundException extends R2D2ChefBotException {
    public PlanNotFoundException() {
        super(ErrorMessage.PLAN_NOT_FOUND);
    }
}
