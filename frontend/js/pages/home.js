// home.js — Renders the home page of the SPA

async function renderHome() {
    const app = document.getElementById('main-content');

    app.innerHTML = `
        <section class="hero">
            <div class="hero-eyebrow">// GALACTIC CULINARY SYSTEM v1.0</div>

            <h1>What are we cooking today, <span>Padawan?</span></h1>

            <p>
                Recipes from distant galaxies, weekly plans and nutritionist
                consultations — all in one place.
            </p>

            <div class="hero-actions">
                <button class="btn-cyan" id="hero-recipes">EXPLORE RECIPES</button>
                <button class="btn-gold" id="hero-plan">BUILD WEEKLY PLAN</button>
            </div>
        </section>

        <section class="menu-grid home-menu-grid">
            <article class="menu-card" id="card-recipes">
                <div class="menu-icon cyan-icon">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8">
                        <path d="M3 11h18M5 11V7a2 2 0 012-2h10a2 2 0 012 2v4M5 11l1 9h12l1-9"/>
                    </svg>
                </div>
                <h3>Recipes</h3>
                <p>Explore the galactic catalogue and discover meals from distant planets.</p>
            </article>

            <article class="menu-card" id="card-plan">
                <div class="menu-icon gold-icon">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8">
                        <rect x="3" y="5" width="18" height="16" rx="2"/>
                        <path d="M3 9h18M8 3v4M16 3v4"/>
                    </svg>
                </div>
                <h3>Weekly Plan</h3>
                <p>Organise your meals for 7 days with a balanced galactic food mission.</p>
            </article>

            <article class="menu-card" id="card-chat">
                <div class="menu-icon green-icon">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8">
                        <path d="M21 15a2 2 0 01-2 2H7l-4 4V5a2 2 0 012-2h14a2 2 0 012 2v10z"/>
                    </svg>
                </div>
                <h3>R2-Chat</h3>
                <p>Ask R2-D2 anything about recipes, ingredients or meal planning.</p>
            </article>

            <article class="menu-card" id="card-profile">
                <div class="menu-icon purple-icon">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8">
                        <circle cx="12" cy="8" r="4"/>
                        <path d="M4 21c0-4 4-7 8-7s8 3 8 7"/>
                    </svg>
                </div>
                <h3>Profile</h3>
                <p>Manage your galactic identity, goals, allergies and preferences.</p>
            </article>
        </section>

        <section class="nutri-banner" id="banner-nutri">
            <div class="nutri-icon green-icon">
                <svg width="26" height="26" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8">
                    <path d="M12 2C8 5 6 9 6 13a6 6 0 0012 0c0-4-2-8-6-11z"/>
                    <path d="M12 13v8"/>
                </svg>
            </div>

            <div>
                <div class="nutri-title-row">
                    <span class="nutri-title">TALK TO A NUTRITIONIST</span>
                    <span class="badge-new">NEW</span>
                </div>
                <p class="nutri-sub">
                    Get personalised advice from our galactic nutrition expert.
                </p>
            </div>

            <span class="arrow">→</span>
        </section>

        <section class="spotlight-section">
            <div class="section-label">QUICK ACCESS</div>

            <div class="spotlight-grid">
                <article class="spot-card" id="spot-favorites">
                    <div class="spot-emoji gold-icon">
                        <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8">
                            <polygon points="12 2 15 9 22 9.5 17 14.5 18.5 22 12 18 5.5 22 7 14.5 2 9.5 9 9"/>
                        </svg>
                    </div>
                    <div>
                        <h4>Favorites</h4>
                        <p>Saved recipes</p>
                    </div>
                </article>

                <article class="spot-card" id="spot-shopping">
                    <div class="spot-emoji cyan-icon">
                        <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8">
                            <path d="M3 4h2l2 12h12l2-8H7"/>
                            <circle cx="9" cy="20" r="1.5"/>
                            <circle cx="17" cy="20" r="1.5"/>
                        </svg>
                    </div>
                    <div>
                        <h4>Shopping List</h4>
                        <p>This week</p>
                    </div>
                </article>

                <article class="spot-card" id="spot-stats">
                    <div class="spot-emoji green-icon">
                        <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8">
                            <path d="M3 21h18M6 17v-6M11 17V7M16 17v-9"/>
                        </svg>
                    </div>
                    <div>
                        <h4>Today's Stats</h4>
                        <p>Daily intake</p>
                    </div>
                </article>
            </div>
        </section>

        <section class="featured-section">
            <div class="page-header">
                <h2>FEATURED RECIPES</h2>
                <button class="btn-outline" id="view-all-recipes">VIEW ALL →</button>
            </div>

            <div id="featured-list" class="recipes-grid">
                <div class="loading">
                    <p>LOADING RECIPES...</p>
                </div>
            </div>
        </section>

        <section class="planet-banner" id="banner-planet">
            <div class="planet-left">
                <div class="planet-emoji purple-icon">
                    <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6">
                        <circle cx="12" cy="12" r="9"/>
                        <ellipse cx="12" cy="12" rx="9" ry="3.5"/>
                    </svg>
                </div>

                <div>
                    <div class="planet-title">BROWSE BY PLANET</div>
                    <div class="planet-sub">
                        Discover recipes inspired by planets across the galaxy.
                    </div>
                </div>
            </div>

            <button class="btn-outline">CHOOSE PLANET</button>
        </section>
    `;

    initHome();
    await loadFeaturedRecipes();
}

function initHome() {
    document.getElementById('hero-recipes')?.addEventListener('click', () => navigate('recipes'));
    document.getElementById('hero-plan')?.addEventListener('click', () => navigate('plan'));

    document.getElementById('card-recipes')?.addEventListener('click', () => navigate('recipes'));
    document.getElementById('card-plan')?.addEventListener('click', () => navigate('plan'));

    document.getElementById('card-chat')?.addEventListener('click', () => {
        const chatWindow = document.getElementById('chat-window');
        if (chatWindow) {
            chatWindow.classList.add('open');
        }
    });

    document.getElementById('card-profile')?.addEventListener('click', () => navigate('profile'));
    document.getElementById('banner-nutri')?.addEventListener('click', () => navigate('nutritionist'));
    document.getElementById('banner-planet')?.addEventListener('click', () => navigate('recipes'));

    document.getElementById('spot-favorites')?.addEventListener('click', () => navigate('recipes'));
    document.getElementById('spot-shopping')?.addEventListener('click', () => navigate('plan'));
    document.getElementById('spot-stats')?.addEventListener('click', () => navigate('nutritionist'));

    document.getElementById('view-all-recipes')?.addEventListener('click', () => navigate('recipes'));
}

async function loadFeaturedRecipes() {
    const container = document.getElementById('featured-list');
    if (!container) return;

    try {
        const recipes = await RecipeAPI.getAll();

        App.recipes = recipes;

        const featured = recipes.slice(0, 3);

        if (!featured.length) {
            container.innerHTML = `
                <div class="empty-state">
                    NO RECIPES FOUND
                </div>
            `;
            return;
        }

        container.innerHTML = featured.map(renderFeaturedCard).join('');
    } catch (error) {
        console.warn('Could not load recipes from backend, using fallback:', error);

        const fallbackRecipes = [
            {
                id: null,
                name: 'Bantha Stew',
                description: 'A warm and powerful stew from the outer rim.',
                preparationTime: 60,
                difficultyLevel: 'MEDIUM',
                calories: 520
            },
            {
                id: null,
                name: 'Coruscant Noodles',
                description: 'Fast, bright and perfect for a city planet lunch.',
                preparationTime: 20,
                difficultyLevel: 'EASY',
                calories: 410
            },
            {
                id: null,
                name: 'Dagobah Swamp Soup',
                description: 'A green, mysterious and surprisingly healthy soup.',
                preparationTime: 30,
                difficultyLevel: 'EASY',
                calories: 280
            }
        ];

        container.innerHTML = fallbackRecipes.map(renderFeaturedCard).join('');
    }
}

function renderFeaturedCard(recipe) {
    const difficulty = recipe.difficultyLevel || 'EASY';
    const diffClass = difficulty.toLowerCase();
    const time = recipe.preparationTime || recipe.time || 30;
    const calories = recipe.calories || recipe.kcal || '---';

    const onclick = recipe.id
        ? `navigate('recipes', { recipeId: ${recipe.id} })`
        : `navigate('recipes')`;

    return `
        <article class="recipe-card" onclick="${onclick}">
            <div class="recipe-banner">
                <div class="recipe-line-icon cyan-icon">
                    ${getRecipeIcon(recipe.name)}
                </div>
                <span class="diff-badge diff-${diffClass}">
                    ${formatDifficulty(difficulty)}
                </span>
            </div>

            <div class="recipe-body">
                <h3 class="recipe-name">${recipe.name}</h3>

                <p class="recipe-desc">
                    ${recipe.description || 'A galactic recipe ready for your next mission.'}
                </p>

                <div class="recipe-tags">
                    <span class="tag tag-diet">GALACTIC</span>
                    <span class="tag tag-goal">MISSION</span>
                </div>

                <div class="recipe-footer">
                    <span class="recipe-meta">${time} min</span>
                    <span class="recipe-cal">${calories} kcal</span>
                </div>
            </div>
        </article>
    `;
}

function formatDifficulty(difficulty) {
    const map = {
        EASY: 'Easy',
        MEDIUM: 'Medium',
        HARD: 'Hard'
    };

    return map[difficulty] || difficulty || 'Easy';
}

function getRecipeIcon(name = '') {
    const lower = name.toLowerCase();

    if (lower.includes('noodle') || lower.includes('pasta')) {
        return `
            <svg width="42" height="42" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6">
                <path d="M4 10h16"/>
                <path d="M6 10c0 5 3 9 6 9s6-4 6-9"/>
                <path d="M8 6v4M12 6v4M16 6v4"/>
            </svg>
        `;
    }

    if (lower.includes('soup') || lower.includes('stew')) {
        return `
            <svg width="42" height="42" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6">
                <path d="M4 11h16"/>
                <path d="M6 11c0 5 3 8 6 8s6-3 6-8"/>
                <path d="M8 7c1-2 3-2 4 0M13 7c1-2 3-2 4 0"/>
            </svg>
        `;
    }

    return `
        <svg width="42" height="42" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6">
            <circle cx="12" cy="12" r="8"/>
            <path d="M8 12h8M12 8v8"/>
        </svg>
    `;
}