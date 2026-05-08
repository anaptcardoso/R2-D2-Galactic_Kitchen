// profiles.js — Renders the list of user profiles

async function renderProfiles(params = {}) {
    const app = document.getElementById('main-content');

    app.innerHTML = `
        <section class="hero">
            <h1>Rebel Alliance Profiles</h1>
        </section>

        <section class="profiles-controls">
            <input 
                type="text" 
                id="profiles-search" 
                placeholder="Search by name..."
            />
            <button class="btn btn--primary" onclick="navigate('profile', { new: true })">
                + New Profile
            </button>
        </section>

        <section id="profiles-list">
            <p class="loading">Loading profiles...</p>
        </section>
    `;

    // Initializes the search
    initProfiles();

    // Fetches users from the backend
    await loadProfiles();
}

// ── Event initialization ──────────────────────────────────────────────────────

function initProfiles() {
    // Real-time search while typing the name
    document.getElementById('profiles-search').addEventListener('input', (e) => {
        const search = e.target.value.toLowerCase().trim();
        filterProfiles(search);
    });
}

// ── Loads profiles from the backend ───────────────────────────────────────────

async function loadProfiles() {
    const list = document.getElementById('profiles-list');

    try {
        const users = await UserAPI.getAll();

        // Saves users to filter without calling the backend again
        App.users = users;

        renderProfileCards(users);

    } catch (e) {
        console.warn('Could not load profiles from backend:', e.message);
        list.innerHTML = `
            <p class="error">Could not load profiles. Please try again later.</p>
        `;
    }
}

// ── Filters profiles by the searched name ─────────────────────────────────────

function filterProfiles(search) {
    if (!App.users) return;

    // If there is no search text, shows all profiles
    if (!search) {
        renderProfileCards(App.users);
        return;
    }

    // Filters by first or last name
    const filtered = App.users.filter(u =>
        (u.firstName || '').toLowerCase().includes(search) ||
        (u.lastName  || '').toLowerCase().includes(search)
    );

    renderProfileCards(filtered);
}

// ── Renders the profile cards ─────────────────────────────────────────────────

function renderProfileCards(users) {
    const list = document.getElementById('profiles-list');
    const avatarImages = {
        'Luke':   'js/assets/luke.jpg',
        'Leia':   'js/assets/leia.jpg',
        'Han':    'js/assets/han_solo.jpg',
        'Ana':    'js/assets/ana_cardoso.jpg',
        'Ines':    'js/assets/ines_azevedo.jpg',
        'Pedro':    'js/assets/saldanha_pedro.jpg',
        'Yasmin':    'js/assets/yasmin_natasha.jpeg',
    };

    if (!users || users.length === 0) {
        list.innerHTML = `<p class="empty">No profiles found.</p>`;
        return;
    }

    list.innerHTML = users.map(user => `
        <div class="profile-card">

            <div class="profile-card__avatar" style="
                border-color: ${getAvatarBorder(user.firstName)};
                overflow: hidden; padding: 0;
            ">
                ${avatarImages[user.firstName]
        ? `<img src="${avatarImages[user.firstName]}" style="width:100%;height:100%;object-fit:cover;border-radius:50%;"/>`
        : (user.firstName?.[0] || '?') + (user.lastName?.[0] || '')
    }
            </div>

            <div class="profile-card__info">
                <div class="profile-card__header">
                    <h3>${user.firstName} ${user.lastName}</h3>
                    <span class="profile-card__id">ID #${user.id}</span>
                </div>

                <p class="profile-card__email">${user.email || ''} ${user.country ? '· ' + user.country : ''}</p>

                <div class="profile-card__tags">
                    ${user.dietType ? `<span class="tag">${formatDiet(user.dietType)}</span>` : ''}
                    ${user.goal     ? `<span class="tag tag--goal">${user.goal}</span>`        : ''}
                </div>

                <div class="profile-card__stats">
                    ${user.weight       ? `<span> ${user.weight} kg</span>`             : ''}
                    ${user.activityLevel? `<span> ${user.activityLevel}</span>`          : ''}
                    ${user.dailyCalories? `<span> ${user.dailyCalories} kcal/day</span>` : ''}
                </div>
            </div>

            <button class="btn btn--outline" onclick="navigate('profile', { userId: ${user.id} })">
                Edit
            </button>
            <button class="btn btn--primary" onclick="loadCurrentUser(${user.id})">
                Switch
            </button>

        </div>
    `).join('');
}