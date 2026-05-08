// modals.js — Recipe and planet modals

// Sets up the modal close behaviour when clicking the backdrop
function setupModal() {
    document.getElementById('modal')?.addEventListener('click', event => {
        if (event.target === event.currentTarget) event.currentTarget.classList.remove('open');
    });
}

// Opens the modal overlay
function openModal() {
    document.getElementById('modal').classList.add('open');
}

// Closes the modal overlay
function closeModal() {
    document.getElementById('modal').classList.remove('open');
}

// Fetches a recipe by ID and renders it inside the modal
async function openRecipeModal(recipeId) {
    try {
        const recipe = await RecipeAPI.getById(recipeId);

        document.getElementById('modal-body').innerHTML = `
            <div class="modal-recipe">
                <div style="height:100px;display:flex;align-items:center;justify-content:center;
                    font-size:60px;border-bottom:1px solid var(--border-bright);margin-bottom:16px;
                    filter:drop-shadow(0 0 12px rgba(0,212,255,.3))">
                    ${getRecipeEmoji(recipe.name)}
                </div>
                <h2>${recipe.name}</h2>
                <p class="modal-desc">${recipe.description || ''}</p>
                <div class="modal-meta">
                    <span> ${recipe.preparationTime} Min</span>
                    <span> ${recipe.servings} People</span>
                    <span> ${recipe.calories} Kcal</span>
                    <span> P: ${recipe.protein}Grams</span>
                    <span> H: ${recipe.carbs}Grams</span>
                    <span> G: ${recipe.fat}Grams</span>
                </div>
                ${recipe.ingredients?.length ? `
                    <h3> Ingredients</h3>
                    <ul class="ingredients-list">
                        ${recipe.ingredients.map(ingredient => `<li>${ingredient.quantity} ${ingredient.unit} ${ingredient.name}</li>`).join('')}
                    </ul>` : ''}
                ${recipe.steps?.length ? `
                    <h3> Preparation</h3>
                    <ol class="steps-list">
                        ${recipe.steps.map(step => `<li>${step}</li>`).join('')}
                    </ol>` : ''}
                ${recipe.tip ? `<div class="recipe-tip"> ${recipe.tip}</div>` : ''}
            </div>
        `;

        openModal();
    } catch (error) {
        console.error('Recipe modal error:', error);
        alert('Fail upload recipe');
    }
}

// Planet modal 
// Renders the planet selection modal
function openPlanetModal() {
    const planets = ['Tatooine','Hoth','Dagobah','Coruscant','Endor','Naboo','Mustafar','Alderaan'];
    document.getElementById('modal-body').innerHTML = `
            <div class="planet-modal">
            <h2> Recipe from the planet</h2>
            <p>Select a Star Wars planet — R2-D2 queries SWAPI and creates a themed recipe!</p>
            <div class="planet-grid">
                ${planets.map(planet =>
                    `<button class="planet-btn" onclick="generatePlanetRecipe('${planet}')">
                        ${getPlanetEmoji(planet)} ${planet.toUpperCase()}
                    </button>`
                ).join('')}
            </div>
            <p id="planet-loading"
            style="display:none;margin-top:1rem;font-family:var(--font-display);
            font-size:10px;color:var(--cyan);letter-spacing:2px;animation:blink 1s infinite">
            R2-D2 Processing SWAPI data...
            </p>
            <div id="planet-result"></div>
            </div>
    `;
    openModal();
}

// Fetches planet data from the SWAPI via the AI service and displays the generated recipe
async function generatePlanetRecipe(planet) {
    document.getElementById('planet-loading').style.display = 'block';
    document.getElementById('planet-result').innerHTML = '';

    try {
        const response = await ChatAPI.chat({ role: 'user', message: planet, context: 'planet' });
        document.getElementById('planet-loading').style.display = 'none';
        document.getElementById('planet-result').innerHTML =
            `<div class="planet-recipe-result">${response.message}</div>`;
    } catch (error) {
        document.getElementById('planet-loading').style.display = 'none';
        document.getElementById('planet-result').innerHTML =
            `<p class="error-inline">Transmition failor: ${error.message}</p>`;
    }
}

