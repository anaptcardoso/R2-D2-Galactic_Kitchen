// recipes.js — Renders the recipes page of the SPA

/**
 * Renders the main recipe catalogue page.
 * This function is called by the router when the user navigates to "recipes".
 *
 * @param {Object} params - Optional navigation parameters.
 * @param {number|string} [params.recipeId] - Recipe ID to open directly in the modal.
 */
async function renderRecipes(params = {}) {
    const app = document.getElementById('main-content');

    app.innerHTML = `
        <section class="catalogue-hero">
            <div class="hero-eyebrow">// GALACTIC RECIPE DATABASE</div>
            <h1>Galactic <span>Catalogue</span></h1>
            <p>Search, filter and discover recipes from across the galaxy.</p>
        </section>

        <section class="catalogue-toolbar">
            <div class="catalogue-search-wrap">
                <svg class="catalogue-search-icon" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8">
                    <circle cx="11" cy="11" r="7"></circle>
                    <path d="M20 20l-4.5-4.5"></path>
                </svg>

                <input 
                    type="text" 
                    id="recipe-search" 
                    class="search-input catalogue-search"
                    placeholder="Search for a recipe..." 
                    value="${App.recipeSearch || ''}"
                />
            </div>

            <button class="btn-gold" id="planet-recipe-btn">
                PLANET RECIPE
            </button>
        </section>

        <section class="filter-row catalogue-filters" id="recipes-filters">
            <button class="f-btn active" data-filter="all">ALL</button>
            <button class="f-btn" data-filter="OMNIVORE">OMNIVORE</button>
            <button class="f-btn" data-filter="VEGAN">VEGAN</button>
            <button class="f-btn" data-filter="KETO">KETO</button>
            <button class="f-btn" data-filter="GLUTEN_FREE">GLUTEN FREE</button>
            <button class="f-btn" data-filter="EASY">EASY</button>
            <button class="f-btn" data-filter="MEDIUM">MEDIUM</button>
            <button class="f-btn" data-filter="HARD">HARD</button>
        </section>

        <section class="catalogue-meta">
            <div>
                <span class="section-label">DATABASE STATUS</span>
                <p id="recipes-count">Loading recipes...</p>
            </div>

            <div class="catalogue-signal">
                <span class="status-dot"></span>
                <span>ARCHIVE ONLINE</span>
            </div>
        </section>

        <section class="recipes-grid catalogue-grid" id="recipes-grid">
            <div class="loading">
                <p>LOADING RECIPES...</p>
            </div>
        </section>
    `;

    // Initializes all input, button and filter events.
    initRecipes();

    // Loads the recipes and renders the grid.
    await loadRecipes(params);
}

/**
 * Initializes all interactive events on the recipes page.
 *
 * Events handled:
 * - live search;
 * - "Planet Recipe" button;
 * - filter buttons;
 * - active filter state.
 */
function initRecipes() {
    const searchInput = document.getElementById('recipe-search');
    const planetButton = document.getElementById('planet-recipe-btn');

    // Live search.
    // Whenever the user types, we save the search term in the global state
    // and re-filter/re-render the recipes.
    searchInput?.addEventListener('input', (e) => {
        App.recipeSearch = e.target.value;
        filterAndRenderRecipes();
    });

    // Opens the modal for generating/browsing recipes by planet.
    planetButton?.addEventListener('click', () => {
        openPlanetModal();
    });

    // Adds behavior to all filter buttons.
    // Only one filter can be active at a time.
    document.querySelectorAll('.f-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            document.querySelectorAll('.f-btn').forEach(b => b.classList.remove('active'));
            btn.classList.add('active');

            App.recipeFilter = btn.dataset.filter;
            filterAndRenderRecipes();
        });
    });
}

/**
 * Loads recipes from the backend.
 *
 * If recipes are already stored in App.recipes, this avoids
 * making another API request.
 *
 * @param {Object} params - Optional navigation parameters.
 * @param {number|string} [params.recipeId] - Recipe ID to open directly.
 */
async function loadRecipes(params = {}) {
    const grid = document.getElementById('recipes-grid');

    try {
        // Uses cached recipes if they already exist; otherwise calls the backend.
        if (!App.recipes || App.recipes.length === 0) {
            App.recipes = await RecipeAPI.getAll();
        }

        // Applies filters/search and updates the interface.
        filterAndRenderRecipes();

        // If navigation includes a specific recipe ID, open that recipe modal directly.
        if (params.recipeId) {
            const recipe = App.recipes.find(r => Number(r.id) === Number(params.recipeId));
            if (recipe) openRecipeModal(recipe);
        }

    } catch (e) {
        console.warn('Could not load recipes from backend:', e.message);

        // Shows a visual error state if the API fails.
        if (grid) {
            grid.innerHTML = `
                <div class="empty-state catalogue-empty">
                    <div class="empty-icon">
                        ${getEmptyIcon()}
                    </div>
                    <h3>DATABASE CONNECTION FAILED</h3>
                    <p>Could not load recipes. Please try again later.</p>
                </div>
            `;
        }

        updateRecipesCount(0);
    }
}

/**
 * Filters recipes based on the active filter and search text.
 * Then renders the matching recipe cards in the grid.
 *
 * Supported filters:
 * - difficulty: EASY, MEDIUM, HARD;
 * - diet: OMNIVORE, VEGAN, KETO, GLUTEN_FREE;
 * - search by name or description.
 */
function filterAndRenderRecipes() {
    const grid = document.getElementById('recipes-grid');
    if (!grid) return;

    let filtered = App.recipes || [];

    // Applies difficulty or diet filter.
    if (App.recipeFilter && App.recipeFilter !== 'all') {
        if (['EASY', 'MEDIUM', 'HARD'].includes(App.recipeFilter)) {
            filtered = filtered.filter(r => r.difficultyLevel === App.recipeFilter);
        } else {
            filtered = filtered.filter(r => r.dietTypes && r.dietTypes.includes(App.recipeFilter));
        }
    }

    // Applies search by recipe name or description.
    if (App.recipeSearch && App.recipeSearch.trim() !== '') {
        const search = App.recipeSearch.toLowerCase();

        filtered = filtered.filter(r => {
            const name = (r.name || '').toLowerCase();
            const description = (r.description || '').toLowerCase();

            return name.includes(search) || description.includes(search);
        });
    }

    // Updates the result count.
    updateRecipesCount(filtered.length);

    // Empty state when no recipe matches the current search/filter.
    if (filtered.length === 0) {
        grid.innerHTML = `
            <div class="empty-state catalogue-empty">
                <div class="empty-icon">
                    ${getEmptyIcon()}
                </div>
                <h3>NO RECIPES FOUND</h3>
                <p>Try another search term or remove some filters.</p>
            </div>
        `;
        return;
    }

    // Renders all filtered cards.
    grid.innerHTML = filtered.map(renderRecipeCard).join('');
}

/**
 * Creates the HTML for one recipe card.
 *
 * @param {Object} recipe - Recipe object returned by the API.
 * @returns {string} Recipe card HTML.
 */
function renderRecipeCard(recipe) {
    // Serializes the recipe so it can be safely used inside the onclick attribute.
    
    const difficulty = recipe.difficultyLevel || 'EASY';
    const diffClass = difficulty.toLowerCase();

    return `
        <article class="recipe-card catalogue-recipe-card" onclick="openRecipeModal(${recipe.id})">
            <div class="recipe-banner catalogue-recipe-banner">
                <div class="recipe-line-icon cyan-icon">
                    ${getRecipeIcon(recipe.name)}
                </div>

                <span class="diff-badge diff-${diffClass}">
                    ${formatDifficulty(difficulty)}
                </span>
            </div>

            <div class="recipe-body">
                <h3 class="recipe-name">${recipe.name || 'Unknown Recipe'}</h3>

                <p class="recipe-desc">
                    ${truncate(recipe.description || 'A galactic recipe ready for your next mission.', 110)}
                </p>

                <div class="recipe-tags">
                    ${(recipe.dietTypes || []).slice(0, 3).map(d => `
                        <span class="tag ${getDietTagClass(d)}">${formatDiet(d)}</span>
                    `).join('')}
                </div>

                <div class="recipe-footer">
                    <span class="recipe-meta">
                        ${recipe.preparationTime || '--'} min · ${recipe.servings || '--'} servings
                    </span>

                    <span class="recipe-cal">
                        ${recipe.calories || '---'} kcal
                    </span>
                </div>
            </div>
        </article>
    `;
}

/**
 * Updates the text showing how many recipes were found.
 *
 * @param {number} count - Number of filtered recipes.
 */
function updateRecipesCount(count) {
    const countElement = document.getElementById('recipes-count');
    if (!countElement) return;

    const label = count === 1 ? 'recipe located' : 'recipes located';
    countElement.textContent = `${count} ${label}`;
}

/**
 * Converts a diet enum from the API into readable text.
 *
 * @param {string} diet - Diet type.
 * @returns {string} Formatted diet name.
 */
function formatDiet(diet) {
    const map = {
        OMNIVORE: 'Omnivore',
        VEGAN: 'Vegan',
        VEGETARIAN: 'Vegetarian',
        KETO: 'Keto',
        GLUTEN_FREE: 'Gluten free'
    };

    return map[diet] || diet;
}

/**
 * Returns the CSS class used to style each diet tag.
 *
 * @param {string} diet - Diet type.
 * @returns {string} CSS class for the tag.
 */
function getDietTagClass(diet) {
    const map = {
        OMNIVORE: 'tag-omnivore',
        VEGAN: 'tag-vegan',
        VEGETARIAN: 'tag-vegan',
        KETO: 'tag-keto',
        GLUTEN_FREE: 'tag-gluten_free'
    };

    return map[diet] || 'tag-diet';
}

/**
 * Converts a difficulty enum into readable text.
 *
 * @param {string} difficulty - EASY, MEDIUM or HARD.
 * @returns {string} Formatted difficulty.
 */
function formatDifficulty(difficulty) {
    const map = {
        EASY: 'Easy',
        MEDIUM: 'Medium',
        HARD: 'Hard'
    };

    return map[difficulty] || difficulty || 'Easy';
}

/**
 * Truncates a text to a maximum number of characters.
 *
 * @param {string} text - Original text.
 * @param {number} max - Maximum number of characters.
 * @returns {string} Truncated text.
 */
function truncate(text, max) {
    if (!text) return '';
    return text.length > max ? text.slice(0, max).trim() + '...' : text;
}

/**
 * Chooses a line-style SVG icon based on the recipe name.
 * This keeps the interface emoji-free and consistent with the sci-fi style.
 *
 * @param {string} name - Recipe name.
 * @returns {string} SVG as a string.
 */
function getRecipeIcon(name = '') {
    const lower = name.toLowerCase();

    if (lower.includes('noodle') || lower.includes('pasta')) {
        return `
            <svg width="46" height="46" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6">
                <path d="M4 10h16"/>
                <path d="M6 10c0 5 3 9 6 9s6-4 6-9"/>
                <path d="M8 6v4M12 6v4M16 6v4"/>
            </svg>
        `;
    }

    if (lower.includes('soup') || lower.includes('stew')) {
        return `
            <svg width="46" height="46" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6">
                <path d="M4 11h16"/>
                <path d="M6 11c0 5 3 8 6 8s6-3 6-8"/>
                <path d="M8 7c1-2 3-2 4 0M13 7c1-2 3-2 4 0"/>
            </svg>
        `;
    }

    if (lower.includes('roast') || lower.includes('brisket')) {
        return `
            <svg width="46" height="46" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6">
                <path d="M7 8h10c2 0 4 2 4 4s-2 4-4 4H7c-2 0-4-2-4-4s2-4 4-4z"/>
                <path d="M8 8c1-2 3-3 5-3"/>
                <path d="M9 16c1 2 3 3 5 3"/>
            </svg>
        `;
    }

    return `
        <svg width="46" height="46" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6">
            <circle cx="12" cy="12" r="8"/>
            <path d="M8 12h8M12 8v8"/>
        </svg>
    `;
}

/**
 * SVG icon used for empty or error states.
 *
 * @returns {string} SVG as a string.
 */
function getEmptyIcon() {
    return `
        <svg width="44" height="44" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6">
            <circle cx="11" cy="11" r="7"></circle>
            <path d="M20 20l-4.5-4.5"></path>
            <path d="M8 11h6"></path>
        </svg>
    `;
}