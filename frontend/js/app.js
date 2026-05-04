// ============================================================
// app.js — Main routing with History API + dynamic avatar
// ============================================================

// ── Global state ──────────────────────────────────────────────────────────────
const App = {
    currentUser:  null,   // currently active user (UserProfileDTO)
    params:       {},     // current route params (e.g. {userId: 1, edit: true})
    recipes:      [],     // cached recipe list
    recipeFilter: 'all',  // active diet filter
    recipeSearch: ''      // active search query
};

// ── Route map ─────────────────────────────────────────────────────────────────
const routes = {
    'home':         renderHome,
    'recipes':      renderRecipes,
    'plan':         renderPlan,
    'nutritionist': renderNutritionist,
    'profiles':     renderProfiles,
    'profile':      renderProfile,
};

// ── navigate() ────────────────────────────────────────────────────────────────

/**
 * Navigates to a page using the History API
 * Updates the URL, the active nav link and renders the page
 * @param {string}  page   - route name (e.g. 'recipes')
 * @param {object}  params - optional route params (e.g. {userId: 1, edit: true})
 * @param {boolean} push   - whether to push a new history entry (default: true)
 */
function navigate(page, params = {}, push = true) {
    App.params = params;

    // build a readable URL for the browser address bar
    let url = '/' + page;
    if (params.userId) url += '/' + params.userId;

    // push a new history entry so the back/forward buttons work
    if (push) {
        window.history.pushState({ page, params }, '', url);
    }

    // highlight the active nav link
    document.querySelectorAll('.nav a[data-page]').forEach(a => {
        a.classList.toggle('active', a.dataset.page === page);
    });

    // render the matching page or fall back to home
    const render = routes[page];
    if (render) render(params);
    else renderHome();

    // scroll to the top of the page
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

// ── Initialisation ────────────────────────────────────────────────────────────

document.addEventListener('DOMContentLoaded', async () => {
    setupNav();
    setupChat();
    setupModal();

    // load the default user and update the topbar avatar
    await loadCurrentUser(1);

    // determine the initial page from the current URL
    const path = window.location.pathname.replace('/', '').split('/')[0] || 'home';
    const page = Object.keys(routes).find(r => path.startsWith(r)) || 'home';
    navigate(page, {}, false);
});

// handle the browser back and forward buttons
window.addEventListener('popstate', (e) => {
    const state = e.state || { page: 'home', params: {} };
    navigate(state.page || 'home', state.params || {}, false);
});

// ── Nav setup ─────────────────────────────────────────────────────────────────

// Intercepts nav link clicks to prevent full page reloads
function setupNav() {
    document.querySelectorAll('.nav a[data-page]').forEach(a => {
        a.addEventListener('click', (e) => {
            e.preventDefault();
            navigate(a.dataset.page);
        });
    });
}

// ── Dynamic avatar ────────────────────────────────────────────────────────────

/**
 * Fetches a user by ID, sets them as the active user and updates the avatar
 * @param {number} userId
 */
async function loadCurrentUser(userId) {
    try {
        const user = await UserAPI.getById(userId);
        setCurrentUser(user);
    } catch (e) {
        console.warn('Could not load current user:', e.message);
    }
}

/**
 * Sets the active user and updates the topbar avatar
 * @param {object} user - UserProfileDTO
 */
function setCurrentUser(user) {
    App.currentUser = user;
    updateAvatar(user);
}

/**
 * Updates the topbar avatar with the user's initials, colour and profile link
 * @param {object} user - UserProfileDTO
 */
function updateAvatar(user) {
    const avatar = document.querySelector('.avatar');
    if (!avatar) return;

    // generate initials from first and last name
    const initials = (user.firstName?.[0] || '?') + (user.lastName?.[0] || '');
    avatar.textContent = initials.toUpperCase();

    // apply colour based on the user's name
    avatar.style.background   = getAvatarBg(user.firstName || 'A');
    avatar.style.borderColor  = getAvatarBorder(user.firstName || 'A');

    // clicking the avatar navigates to the user's profile
    avatar.onclick = () => navigate('profile', { userId: user.id });

    // tooltip shows the full name
    avatar.title = user.firstName + ' ' + user.lastName;
}
