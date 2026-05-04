// home.js — Renderiza a página inicial

async function renderHome() {
    const app = document.getElementById('app');

    app.innerHTML = `
        <section class="hero">
            <h1>What are we cooking today, Padawan?</h1>
            <p>Recipes from distant galaxies, weekly plans and nutritionist consultations — all in one place.</p>
        </section>

        <section class="home-grid">
            <div class="home-card" id="card-recipes">
                <h3>Recipes</h3>
                <p>Explore the galactic catalogue</p>
            </div>
            <div class="home-card" id="card-plan">
                <h3>Weekly Plan</h3>
                <p>Organise your meals for 7 days</p>
            </div>
            <div class="home-card" id="card-chat">
                <h3>R2-Chat</h3>
                <p>Ask R2-D2 anything</p>
            </div>
            <div class="home-card" id="card-profile">
                <h3>Profile</h3>
                <p>Your galactic identity</p>
            </div>
        </section>

        <section class="featured">
            <h2>FEATURED RECIPES</h2>
            <div id="featured-list">
                <p class="loading">Loading recipes...</p>
            </div>
        </section>
    `;

    // adiciona eventos de navegação aos cards
    initHome();

    // vai buscar as receitas ao backend
    await loadFeaturedRecipes();
}

// Inicialização dos eventos 

function initHome() {
    document.getElementById('card-recipes').addEventListener('click', () => navigate('recipes'));
    document.getElementById('card-plan').addEventListener('click', () => navigate('plan'));
    document.getElementById('card-chat').addEventListener('click', () => navigate('chat'));
    document.getElementById('card-profile').addEventListener('click', () => navigate('profile'));
}

//  Receitas em destaque 

async function loadFeaturedRecipes() {
    const container = document.getElementById('featured-list');
    if (!container) return;

    try {
        // vai buscar todas as receitas ao backend
        const recipes = await RecipeAPI.getAll();

        // guarda em cache para outras páginas usarem
        App.recipes = recipes;

        // mostra só as primeiras 3
        const featured = recipes.slice(0, 3);

        container.innerHTML = featured.map(recipe => `
            <div class="featured-card" onclick="navigate('recipes', { recipeId: ${recipe.id} })">
                <div class="featured-card__info">
                    <h4>${recipe.name}</h4>
                    <span>${recipe.preparationTime} min · ${formatDifficulty(recipe.difficultyLevel)}</span>
                </div>
            </div>
        `).join('');

    } catch (e) {
        // se o backend não estiver disponível usa dados estáticos como fallback
        console.warn('Could not load recipes from backend, using fallback:', e.message);
        container.innerHTML = `
            <div class="featured-card" onclick="navigate('recipes')">
                <div class="featured-card__info"><h4>Bantha Stew</h4><span>60 min · Medium</span></div>
            </div>
            <div class="featured-card" onclick="navigate('recipes')">
                <div class="featured-card__info"><h4>Dagobah Soup</h4><span>30 min · Easy</span></div>
            </div>
            <div class="featured-card" onclick="navigate('recipes')">
                <div class="featured-card__info"><h4>Coruscant Noodles</h4><span>20 min · Easy</span></div>
            </div>
        `;
    }
}

/**
 * Formata o nível de dificuldade em inglês
 * @param {string} difficulty
 */
function formatDifficulty(difficulty) {
    const map = {
        'EASY':   'Easy',
        'MEDIUM': 'Medium',
        'HARD':   'Hard'
    };
    return map[difficulty] || difficulty;
}