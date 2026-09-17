// ============================================================
// api.js — REST API calls to the R2-D2 Galactic Kitchen backend
// Points to the Spring MVC backend running on Tomcat
// ============================================================

const API_BASE = 'http://localhost:8080/R2-D2-Galactic_Kitchen/api';

// ── Generic request helper ────────────────────────────────────────────────────

/**
 * Sends an HTTP request to the backend API
 * @param {string} method - HTTP method (GET, POST, PUT, DELETE)
 * @param {string} path   - API path (e.g. '/recipes')
 * @param {object} body   - optional request body for POST/PUT
 * @returns {Promise}     - parsed JSON response or null for 204
 */
async function request(method, path, body = null) {
    const options = {
        method,
        headers: { 'Content-Type': 'application/json' }
    };
    if (body) options.body = JSON.stringify(body);

    const response = await fetch(API_BASE + path, options);

    if (!response.ok) {
        const error = await response.json().catch(() => ({ message: 'Unknown error' }));
        throw new Error(error.message || `HTTP ${response.status}`);
    }

    // 204 No Content — no response body
    if (response.status === 204) return null;

    return response.json();
}

// ── Recipe endpoints ──────────────────────────────────────────────────────────

const RecipeAPI = {
    // Fetch all recipes
    getAll:          ()               => request('GET',    '/recipes'),
    // Fetch a single recipe by ID
    getById:         (id)             => request('GET',    `/recipes/${id}`),
    // Fetch recipes filtered by category
    getByCategory:   (category)       => request('GET',    `/recipes/category/${category}`),
    // Search recipes by name (partial, case-insensitive)
    search:          (name)           => request('GET',    `/recipes/search?name=${encodeURIComponent(name)}`),
    // Fetch recipes filtered by difficulty level
    getByDifficulty: (level)          => request('GET',    `/recipes/difficulty/${level}`),
    // Fetch recipes filtered by meal type
    getByMealType:   (mealType)       => request('GET',    `/recipes/mealtype/${mealType}`),
    // Create a new recipe
    create:          (dto)            => request('POST',   '/recipes',       dto),
    // Update an existing recipe
    update:          (id, dto)        => request('PUT',    `/recipes/${id}`, dto),
    // Delete a recipe by ID
    delete:          (id)             => request('DELETE', `/recipes/${id}`)
};

// ── User endpoints ────────────────────────────────────────────────────────────

const UserAPI = {
    // Fetch all users
    getAll:          ()               => request('GET',    '/users'),
    // Fetch a single user by ID
    getById:         (id)             => request('GET',    `/users/${id}`),
    // Search users by first and last name
    search:          (fn, ln)         => request('GET',    `/users/search?firstName=${encodeURIComponent(fn)}&lastName=${encodeURIComponent(ln)}`),
    // Register a new user
    register:        (dto)            => request('POST',   '/users/register',       dto),
    // Update an existing user's personal data
    update:          (id, dto)        => request('PUT',    `/users/${id}`,           dto),
    // Delete a user by ID
    delete:          (id)             => request('DELETE', `/users/${id}`),
    // Fetch a user's nutritional profile
    getNutrition:    (id)             => request('GET',    `/users/${id}/nutrition`),
    // Update a user's nutritional profile
    updateNutrition: (id, dto)        => request('PUT',    `/users/${id}/nutrition`, dto)
};

// ── Weekly plan endpoints ─────────────────────────────────────────────────────

const PlanAPI = {
    // Fetch all plans for a given user
    getByUser:    (userId)            => request('GET',    `/plan/${userId}`),
    // Fetch the plan for a specific week
    getByWeek:    (userId, weekStart) => request('GET',    `/plan/${userId}/week/${weekStart}`),
    // Create a new weekly plan
    create:       (dto)               => request('POST',   '/plan',                          dto),
    // Add a recipe to an existing plan
    addRecipe:    (planId, recipeId)  => request('POST',   `/plan/${planId}/recipes/${recipeId}`),
    // Remove a recipe from a plan
    removeRecipe: (planId, recipeId)  => request('DELETE', `/plan/${planId}/recipes/${recipeId}`),
    // Delete a plan by ID
    delete:       (planId)            => request('DELETE', `/plan/${planId}`)
};

// ── Nutrition endpoints ───────────────────────────────────────────────────────

const NutritionAPI = {
    // Fetch a user's nutritional profile
    getProfile:       (userId)        => request('GET',  `/nutrition/${userId}`),
    // Update a user's nutritional profile
    update:           (userId, dto)   => request('PUT',  `/nutrition/${userId}`,  dto),
    // Analyse food items via AI
    analyse:          (dto)           => request('POST', '/nutrition/analyse',    dto),
    // Get nutritional info for a specific recipe
    getByRecipe:      (recipeId)      => request('GET',  `/nutrition/recipe/${recipeId}`),
    // Get recipes below a calorie threshold
    getBelowCalories: (maxCal)        => request('GET',  `/nutrition/below/${maxCal}`),
    // Calculate total macros for a list of recipe IDs
    getTotal:         (ids)           => request('POST', '/nutrition/total',      ids)
};

// ── Chat endpoints (R2-D2 ChefBot) ───────────────────────────────────────────

const ChatAPI = {
    // Send a general chat message to R2-D2
    chat:      (dto) => request('POST', '/chat',           dto),
    // Ask R2-D2 for recipe suggestions
    suggest:   (dto) => request('POST', '/chat/suggest',   dto),
    // Ask R2-D2 for nutritional information
    nutrition: (dto) => request('POST', '/chat/nutrition', dto)
};

// ── Nutritionist endpoints ────────────────────────────────────────────────────

const NutritionistAPI = {
    // Send a personalised consultation message (includes user profile context)
    consult:         (userId, dto)      => request('POST', `/nutritionist/consult/${userId}`,         dto),
    // Analyse a food item's nutritional value via AI
    analyseFood:     (dto)              => request('POST', '/nutritionist/analyse',                   dto),
    // Generate a personalised meal plan based on the user's nutritional profile
    suggestMealPlan: (dto)              => request('POST', '/nutritionist/meal-plan',                 dto),
    // Evaluate whether a recipe is suitable for a specific user
    evaluateRecipe:  (recipeId, userId) => request('POST', `/nutritionist/evaluate/${recipeId}/${userId}`)
};
