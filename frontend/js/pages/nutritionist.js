// ============================================================
// nutritionist.js — Nutritionist page
// Uses NutritionistAPI from api.js and App.currentUser
// ============================================================

// Renders the nutritionist page with three tabs: Consult, Analyse Food, Meal Plan
async function renderNutritionist(params = {}) {
    const userId = App.currentUser?.id || 1;
    const firstName = App.currentUser?.firstName || '';

    setContent(`
        <div class="page-header">
            <h2>Nutritional Consult</h2>
        </div>

        <!-- Tab buttons --> 
        <div class="nutrition-tabs">
            <button class="tab-btn active" data-tab="consult">Consult</button>
            <button class="tab-btn" data-tab="analyse">Analyze Food</button>
            <button class="tab-btn" data-tab="mealplan">Meal Plan</button>
        </div>

        <!-- TAB: Consult  --> 
        <div class="tab-content" id="tab-consult">
            <div class="nutritionist-layout">
                    <div class="nutri-sidebar">
                        <!-- User nutritional profile -->
                        <div class="nutri-profile-card">
                            <p class="section-label">User Profile</p>
                            <div id="nutri-profile-data">
                                <p class="loading-text">Loading...</p>
                            </div>
                        </div>
                        <!-- Quick action buttons -->
                        <div class="quick-actions">
                            <p class="section-label">Quick actions</p>
                            <button class="quick-btn" data-quick="Analyze my food this week">
                                Weekly analysis
                            </button>
                            <button class="quick-btn" data-quick="Create a personalized meal plan for me">
                                Meal Plan
                            </button>
                            <button class="quick-btn" data-quick="Calculate my ideal macros">
                                Calculate Macros
                            </button>
                            <button class="quick-btn" data-quick="Which recipes are best suited to my profile">
                                Evaluate Recipes
                            </button>
                        </div>              
                    </div>

                    <!-- Chat area -->
                    <div class="nutri-chat">
                        <div class="chat-messages-area" id="chat-messages">
                            <div class="msg>
                                <div class="msg-av nutri-av">NI</div>
                                <div class="msg-bubble nutri-bubble">
                                Active nutritional system.
                                ${firstName ? 'Hello, ' + firstName + '!' : 'Hello!'}
                                Based on your galactic profile.I will provide personalized recommendations.
                                How can I help you today, Padawan?
                            </div>
                        </div>
                    </div>
                    <div class="chat-input-area">
                        <textarea id="consult-input" class="chat-textarea"
                            placeholder="Submit question to the nutririonist..."></textarea>
                        <button class="btn-green" id="consult-send">Send</button>
                    </div>
                </div>
            </div>
        </div>


        <!-- TAB: Analyze Food -->
        <div class="tab-content hidden" id="tab-analyse">
            <div class="holo-card" style="padding:1.5rem;max-width:600px;margin:0 auto">
                <p class="section-label">Nutritional analysis of foods</p>
                <p style="font-size:12px;color:var(--text-dim);margin-bottom:1rem">
                    Describes the food or meal to obtain estimated nutrirional values.
                </p>
                <div style="display:flex;gap:8px;margin-bottom:1rem">
                    <input type="text" id="food-input" class="search-input" style="flex:1;width:auto"
                        placeholder="Ex: 100g grilled chicken">
                    <button class="btn-cyan" id="food-send">Analyze</button>
                </div>
                <div id="food-result" class="result-box hidden"></div>
            </div>            
        </div>

        <!-- TAB: Meal Plan -->
        <div class="tab-content hidden" id="tab-mealplan">
            <div class="holo-card" style="padding:1.5rem;max-width:600px;margin:0 auto">
                <p class="section-label">Generate meal plan</p>
                <p style="font-size:12px;color:var(--text-dim);margin-bottom:1rem">
                    Generate a personalized meal plan based on your preferences and goals.
                </p> 
                <div style="display:grid;grid-template-columns:1fr 1fr;gap:10px;margin-bottom:1rem">             
                    <div class="field">
                        <label=Daily calories</label>
                        <input type="number" id="calories-input" placeholder=" (ex: 2000)">
                    </div>
                    <div class="field">
                        <label="Type of diet"</label>
                        <select id="diet-select">
                            <option value="">No preference</option>
                            <option value="VEGAN">Vegan</option>
                            <option value="VEGETARIAN">Vegetarian</option>
                            <option value="GLUTEN_FREE">Gluten Free</option>
                            <option value="HIGH_PROTEIN">High Protein</option>
                            <option value="KETO">Keto</option>
                            <option value="OMNIVORE">Omnivorous</option>
                        </select>
                    </div>
                    <div class="field">
                        <label>Purpose</label>
                        <input type="text" id="goal-input" placeholder="Ex: Lose weight">
                    </div>
                    <div class="field">
                        <label>Activity Level</label>
                            <select id="activity-select">
                                <option value="sedentary">Sedentary</option>
                                <option value="light">Light</option>
                                <option value="moderate" selected>Moderate</option>
                                <option value="active">Active</option>
                                <option value="very active">Very active</option>
                            </select>
                    </div>
                </div> 
                <button class="btn-cyan" id="mealplan-send" style="width:100%">
                    Generate Plan
                </button>            
                <div id="mealplan-result" class="result-box hidden"></div>
            </div>
        </div>
    `);

    // load the user's nutritional profile and populate the sidebar
    loadNutritionProfile(userId);

    // initialise all event listeners
    initNutritionistEvents(userId);
}

// Fetches the user's nutritional profile and populates the sidebar
async function loadNutritionProfile(userId) {
    try {
        const nutrition = await UserAPI.getNutrition(userId);
        document.getElementById('nutri-profile-data').innerHTML = `
            <div class="stat-row"><span>Weight</span><strong>${nutrition.weight || '—'} KG</strong></div>
            <div class="stat-row"><span>Height</span><strong>${nutrition.height || '—'} CM</strong></div>
            <div class="stat-row"><span>Purpose</span>
                <strong style="font-size:10px">${nutrition.goal || '—'}</strong>
            </div>
            <div class="stat-row"><span>Activity</span>
                <strong style="font-size:10px">${nutrition.activityLevel || '—'}</strong>
            </div>
        `;
    } catch (error) {
        document.getElementById('nutri-profile-data').innerHTML =
            '<p class="error-inline">Data unavavailable</p>';
    }
}


// ── Event listeners ───────────────────────────────────────────────────────────

// Sets up all tab switching, chat and form event listeners
function initNutritionistEvents(userId) {

    // ── Tabs ─────────────────────────────────────────────────
    document.querySelectorAll('.tab-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            // deactivate all tabs
            document.querySelectorAll('.tab-btn')
                .forEach(button => button.classList.remove('active'));
            document.querySelectorAll('.tab-content')
                .forEach(content => content.classList.add('hidden'));
            // activate the clicked tab
            btn.classList.add('active');
            document.getElementById(`tab-${btn.dataset.tab}`)
                .classList.remove('hidden');
        });
    });

    // ── Quick action buttons ──────────────────────────────────────────────────

    document.querySelectorAll('.quick-btn[data-quick]').forEach(btn => {
        btn.addEventListener('click', () => {
            const input = document.getElementById('consult-input');
            if (input) {
                input.value = btn.dataset.quick;
                sendConsultMessage(userId);
            }
        });
    });


    // ── Tab 1: Consult ───────────────────────────────────────
    
    const consultInput = document.getElementById('consult-input');

    // send on button click
    document.getElementById('consult-send')
        .addEventListener('click', () => sendConsultMessage(userId));

    // send on Enter key (without Shift)
    consultInput.addEventListener('keydown', event => {
        if (event.key === 'Enter' && !event.shiftKey) {
            event.preventDefault();
            sendConsultMessage(userId);
        }
    });
    

    // ── Tab 2: Analyze Food ──────────────────────────────
    document.getElementById('food-send').addEventListener('click', async () => {
        const food   = document.getElementById('food-input').value.trim();
        const result = document.getElementById('food-result');
        if (!food) return;

        result.textContent = 'Analyzing...';
        result.classList.remove('hidden');

        try {
            // send as a ChatMessageDTO — the backend expects this format
            const response = await NutritionistAPI.analyseFood({ 
                role: 'user',
                message: food,
                context:'nutrition'
            });
            result.innerHTML = `
                <strong style="color:var(--cyan)">${food}</strong><br><br>
                ${response.message || JSON.stringify(response)}

            `;
        } catch (error) {
            result.textContent = `Error: ${error.message}`;
        }
    });

    // ── Tab 3: Meal Plan ────────────────────────────────
    document.getElementById('mealplan-send').addEventListener('click', async () => {
        
        const result   = document.getElementById('mealplan-result');

        result.textContent = 'Generating plan...';
        result.classList.remove('hidden');

        try {
            // build a NutritionDTO with the form values
            const response = await NutritionistAPI.suggestMealPlan({
                weight: null,
                height: null,
                goal: document.getElementById('goal-input').value || null,
                activityLevel: document.getElementById('activity-select').value || null,
                dietPreferences: document.getElementById('diet-select').value?[document.getElementById('diet-select').value]:[],
                allergies: null
            });
            result.innerHTML = `
                <strong style="color:var(--cyan)">Your eating plan:</strong><br><br>
                ${response.message || JSON.stringify(response)}
            `;
        } catch (error) {
            result.textContent = `Error: ${error.message}`;
        }
    });
}

// ── Chat Helpers ─────────────────────────────────────────────────────────────────

// Sends the consult message to the nutritionist API and displays the response
async function sendConsultMessage(userId) {
    const input    = document.getElementById('consult-input');
    const messages = document.getElementById('chat-messages');
    const message  = input.value.trim();
    if (!message) return;

    // display the user's message
    appendNutriMessage(messages, 'user', message);
    input.value = '';

    // show a loading indicator while waiting for the response
    appendNutriMessage(messages, 'nutri', 'Processing transmission...', 'nutri-loading');

    try {
        const response = await NutritionistAPI.consult(userId, {
            role: 'user',
            message,
            context: 'nutrition'
        });
        // remove the loading indicator and show the real response
        document.getElementById('nutri-loading')?.remove();
        appendNutriMessage(messages, 'nutri', response.message || JSON.stringify(response));
    } catch (error) {
        document.getElementById('nutri-loading')?.remove();
        appendNutriMessage(messages, 'nutri', `Transmission error: ${error.message}`);
    }
}

// Appends a styled message bubble to the chat area
function appendNutriMessage(container, type, text, id = '') {
    const isNutri = type === 'nutri';
    // generate user initials dynamically from the active user
    const userInitials = getUserInitials();
    const userBg       = getAvatarBg(App.currentUser?.firstName || 'A');
    const userBorder   = getAvatarBorder(App.currentUser?.firstName || 'A')
    const div = document.createElement('div');
    div.className = `msg ${isNutri ? '' : 'user-msg'}`;
    if (id) div.id = id;
    div.innerHTML = `
                <div class="msg-av ${isNutri ? 'nutri-av' : 'user-av'}"
            ${!isNutri ? `style="background:${userBg};border-color:${userBorder}"` : ''}>
            ${isNutri ? 'NI' : userInitials}
        </div>

        <div class="msg-bubble ${isNutri ? 'nutri-bubble' : 'user-bubble'}">${text}</div>
    `;
    container.appendChild(div);
    // scroll to the latest message
    container.scrollTop = container.scrollHeight;
}




