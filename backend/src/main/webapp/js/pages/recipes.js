// recipes.js — Renders the recipes page

async function renderRecipes(params = {}) {

    const app = document.getElementById('main-content');

    app.innerHTML = `
        <section class="recipes-header">
            <h1>Galactic Catalogue</h1>
            <div class="recipes-controls">
                <input 
                    type="text" 
                    id="recipe-search" 
                    placeholder="Search for a recipe..." 
                    value="${App.recipeSearch || ''}"
                />
                <button class="btn btn--primary" onclick="openPlanetModal()">Planet recipe</button>
            </div>
        </section>

        <section class="recipes-filters" id="recipes-filters">
            <button class="filter-btn active" data-filter="all">All</button>
            <button class="filter-btn" data-filter="OMNIVORE">Omnivore</button>
            <button class="filter-btn" data-filter="VEGAN">Vegan</button>
            <button class="filter-btn" data-filter="KETO">Keto</button>
            <button class="filter-btn" data-filter="GLUTEN_FREE">Gluten free</button>
            <button class="filter-btn" data-filter="EASY">Easy</button>
            <button class="filter-btn" data-filter="HARD">Hard</button>
        </section>

        <section class="recipes-grid" id="recipes-grid">
            <p class="loading">Loading recipes...</p>
        </section>
    `;

    // Initializes search and filters
    initRecipes();

    // Fetches recipes from the backend
    await loadRecipes(params);
}

// ── Event initialization ──────────────────────────────────────────────────────

function initRecipes() {
    // Search event — triggers when the user types
    document.getElementById('recipe-search').addEventListener('input', (e) => {
        App.recipeSearch = e.target.value;
        filterAndRenderRecipes();
    });

    // Filter button events
    document.querySelectorAll('.filter-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            // Removes the active class from all buttons
            document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
            // Adds the active class to the clicked button
            btn.classList.add('active');
            // Saves the active filter
            App.recipeFilter = btn.dataset.filter;
            filterAndRenderRecipes();
        });
    });
}

// Loads recipes from the backend

async function loadRecipes(params = {}) {
    try {
        // If recipes are already cached, do not call the backend again
        if (!App.recipes || App.recipes.length === 0) {
            App.recipes = await RecipeAPI.getAll();
        }

        filterAndRenderRecipes();

        // If a recipeId was passed in the params, opens that modal directly
        if (params.recipeId) {
            const recipe = App.recipes.find(r => r.id === params.recipeId);
            if (recipe) openRecipeModal(recipe);
        }

    } catch (e) {
        // If the backend is not available, shows an error message
        console.warn('Could not load recipes from backend:', e.message);
        document.getElementById('recipes-grid').innerHTML = `
            <p class="error">Could not load recipes. Please try again later.</p>
        `;
    }
}

// Filters and renders recipes

function filterAndRenderRecipes() {
    const grid = document.getElementById('recipes-grid');
    if (!grid) return;

    let filtered = App.recipes || [];

    // Applies the diet or difficulty filter
    if (App.recipeFilter && App.recipeFilter !== 'all') {
        if (App.recipeFilter === 'EASY' || App.recipeFilter === 'HARD') {
            // Filters by difficulty
            filtered = filtered.filter(r => r.difficultyLevel === App.recipeFilter);
        } else {
            // Filters by diet type
            filtered = filtered.filter(r => r.dietTypes && r.dietTypes.includes(App.recipeFilter));
        }
    }

    // Applies the name search
    if (App.recipeSearch && App.recipeSearch.trim() !== '') {
        const search = App.recipeSearch.toLowerCase();
        filtered = filtered.filter(r => r.name.toLowerCase().includes(search));
    }

    // If there are no recipes to show
    if (filtered.length === 0) {
        grid.innerHTML = `<p class="empty">No recipes found.</p>`;
        return;
    }

    // Renders the recipe cards
    grid.innerHTML = filtered.map(recipe => `
        <div class="recipe-card" onclick="openRecipeModal(${JSON.stringify(recipe).replace(/"/g, '&quot;')})">
            <div class="recipe-card__header">
                <span class="recipe-card__difficulty ${getDifficultyClass(recipe.difficultyLevel)}">
                    ${formatDifficulty(recipe.difficultyLevel)}
                </span>
            </div>
            <div class="recipe-card__body">
                <h3>${recipe.name}</h3>
                <p>${truncate(recipe.description, 80)}</p>
                <div class="recipe-card__tags">
                    ${(recipe.dietTypes || []).map(d => `
                        <span class="tag">${formatDiet(d)}</span>
                    `).join('')}
                </div>
            </div>
            <div class="recipe-card__footer">
                <span>${recipe.preparationTime} min · ${recipe.servings} servings</span>
                <span class="recipe-card__calories">${recipe.calories} kcal</span>
            </div>
        </div>
    `).join('');
}

// ── Helper functions ──────────────────────────────────────────────────────────

/**
 * Formats the diet type in readable English
 * @param {string} diet
 */
function formatDiet(diet) {
    const map = {
        'OMNIVORE':   'Omnivore',
        'VEGAN':      'Vegan',
        'VEGETARIAN': 'Vegetarian',
        'KETO':       'Keto',
        'GLUTEN_FREE':'Gluten free'
    };
    return map[diet] || diet;
}

/**
 * Returns the CSS class based on the difficulty to color the badge
 * @param {string} difficulty
 */
function getDifficultyClass(difficulty) {
    const map = {
        'EASY':   'difficulty--easy',
        'MEDIUM': 'difficulty--medium',
        'HARD':   'difficulty--hard'
    };
    return map[difficulty] || '';
}

/**
 * Truncates text to a maximum number of characters
 * @param {string} text
 * @param {number} max
 */
function truncate(text, max) {
    if (!text) return '';
    return text.length > max ? text.slice(0, max) + '...' : text;
}