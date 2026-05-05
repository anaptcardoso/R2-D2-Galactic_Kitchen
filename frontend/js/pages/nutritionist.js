// ============================================================
// nutritionist.js — Nutritionist page
// Uses NutritionistAPI from api.js and App.currentUser
// ============================================================

function renderNutritionist(params = {}) {
    const app = document.getElementById('app');

    app.innerHTML = `
        <div class="hero">
            <h1>Nutritionist R2-D2</h1>
            <p>Your personalized nutrition assistant</p>
        </div>

        <div class="nutrition-tabs">
            <button class="tab-btn active" data-tab="consult">Consult</button>
            <button class="tab-btn" data-tab="analyse">Analyze Food</button>
            <button class="tab-btn" data-tab="mealplan">Meal Plan</button>
        </div>

        <!-- TAB: Consult -->
        <div class="tab-content" id="tab-consult">
            <div class="chat-messages" id="chat-messages">
                <div class="chat-msg bot">
                    Olá${App.currentUser ? ', ' + App.currentUser.firstName : ''}! 
                    I'm nutritionist R2-D2. How can I help you?
                </div>
            </div>
            <div class="chat-input-area">
                <input type="text" id="consult-input" 
                    placeholder="Ex: How much protein shoul I eat per day?" />
                <button id="consult-send">Send</button>
            </div>
        </div>

        <!-- TAB: Analyze Food -->
        <div class="tab-content hidden" id="tab-analyse">
            <div class="nutrition-form">
                <input type="text" id="food-input" 
                    placeholder="Ex: 100g grilled chicken" />
                <button id="food-send">Analyze</button>
            </div>
            <div id="food-result" class="result-box hidden"></div>
        </div>

        <!-- TAB: Diet Plan -->
        <div class="tab-content hidden" id="tab-mealplan">
            <div class="nutrition-form">
                <input type="number" id="calories-input" 
                    placeholder="Daily calories (ex: 2000)" />
                <select id="diet-select">
                    <option value="">Type of diet</option>
                    <option value="VEGAN">Vegan</option>
                    <option value="VEGETARIAN">Vegetarian</option>
                    <option value="GLUTEN_FREE">Gluten Free</option>
                    <option value="HIGH_PROTEIN">High Protein</option>
                </select>
                <button id="mealplan-send">Generate Plan</button>
            </div>
            <div id="mealplan-result" class="result-box hidden"></div>
        </div>
    `;

    initNutritionistEvents();
}

// ── Event listeners ───────────────────────────────────────────────────────────

function initNutritionistEvents() {

    // ── Tabs ─────────────────────────────────────────────────
    document.querySelectorAll('.tab-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            document.querySelectorAll('.tab-btn')
                .forEach(button => button.classList.remove('active'));
            document.querySelectorAll('.tab-content')
                .forEach(content => content.classList.add('hidden'));
            btn.classList.add('active');
            document.getElementById(`tab-${btn.dataset.tab}`)
                .classList.remove('hidden');
        });
    });

    // ── Tab 1: Consult ───────────────────────────────────────
    const messages     = document.getElementById('chat-messages');
    const consultInput = document.getElementById('consult-input');

    async function sendConsult() {
        const question = consultInput.value.trim();
        if (!question) return;

        appendMessage(messages, 'user', question);
        consultInput.value = '';
        appendMessage(messages, 'bot', 'Thinking...', 'loading-msg');

        try {
            const userId = App.currentUser?.id || 1;
            const res = await NutritionistAPI.consult(userId, { message: question });
            document.getElementById('loading-msg')?.remove();
            appendMessage(messages, 'bot', response.answer || response.message || JSON.stringify(response));
        } catch (error) {
            document.getElementById('loading-msg')?.remove();
            appendMessage(messages, 'bot', `Erro: ${error.message}`);
        }
    }

    document.getElementById('consult-send')
        .addEventListener('click', sendConsult);
    consultInput.addEventListener('keypress', event => {
        if (event.key === 'Enter') sendConsult();
    });

    // ── Tab 2: Analyze Food ──────────────────────────────
    document.getElementById('food-send').addEventListener('click', async () => {
        const food   = document.getElementById('food-input').value.trim();
        const result = document.getElementById('food-result');
        if (!food) return;

        result.textContent = 'Analyzing...';
        result.classList.remove('hidden');

        try {
            const response = await NutritionistAPI.analyseFood({ food });
            result.innerHTML = `
                <strong>${food}</strong><br><br>
                ${response.analysis || response.message || JSON.stringify(response)}
            `;
        } catch (error) {
            result.textContent = `Erro: ${error.message}`;
        }
    });

    // ── Tab 3: Food Plan ────────────────────────────────
    document.getElementById('mealplan-send').addEventListener('click', async () => {
        const calories = document.getElementById('calories-input').value;
        const diet     = document.getElementById('diet-select').value;
        const result   = document.getElementById('mealplan-result');

        result.textContent = 'Generating plan...';
        result.classList.remove('hidden');

        try {
            const response = await NutritionistAPI.suggestMealPlan({
                calories: Number(calories),
                dietType: diet
            });
            result.innerHTML = `
                <strong>Your eating plan:</strong><br><br>
                ${response.plan || response.message || JSON.stringify(response)}
            `;
        } catch (error) {
            result.textContent = `Erro: ${error.message}`;
        }
    });
}

// ── Helper ────────────────────────────────────────────────────────────────────

/**
 * Appends a chat message to the messages container
 * @param {HTMLElement} container
 * @param {string}      type - 'user' | 'bot'
 * @param {string}      text
 * @param {string}      id   - optional element id
 */
function appendMessage(container, type, text, id = '') {
    const div = document.createElement('div');
    div.className = `chat-msg ${type}`;
    if (id) div.id = id;
    div.textContent = text;
    container.appendChild(div);
    container.scrollTop = container.scrollHeight;
}