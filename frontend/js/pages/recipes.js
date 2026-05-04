// recipes.js — Renderiza a página de receitas

async function renderRecipes(params = {}) {
    const app = document.getElementById('app');

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

    // inicializa a pesquisa e os filtros
    initRecipes();

    // vai buscar as receitas ao backend
    await loadRecipes(params);
}

// ── Inicialização dos eventos ─────────────────────────────────────────────────

function initRecipes() {
    // evento de pesquisa — dispara quando o utilizador escreve
    document.getElementById('recipe-search').addEventListener('input', (e) => {
        App.recipeSearch = e.target.value;
        filterAndRenderRecipes();
    });

    // eventos dos botões de filtro
    document.querySelectorAll('.filter-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            // remove a classe active de todos os botões
            document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
            // adiciona a classe active ao botão clicado
            btn.classList.add('active');
            // guarda o filtro activo
            App.recipeFilter = btn.dataset.filter;
            filterAndRenderRecipes();
        });
    });
}

// Carrega as receitas do backend

async function loadRecipes(params = {}) {
    try {
        // se já temos receitas em cache não vai ao backend outra vez
        if (!App.recipes || App.recipes.length === 0) {
            App.recipes = await RecipeAPI.getAll();
        }

        filterAndRenderRecipes();

        // se chegou com um recipeId nos params, abre esse modal directamente
        if (params.recipeId) {
            const recipe = App.recipes.find(r => r.id === params.recipeId);
            if (recipe) openRecipeModal(recipe);
        }

    } catch (e) {
        // se o backend não estiver disponível mostra mensagem de erro
        console.warn('Could not load recipes from backend:', e.message);
        document.getElementById('recipes-grid').innerHTML = `
            <p class="error">Could not load recipes. Please try again later.</p>
        `;
    }
}

// Filtra e renderiza as receitas 

function filterAndRenderRecipes() {
    const grid = document.getElementById('recipes-grid');
    if (!grid) return;

    let filtered = App.recipes || [];

    // aplica o filtro de dieta ou dificuldade
    if (App.recipeFilter && App.recipeFilter !== 'all') {
        if (App.recipeFilter === 'EASY' || App.recipeFilter === 'HARD') {
            // filtra por dificuldade
            filtered = filtered.filter(r => r.difficultyLevel === App.recipeFilter);
        } else {
            // filtra por tipo de dieta
            filtered = filtered.filter(r => r.dietTypes && r.dietTypes.includes(App.recipeFilter));
        }
    }

    // aplica a pesquisa por nome
    if (App.recipeSearch && App.recipeSearch.trim() !== '') {
        const search = App.recipeSearch.toLowerCase();
        filtered = filtered.filter(r => r.name.toLowerCase().includes(search));
    }

    // se não há receitas para mostrar
    if (filtered.length === 0) {
        grid.innerHTML = `<p class="empty">No recipes found.</p>`;
        return;
    }

    // renderiza os cards das receitas
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

// ── Funções auxiliares ────────────────────────────────────────────────────────

/**
 * Formata o tipo de dieta em inglês legível
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
 * Devolve a classe CSS com base na dificuldade para colorir o badge
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
 * Corta um texto para um número máximo de caracteres
 * @param {string} text
 * @param {number} max
 */
function truncate(text, max) {
    if (!text) return '';
    return text.length > max ? text.slice(0, max) + '...' : text;
}