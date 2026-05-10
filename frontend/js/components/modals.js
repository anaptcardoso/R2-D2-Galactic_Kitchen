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
                <h2>${recipe.name}</h2>
                <p class="modal-desc">${recipe.description || ''}</p>
                <div class="recipe-story">
                    ${getRecipeStory(recipe)}
                </div>
                <div class="modal-meta">
                    <span>${recipe.preparationTime} Min</span>
                    <span>${recipe.servings} People</span>
                    <span>${recipe.calories} Kcal</span>
                    <span>P: ${recipe.protein}g</span>
                    <span>H: ${recipe.carbs}g</span>
                    <span>G: ${recipe.fat}g</span>
                </div>
                ${recipe.ingredients && recipe.ingredients.length > 0 ? `
                    <h3>Ingredients</h3>
                    <ul class="ingredients-list">
                        ${recipe.ingredients.map(i => `<li>${i.quantity} ${i.unit} ${i.name}</li>`).join('')}
                    </ul>` : ''}

                ${recipe.steps ? `
                    <h3>Preparation</h3>
                    <ol class="steps-list">
                        ${(Array.isArray(recipe.steps) 
                            ? recipe.steps 
                            : recipe.steps.split('\n').filter(s => s.trim())
                        ).map(s => `<li>${s.replace(/^\d+\.\s*/, '')}</li>`).join('')}
                    </ol>` : ''}

                ${recipe.tip ? `<div class="recipe-tip">${recipe.tip}</div>` : ''}
            </div>
        `;

        openModal();
    } catch (error) {
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
                        ${planet.toUpperCase()}
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

// Returns a short Star Wars themed story for each recipe
function getRecipeStory(recipe) {
    const name = (recipe.name || '').toLowerCase();

    if (name.includes('bantha')) {
        return 'Beep boop! R2-D2 first discovered this stew on Tatooine, where desert travellers needed a warm and filling meal after crossing the dunes under the twin suns.';
    }

    if (name.includes('coruscant')) {
        return 'Whirr! This dish reminds R2-D2 of the busy food markets of Coruscant, where flavours from across the galaxy are mixed into fast, colourful meals.';
    }

    if (name.includes('dagobah')) {
        return 'Beep beep! Inspired by the misty swamps of Dagobah, this recipe feels earthy, green and mysterious — exactly the kind of meal Master Yoda might enjoy.';
    }

    if (name.includes('ewok')) {
        return 'Excited beeping! R2-D2 learned this forest-style recipe during a celebration on Endor, surrounded by Ewoks, campfires and victory music.';
    }
    if (name.includes('mandalorian')) {
        return 'This recipe reminds R2-D2 of Mandalorian warriors: strong, bold and practical, made for long journeys across the galaxy.';
    }

    if (name.includes('mediterranean')) {
        return 'Beep boop! R2-D2 says this fresh bowl feels like something served on Naboo, with bright flavours, balanced ingredients and elegant presentation.';
    }

    if (name.includes('pancake')) {
        return 'R2-D2 stores this breakfast recipe in his comfort-food database. It is simple, warm and perfect before starting a long mission.';
    }

    if (name.includes('avocado')) {
        return 'Whirr! This light recipe reminds R2-D2 of peaceful mornings on Naboo, where fresh ingredients are enjoyed slowly before the day begins.';
    }

    if (name.includes('taco')) {
        return 'Beep! R2-D2 imagines this recipe being shared in a lively spaceport cantina, full of travellers, pilots and smugglers from every system.';
    }

    if (name.includes('omelette')) {
        return 'This quick and practical dish feels like something prepared on a rebel base before an early mission briefing.';
    }
    if (name.includes('salmon')) {
        return 'R2-D2 connects this recipe with the lakes of Naboo, where fresh and balanced meals match the planet’s calm and natural beauty.';
    }

    if (name.includes('pasta')) {
        return 'Beep boop! This comforting pasta reminds R2-D2 of crowded Coruscant kitchens, where quick meals keep the city moving.';
    }

    if (name.includes('caesar')) {
        return 'This salad feels like a clean and efficient rebel meal: simple, balanced and ready before the next mission.';
    }

    if (name.includes('lentil')) {
        return 'R2-D2 associates this warm curry with distant outer-rim settlements, where nourishing meals are made from simple ingredients.';
    }

    if (name.includes('smoothie')) {
        return 'Beep! A fast energy boost for pilots, rebels and hungry droids preparing for hyperspace travel.';
    }

    if (name.includes('burger')) {
        return 'This recipe feels like cantina food from a busy space station — satisfying, practical and full of character.';
    }

    if (name.includes('parfait') || name.includes('yogurt')) {
        return 'R2-D2 files this under light mission fuel: fresh, balanced and easy to prepare before a calm morning.';
    }

    if (name.includes('shrimp')) {
        return 'Whirr! This stir fry reminds R2-D2 of the fast-paced kitchens of Coruscant, where meals are cooked quickly for busy travellers.';
    }

    if (name.includes('tomato')) {
        return 'This soup feels like a peaceful meal from Naboo, warm and simple, perfect after a long day of diplomacy.';
    }

    if (name.includes('quinoa')) {
        return 'R2-D2 marks this as a clean and balanced rebel meal, ideal for keeping energy levels stable during missions.';
    }
    if (name.includes('pizza')) {
        return 'Beep boop! This recipe sounds like something shared in a rebel hangar after a successful mission.';
    }

    if (name.includes('risotto')) {
        return 'This creamy dish reminds R2-D2 of elegant Naboo banquets, where food is carefully prepared and beautifully served.';
    }

    if (name.includes('sweet potato')) {
        return 'R2-D2 links this recipe to simple survival meals from outer-rim worlds: warm, reliable and naturally sweet.';
    }

    if (name.includes('tuna')) {
        return 'This quick sandwich feels like something packed for a pilot before leaving the hangar in a hurry.';
    }

    if (
        name.includes('brownie') ||
        name.includes('cheesecake') ||
        name.includes('cookies') ||
        name.includes('pie') ||
        name.includes('tart') ||
        name.includes('pudding') ||
        name.includes('ice cream')
    ) {
        return 'Excited beeping! R2-D2 saves this dessert for celebration moments — the kind of sweet reward served after a successful rebel victory.';
    }

    if (
        name.includes('muffin') ||
        name.includes('energy') ||
        name.includes('hummus') ||
        name.includes('chickpeas') ||
        name.includes('nuts') ||
        name.includes('toast') ||
        name.includes('crackers')
    ) {
        return 'Beep boop! R2-D2 classifies this as mission snack fuel: easy to prepare, easy to carry and perfect for galactic adventures.';
    }

    return 'Beep boop! R2-D2 added this recipe to his galactic archive after discovering that even simple ingredients can become a meal worthy of a rebel mission.';
}

