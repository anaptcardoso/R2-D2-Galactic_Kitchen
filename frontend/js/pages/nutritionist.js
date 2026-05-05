// ============================================================
// nutritionist.js — Nutritionist page
// Uses NutritionistAPI from api.js and App.currentUser
// ============================================================

function renderNutritionist(params = {}) {
    const app = document.getElementById('app');

    app.innerHTML = `
        <div class="hero">
            <h1>🥗 Nutricionista R2-D2</h1>
            <p>O teu assistente de nutrição personalizado</p>
        </div>

        <div class="nutrition-tabs">
            <button class="tab-btn active" data-tab="consult">Consulta</button>
            <button class="tab-btn" data-tab="analyse">Analisar Alimento</button>
            <button class="tab-btn" data-tab="mealplan">Plano Alimentar</button>
        </div>

        <!-- TAB: Consulta -->
        <div class="tab-content" id="tab-consult">
            <div class="chat-messages" id="chat-messages">
                <div class="chat-msg bot">
                    Olá${App.currentUser ? ', ' + App.currentUser.firstName : ''}! 
                    Sou o nutricionista R2-D2. Como te posso ajudar? 🤖
                </div>
            </div>
            <div class="chat-input-area">
                <input type="text" id="consult-input" 
                    placeholder="Ex: Quantas proteínas devo comer por dia?" />
                <button id="consult-send">Enviar</button>
            </div>
        </div>

        <!-- TAB: Analisar Alimento -->
        <div class="tab-content hidden" id="tab-analyse">
            <div class="nutrition-form">
                <input type="text" id="food-input" 
                    placeholder="Ex: 100g de frango grelhado" />
                <button id="food-send">Analisar</button>
            </div>
            <div id="food-result" class="result-box hidden"></div>
        </div>

        <!-- TAB: Plano Alimentar -->
        <div class="tab-content hidden" id="tab-mealplan">
            <div class="nutrition-form">
                <input type="number" id="calories-input" 
                    placeholder="Calorias diárias (ex: 2000)" />
                <select id="diet-select">
                    <option value="">Tipo de dieta</option>
                    <option value="VEGAN">Vegan</option>
                    <option value="VEGETARIAN">Vegetariano</option>
                    <option value="GLUTEN_FREE">Sem Glúten</option>
                    <option value="HIGH_PROTEIN">Alto Proteína</option>
                </select>
                <button id="mealplan-send">Gerar Plano</button>
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
                .forEach(b => b.classList.remove('active'));
            document.querySelectorAll('.tab-content')
                .forEach(c => c.classList.add('hidden'));
            btn.classList.add('active');
            document.getElementById(`tab-${btn.dataset.tab}`)
                .classList.remove('hidden');
        });
    });

    // ── Tab 1: Consulta ───────────────────────────────────────
    const messages     = document.getElementById('chat-messages');
    const consultInput = document.getElementById('consult-input');

    async function sendConsult() {
        const question = consultInput.value.trim();
        if (!question) return;

        appendMessage(messages, 'user', question);
        consultInput.value = '';
        appendMessage(messages, 'bot', 'A pensar... 🤖', 'loading-msg');

        try {
            const userId = App.currentUser?.id || 1;
            const res = await NutritionistAPI.consult(userId, { message: question });
            document.getElementById('loading-msg')?.remove();
            appendMessage(messages, 'bot', res.answer || res.message || JSON.stringify(res));
        } catch (e) {
            document.getElementById('loading-msg')?.remove();
            appendMessage(messages, 'bot', `Erro: ${e.message}`);
        }
    }

    document.getElementById('consult-send')
        .addEventListener('click', sendConsult);
    consultInput.addEventListener('keypress', e => {
        if (e.key === 'Enter') sendConsult();
    });

    // ── Tab 2: Analisar Alimento ──────────────────────────────
    document.getElementById('food-send').addEventListener('click', async () => {
        const food   = document.getElementById('food-input').value.trim();
        const result = document.getElementById('food-result');
        if (!food) return;

        result.textContent = 'A analisar... 🔍';
        result.classList.remove('hidden');

        try {
            const res = await NutritionistAPI.analyseFood({ food });
            result.innerHTML = `
                <strong>${food}</strong><br><br>
                ${res.analysis || res.message || JSON.stringify(res)}
            `;
        } catch (e) {
            result.textContent = `Erro: ${e.message}`;
        }
    });

    // ── Tab 3: Plano Alimentar ────────────────────────────────
    document.getElementById('mealplan-send').addEventListener('click', async () => {
        const calories = document.getElementById('calories-input').value;
        const diet     = document.getElementById('diet-select').value;
        const result   = document.getElementById('mealplan-result');

        result.textContent = 'A gerar plano... 🗓️';
        result.classList.remove('hidden');

        try {
            const res = await NutritionistAPI.suggestMealPlan({
                calories: Number(calories),
                dietType: diet
            });
            result.innerHTML = `
                <strong>O teu plano alimentar:</strong><br><br>
                ${res.plan || res.message || JSON.stringify(res)}
            `;
        } catch (e) {
            result.textContent = `Erro: ${e.message}`;
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