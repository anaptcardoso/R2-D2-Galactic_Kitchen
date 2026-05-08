// ============================================================
// plan.js — Weekly Plan page
// Uses PlanAPI and NutritionistAPI from api.js and App.currentUser
// ============================================================

// Generates the page HTML and initializes the events
function renderPlan(params = {}) {
  const app = document.getElementById('main-content');
  app.innerHTML = `
        <div class="hero">
            <h1>R2-D2 Weekly Plan AI</h1>
            <p>Your AI-powered weekly meal planner</p>
        </div>

        <!-- Tab navigation -->
        <nav class="nutrition-tabs" role="tablist">
            <button class="tab-btn active" data-tab="current" role="tab">Current Plan</button>
            <button class="tab-btn" data-tab="generate" role="tab">Generate Plan</button>
            <button class="tab-btn" data-tab="add" role="tab">Add Recipe</button>
        </nav>

        <!-- TAB: User's current plan -->
        <section class="tab-content" id="tab-current" role="tabpanel">
            <ul class="plan-recipe-list" id="current-plan-result">
                <li class="plan-loading-msg">Loading your plan...</li>
            </ul>
        </section>

        <!-- TAB: Generate weekly plan with AI -->
        <section class="tab-content hidden" id="tab-generate" role="tabpanel">
            <section class="chat-messages-area" id="plan-messages" aria-live="polite">
                <article class="chat-msg bot">
                    Hello${App.currentUser ? ', ' + App.currentUser.firstName : ''}!
                    I'm R2-D2, your AI meal planner. Tell me your goals! 
                </article>
            </section>
            <div class="chat-input-area">
                <input type="text" id="plan-input"
                    placeholder="Ex: I want to lose weight, high protein, no gluten" />
                <button type="button" id="plan-send">Generate</button>
            </div>
        </section>

        <!-- TAB: Add recipe to existing plan -->
        <section class="tab-content hidden" id="tab-add" role="tabpanel">
            <form class="nutrition-form" onsubmit="return false;">
                <input type="number" id="plan-id-input"
                    placeholder="Plan ID" />
                <input type="number" id="recipe-id-input"
                    placeholder="Recipe ID" />
                <button type="button" id="add-recipe-btn">Add Recipe</button>
            </form>
            <div id="add-result" class="result-box hidden"></div>
        </section>
    `;

  initPlan();
}

// Initializes events and API calls
function initPlan() {

  // ── Tab navigation ────────────────────────────────────────
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

  // ── Tab 1: Loads the user's current plan ──────────────────
  loadCurrentPlan();

  async function loadCurrentPlan() {
    const list = document.getElementById('current-plan-result');
    try {
      const userId = App.currentUser?.id || 1;
      const plans  = await PlanAPI.getByUser(userId);

      // Checks if there are plans for the user
      if (!plans || plans.length === 0) {
        list.innerHTML = `
                    <li class="plan-empty-msg">
                        No weekly plan found. Go to <strong>Generate Plan</strong> to create one! 
                    </li>
                `;
        return;
      }

      // Shows the most recent plan
      const latest = plans[plans.length - 1];
      list.innerHTML = `
                <li class="plan-week-header">
                    <strong>Week starting: ${latest.weekStart}</strong>
                </li>
                ${renderPlanRecipes(latest.recipes)}
            `;
    } catch (e) {
      list.innerHTML = `<li class="plan-error-msg">Error loading plan: ${e.message}</li>`;
    }
  }

  // ── Tab 2: Generate weekly plan through AI ────────────────
  const planMessages = document.getElementById('plan-messages');
  const planInput    = document.getElementById('plan-input');

  async function sendPlanRequest() {
    const message = planInput.value.trim();
    if (!message) return;

    appendPlanMessage(planMessages, 'user', message);
    planInput.value = '';
    appendPlanMessage(planMessages, 'bot', 'R2-D2 AI is generating your plan...', 'plan-loading');

    try {
      const response = await NutritionistAPI.suggestMealPlan({ message });
      document.getElementById('plan-loading')?.remove();
      appendPlanMessage(planMessages, 'bot', response.plan || response.message || JSON.stringify(response));
    } catch (e) {
      document.getElementById('plan-loading')?.remove();
      appendPlanMessage(planMessages, 'bot', `Error: ${e.message}`);
    }
  }

  document.getElementById('plan-send')
      .addEventListener('click', sendPlanRequest);
  planInput.addEventListener('keypress', e => {
    if (e.key === 'Enter') sendPlanRequest();
  });

  // ── Tab 3: Add recipe to an existing plan ─────────────────
  document.getElementById('add-recipe-btn').addEventListener('click', async () => {
    const planId   = document.getElementById('plan-id-input').value;
    const recipeId = document.getElementById('recipe-id-input').value;
    const result   = document.getElementById('add-result');

    // Validates if the fields are filled in
    if (!planId || !recipeId) {
      result.textContent = 'Please fill in both Plan ID and Recipe ID.';
      result.classList.remove('hidden');
      return;
    }

    result.textContent = 'Adding recipe to plan...';
    result.classList.remove('hidden');

    try {
      await PlanAPI.addRecipe(Number(planId), Number(recipeId));
      result.textContent = 'Recipe added to plan successfully!';
    } catch (e) {
      result.textContent = `Error: ${e.message}`;
    }
  });
}

// ── Helpers ───────────────────────────────────────────────────────────────────

/**
 * Renders the list of recipes in a weekly plan
 * @param {Array} recipes - list of recipes in the plan
 * @returns {string} - semantic HTML with the recipes as a list
 */
function renderPlanRecipes(recipes) {
  if (!recipes || recipes.length === 0) {
    return '<li class="plan-empty-msg">No recipes in this plan yet. Add some!</li>';
  }
  return recipes.map(r => `
        <li class="plan-recipe-item">
            <span class="plan-recipe-name">${r.name}</span>
            <span class="plan-recipe-type">${r.mealType || ''}</span>
        </li>
    `).join('');
}

/**
 * Adds a message to the weekly plan chat
 * @param {HTMLElement} container - message container
 * @param {string}      type      - 'user' | 'bot'
 * @param {string}      text      - message text
 * @param {string}      id        - optional ID for later removal
 */
function appendPlanMessage(container, type, text, id = '') {
  const article = document.createElement('article');
  article.className = `chat-msg ${type}`;
  if (id) article.id = id;
  article.textContent = text;
  container.appendChild(article);
  container.scrollTop = container.scrollHeight;
}