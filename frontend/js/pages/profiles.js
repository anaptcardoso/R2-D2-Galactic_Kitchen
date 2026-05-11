// profiles.js — Renders the user profiles page of the SPA

// Renders the profiles list page.

async function renderProfiles(params = {}) {
    const app = document.getElementById('main-content');

    app.innerHTML = `
        <section class="profiles-hero">
            <div class="hero-eyebrow">// REBEL ALLIANCE DATABASE</div>
            <h1>Rebel Alliance <span>Profiles</span></h1>
            <p>Manage galactic identities, nutrition goals and mission preferences.</p>
        </section>

        <section class="profiles-toolbar">
            <div class="profiles-search-wrap">
                <svg class="profiles-search-icon" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8">
                    <circle cx="11" cy="11" r="7"></circle>
                    <path d="M20 20l-4.5-4.5"></path>
                </svg>

                <input 
                    type="text" 
                    id="profiles-search" 
                    class="search-input profiles-search"
                    placeholder="Search by name..."
                />
            </div>

            <button class="btn-cyan" id="new-profile-btn">
                NEW PROFILE
            </button>
        </section>

        <section class="profiles-meta">
            <div>
                <span class="section-label">PROFILE STATUS</span>
                <p id="profiles-count">Loading profiles...</p>
            </div>

            <div class="profiles-signal">
                <span class="status-dot"></span>
                <span>IDENTITY SYSTEM ONLINE</span>
            </div>
        </section>

        <section id="profiles-list" class="profiles-list upgraded-profiles-list">
            <div class="loading">
                <p>LOADING PROFILES...</p>
            </div>
        </section>
    `;

    // Initialize search and action events
    initProfiles();

    // Load profiles from the backend
    await loadProfiles();
}

//Initializes all interactive events on the profiles page.

function initProfiles() {
    const searchInput = document.getElementById('profiles-search');
    const newProfileButton = document.getElementById('new-profile-btn');

    // Live search by first name, last name, email or country
    searchInput?.addEventListener('input', (e) => {
        const search = e.target.value.toLowerCase().trim();
        filterProfiles(search);
    });

    // Navigate to the profile creation screen
    newProfileButton?.addEventListener('click', () => {
        navigate('profile', { new: true });
    });
}

/**
 * Loads profiles from the backend.
 * Stores users in App.users so filtering can happen without another API call.
 */
async function loadProfiles() {
    const list = document.getElementById('profiles-list');

    try {
        const users = await UserAPI.getAll();

        // Store users globally for local filtering
        App.users = users;

        renderProfileCards(users);

    } catch (e) {
        console.warn('Could not load profiles from backend:', e.message);

        // Display empty/error state if API fails
        list.innerHTML = `
            <div class="empty-state profiles-empty">
                <div class="empty-icon">
                    ${getProfileEmptyIcon()}
                </div>
                <h3>PROFILE DATABASE FAILED</h3>
                <p>Could not load profiles. Please try again later.</p>
            </div>
        `;

        updateProfilesCount(0);
    }
}

// Filters profiles by the current search term.

function filterProfiles(search) {
    if (!App.users) return;

    if (!search) {
        renderProfileCards(App.users);
        return;
    }

    // Filter users by first name, last name, email, or country
    const filtered = App.users.filter(user => {
        const firstName = (user.firstName || '').toLowerCase();
        const lastName = (user.lastName || '').toLowerCase();
        const email = (user.email || '').toLowerCase();
        const country = (user.country || '').toLowerCase();

        return (
            firstName.includes(search) ||
            lastName.includes(search) ||
            email.includes(search) ||
            country.includes(search)
        );
    });

    renderProfileCards(filtered);
}

// Renders all profile cards.

function renderProfileCards(users) {
    const list = document.getElementById('profiles-list');

    updateProfilesCount(users ? users.length : 0);

    if (!users || users.length === 0) {
        list.innerHTML = `
            <div class="empty-state profiles-empty">
                <div class="empty-icon">
                    ${getProfileEmptyIcon()}
                </div>
                <h3>NO PROFILES FOUND</h3>
                <p>Try another search term or create a new profile.</p>
            </div>
        `;
        return;
    }

    list.innerHTML = users.map(renderProfileCard).join('');
}

//Builds the HTML for one profile card.

function renderProfileCard(user) {
    const fullName = `${user.firstName || ''} ${user.lastName || ''}`.trim() || 'Unknown User';
    const initials = getInitials(user);
    const avatar = getAvatarMarkup(user);
    const nutrition = user.nutritionDTO || {};

    return `
        <article class="profile-card upgraded-profile-card">
            <div class="profile-av upgraded-profile-avatar" style="border-color: ${getAvatarBorder(user.firstName)};">
                ${avatar || initials}
            </div>

            <div class="profile-main">
                <div class="profile-top">
                    <div>
                        <h3 class="profile-name">${fullName}</h3>
                        <p class="profile-email">
                            ${user.email || 'No email registered'}${user.country ? ` · ${user.country}` : ''}
                        </p>
                    </div>

                    <span class="id-badge">ID #${user.id || '--'}</span>
                </div>

                <div class="profile-quote-row">
                    ${user.bio ? `<p class="profile-card__bio"><em>"${user.bio.replace(/(https?:\/\/[^\s]+)/g, '<a href="$1" target="_blank">$1</a>')}"</em></p>` : ''}
                </div>

                <div class="tags profile-tags">
                    ${user.dietType ? `<span class="tag ${getDietTagClass(user.dietType)}">${formatDiet(user.dietType)}</span>` : ''}
                    ${nutrition.goal ? `<span class="tag tag-goal">${nutrition.goal}</span>` : ''}
                    ${nutrition.allergies ? `<span class="tag tag-gluten_free">Allergies</span>` : ''}
                </div>

                <div class="stats-inline upgraded-profile-stats">
                    ${nutrition.weight ? `<span><strong>${nutrition.weight}</strong> kg</span>` : ''}
                    ${nutrition.height ? `<span><strong>${nutrition.height}</strong> cm</span>` : ''}
                    ${nutrition.activityLevel ? `<span>${formatActivity(nutrition.activityLevel)}</span>` : ''}
                    ${user.dailyCalories ? `<span><strong>${user.dailyCalories}</strong> kcal/day</span>` : ''}
                </div>

                <div class="profile-extra">
                    ${nutrition.goal ? `<p><strong>Goal:</strong> ${nutrition.goal}</p>` : ''}
                    ${nutrition.dietPreferences ? `<p><strong>Diet:</strong> ${formatList(nutrition.dietPreferences)}</p>` : ''}
                    ${nutrition.allergies ? `<p><strong>Allergies:</strong> ${formatList(nutrition.allergies)}</p>` : ''}
                </div>
            </div>

            <div class="profile-actions">
                <button class="btn-outline" onclick="navigate('profile', { userId: ${user.id} })">
                    EDIT
                </button>

                <button class="btn-cyan" onclick="loadCurrentUser(${user.id})">
                    SWITCH
                </button>
            </div>
        </article>
    `;
}

// Updates the profile count text.

function updateProfilesCount(count) {
    const countElement = document.getElementById('profiles-count');
    if (!countElement) return;

    const label = count === 1 ? 'profile located' : 'profiles located';
    countElement.textContent = `${count} ${label}`;
}

// Returns the avatar image markup if an image exists for the user.

function getAvatarMarkup(user) {
    const avatarImages = {
        Luke: '/js/assets/luke.jpg',
        Leia: '/js/assets/leia.jpg',
        Han: '/js/assets/han_solo.jpg',
        Ana: '/js/assets/ana_cardoso.jpg',
        Ines: '/js/assets/ines_azevedo.jpg',
        Inês: '/js/assets/ines_azevedo.jpg',
        Pedro: '/js/assets/saldanha_pedro.jpg',
        Yasmin: '/js/assets/yasmin_natasha.jpeg'
    };

    const image = avatarImages[user.firstName];

    if (!image) return '';

    return `
        <img 
            src="${image}" 
            alt="${user.firstName || 'User'} avatar"
            class="profile-avatar-img"
        />
    `;
}

// Creates initials from first and last name.

function getInitials(user) {
    const first = user.firstName?.[0] || '';
    const last = user.lastName?.[0] || '';

    return `${first}${last}` || '?';
}

// Returns a border color for the avatar based on the user's first name.

function getAvatarBorder(firstName = '') {
    const colors = {
        Luke: '#00D4FF',
        Leia: '#FFD700',
        Han: '#FF4444',
        Ana: '#00FF88',
        Ines: '#8B5CF6',
        Inês: '#8B5CF6',
        Pedro: '#00D4FF',
        Yasmin: '#FFD700'
    };

    return colors[firstName] || '#00D4FF';
}

// Converts a diet enum into readable text.

function formatDiet(diet) {
    const map = {
        OMNIVORE: 'Omnivore',
        VEGAN: 'Vegan',
        VEGETARIAN: 'Vegetarian',
        KETO: 'Keto',
        GLUTEN_FREE: 'Gluten free'
    };

    return map[diet] || diet;
}

// Returns the CSS class used to style each diet tag.

function getDietTagClass(diet) {
    const map = {
        OMNIVORE: 'tag-omnivore',
        VEGAN: 'tag-vegan',
        VEGETARIAN: 'tag-vegan',
        KETO: 'tag-keto',
        GLUTEN_FREE: 'tag-gluten_free'
    };

    return map[diet] || 'tag-diet';
}

// Formats a value that might be an array or string.

function formatList(value) {
    if (Array.isArray(value)) return value.join(', ');
    return value || '';
}

// Formats the activity level enum into readable text.

function formatActivity(activity = '') {
    return activity
        .toLowerCase()
        .replaceAll('_', ' ')
        .replace(/\b\w/g, char => char.toUpperCase());
}

// SVG icon used for empty or error states.

function getProfileEmptyIcon() {
    return `
        <svg width="44" height="44" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6">
            <circle cx="12" cy="8" r="4"></circle>
            <path d="M4 21c0-4 4-7 8-7s8 3 8 7"></path>
            <path d="M8 3l8 18"></path>
        </svg>
    `;
}