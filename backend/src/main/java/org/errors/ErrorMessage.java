package org.errors;

public class ErrorMessage {
    // ── Recipe ────────────────────────────────────────────────────────────────
    public static final String RECIPE_NOT_FOUND        = "Recipe does not exist";
    public static final String RECIPE_ALREADY_EXISTS   = "Recipe already exists";
    public static final String RECIPE_INVALID          = "Invalid recipe data";

    // ── User ──────────────────────────────────────────────────────────────────
    public static final String USER_NOT_FOUND          = "User does not exist";
    public static final String USER_ALREADY_EXISTS     = "User already exists";
    public static final String USER_UNAUTHORIZED       = "Unauthorized access";

    // ── Plan ──────────────────────────────────────────────────────────────────
    public static final String PLAN_NOT_FOUND          = "Weekly plan does not exist";
    public static final String PLAN_INVALID            = "Invalid plan data";

    // ── Nutrition ─────────────────────────────────────────────────────────────
    public static final String NUTRITION_NOT_FOUND     = "Nutrition profile does not exist";
    public static final String NUTRITION_INVALID       = "Invalid nutrition data";

    // ── Input ─────────────────────────────────────────────────────────────────
    public static final String INVALID_INPUT           = "Invalid input data";
    public static final String ASSOCIATION_EXISTS      = "Entity contains association with another entity";

    // ── External services ─────────────────────────────────────────────────────
    public static final String AI_SERVICE_ERROR        = "Unable to reach AI service";
    public static final String EXTERNAL_SERVICE_ERROR  = "External service unavailable";

}
