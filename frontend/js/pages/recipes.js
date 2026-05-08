// recipes.js — Renders the recipes page of the SPA

// Renders the main recipe catalogue page.
// This function is called by the router when the user navigates to "recipes".

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

// Initializes all interactive events on the recipes page.

function initRecipes() {
    const searchInput = document.getElementById('recipe-search');
    

    // Live search.
    // Whenever the user types, we save the search term in the global state
    // and re-filter/re-render the recipes.
    searchInput?.addEventListener('input', (e) => {
        App.recipeSearch = e.target.value;
        filterAndRenderRecipes();
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

// Loads recipes from the backend.
// If recipes are already stored in App.recipes, this avoids making another API request.

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

// Filters recipes based on the active filter and search text.
// Then renders the matching recipe cards in the grid.
// search by name or description.

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

// Creates the HTML for one recipe card.

function renderRecipeCard(recipe) {
    // Serializes the recipe so it can be safely used inside the onclick attribute.
    
    const difficulty = recipe.difficultyLevel || 'EASY';
    const diffClass = difficulty.toLowerCase();

    return `
        <article class="recipe-card catalogue-recipe-card" onclick="openRecipeModal(${recipe.id})">
            <div class="recipe-banner catalogue-recipe-banner">
                <div class="recipe-line-icon cyan-icon">
                    ${getRecipeIcon(recipe)}
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

// Updates the text showing how many recipes were found.

function updateRecipesCount(count) {
    const countElement = document.getElementById('recipes-count');
    if (!countElement) return;

    const label = count === 1 ? 'recipe located' : 'recipes located';
    countElement.textContent = `${count} ${label}`;
}

// Converts a diet enum from the API into readable text.

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

// Returns the CSS class used to style each diet tag.

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

// Converts a difficulty enum into readable text.

function formatDifficulty(difficulty) {
    const map = {
        EASY: 'Easy',
        MEDIUM: 'Medium',
        HARD: 'Hard'
    };

    return map[difficulty] || difficulty || 'Easy';
}

// Truncates a text to a maximum number of characters.

function truncate(text, max) {
    if (!text) return '';
    return text.length > max ? text.slice(0, max).trim() + '...' : text;
}

// Chooses a line-style SVG icon based on the recipe name.

function getRecipeIcon(recipe = {}) {    
    const text = `
        ${recipe.name || ''}
        ${recipe.description || ''}
        ${(recipe.dietTypes || []).join(' ')}
    `.toLowerCase();

    if (
        text.includes('omnivore')
    ) {
        return beefIcon();
    }

    if (
        text.includes('vegan')
    ) {
        return veganIcon();
    }

    if (
        text.includes('keto') 
    ) {
        return eggIcon();
    }

    if (
        text.includes('gluten_free') || text.includes('gluten')
    ) {
        return wheatOffIcon();
    }
    
    if (
        text.includes('vegetarian')
    ) {
        return vegetarianIcon();
    }

    return defaultIcon();
}

// SVG icon used for empty or error states.

function getEmptyIcon() {
    return `
        <svg width="44" height="44" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6">
            <circle cx="11" cy="11" r="7"></circle>
            <path d="M20 20l-4.5-4.5"></path>
            <path d="M8 11h6"></path>
        </svg>
    `;
}

function beefIcon() {
    return `
        <svg xmlns="http://www.w3.org/2000/svg"
        width="24"
        height="24"
        viewBox="0 0 24 24"
        fill="none"
        stroke="currentColor"
        stroke-width="2"
        stroke-linecap="round"
        stroke-linejoin="round"
        class="lucide lucide-beef-icon lucide-beef">
        <path d="M16.4 13.7A6.5 6.5 0 1 0 6.28 6.6c-1.1 3.13-.78 3.9-3.18 6.08A3 3 0 0 0 5 18c4 0 8.4-1.8 11.4-4.3"/>
        <path d="m18.5 6 2.19 4.5a6.48 6.48 0 0 1-2.29 7.2C15.4 20.2 11 22 7 22a3 3 0 0 1-2.68-1.66L2.4 16.5"/>
        <circle cx="12.5" cy="8.5" r="2.5"/></svg>
    `;
}

function veganIcon() {
    return `
        <svg xmlns="http://www.w3.org/2000/svg"
        width="24"
        height="24"
        viewBox="0 0 24 24"
        fill="none"
        stroke="currentColor"
        stroke-width="2"
        stroke-linecap="round"
        stroke-linejoin="round"
        class="lucide lucide-vegan-icon lucide-vegan">
        <path d="M16 8q6 0 6-6-6 0-6 6"/><path d="M17.41 3.59a10 10 0 1 0 3 3"/>
        <path d="M2 2a26.6 26.6 0 0 1 10 20c.9-6.82 1.5-9.5 4-14"/></svg>
    `;
}

function eggIcon() {
    return `
        <svg xmlns="http://www.w3.org/2000/svg"
        width="24"
        height="24"
        viewBox="0 0 24 24"
        fill="none"
        stroke="currentColor"
        stroke-width="2"
        stroke-linecap="round"
        stroke-linejoin="round"
        class="lucide lucide-egg-icon lucide-egg">
        <path d="M12 2C8 2 4 8 4 14a8 8 0 0 0 16 0c0-6-4-12-8-12"/></svg>
    `;
}

function wheatOffIcon() {
    return `
        <svg xmlns="http://www.w3.org/2000/svg"
        width="24"
        height="24"
        viewBox="0 0 24 24"
        fill="none"
        stroke="currentColor"
        stroke-width="2"
        stroke-linecap="round"
        stroke-linejoin="round"
        class="lucide lucide-wheat-off-icon lucide-wheat-off">
        <path d="m2 22 10-10"/><path d="m16 8-1.17 1.17"/>
        <path d="M3.47 12.53 5 11l1.53 1.53a3.5 3.5 0 0 1 0 4.94L5 19l-1.53-1.53a3.5 3.5 0 0 1 0-4.94Z"/>
        <path d="m8 8-.53.53a3.5 3.5 0 0 0 0 4.94L9 15l1.53-1.53c.55-.55.88-1.25.98-1.97"/>
        <path d="M10.91 5.26c.15-.26.34-.51.56-.73L13 3l1.53 1.53a3.5 3.5 0 0 1 .28 4.62"/>
        <path d="M20 2h2v2a4 4 0 0 1-4 4h-2V6a4 4 0 0 1 4-4Z"/>
        <path d="M11.47 17.47 13 19l-1.53 1.53a3.5 3.5 0 0 1-4.94 0L5 19l1.53-1.53a3.5 3.5 0 0 1 4.94 0Z"/>
        <path d="m16 16-.53.53a3.5 3.5 0 0 1-4.94 0L9 15l1.53-1.53a3.49 3.49 0 0 1 1.97-.98"/>
        <path d="M18.74 13.09c.26-.15.51-.34.73-.56L21 11l-1.53-1.53a3.5 3.5 0 0 0-4.62-.28"/>
        <line x1="2" x2="22" y1="2" y2="22"/></svg>
    `;
}

function vegetarianIcon() {
    return `
<svg xmlns="http://www.w3.org/2000/svg"
width="24"
height="24"
viewBox="0 0 24 24"
fill="none"
stroke="currentColor"
stroke-width="2"
stroke-linecap="round"
stroke-linejoin="round"
class="lucide lucide-beef-off-icon lucide-beef-off">
<path d="M11.771 6.109a2.5 2.5 0 0 1 3.12 3.12"/>
<path d="M17.852 12.185a6.5 6.5 0 0 0-9.035-9.04"/>
<path d="M18.013 18.013C15.029 20.349 10.831 22 7 22a3 3 0 0 1-2.68-1.66L2.4 16.5"/>
<path d="m18.5 6 2.19 4.5a6.48 6.48 0 0 1-.139 4.393"/><path d="m2 2 20 20"/>
<path d="M6.355 6.37a7 7 0 0 0-.075.23c-1.1 3.13-.78 3.9-3.18 6.08A3 3 0 0 0 5 18c3.356 0 6.993-1.267 9.85-3.151"/></svg>
`;
}

function defaultIcon() {
    return `
        <svg width="46" height="46" viewBox="0 0 24 24"
            fill="none" stroke="currentColor" stroke-width="1.6">
            <circle cx="12" cy="12" r="8"/>
            <path d="M8 12h8"/>
        </svg>
    `;
}