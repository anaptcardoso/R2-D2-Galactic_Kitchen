// profile.js — Renders the individual user profile

async function renderProfile(params = {}) {

    // if new: true is passed, show an empty form
    if (params.new) {
        renderNewProfile();
        return;
    }

    const app = document.getElementById('main-content');
    const userId = params.userId || App.currentUser?.id || 1;

    // show loading while fetching data
    app.innerHTML = `
        <div class="loading">
            <p>LOADING PILOT DATA...</p>
        </div>
    `;

    try {
        // fetch the user from the backend
        const user = await UserAPI.getById(userId);

        // nutritionDTO contains weight, height, goal, activityLevel, dietPreferences
        const n = user.nutritionDTO || {};
        const initials = (user.firstName?.[0] || '?') + (user.lastName?.[0] || '');
        const dietType = Array.isArray(n.dietPreferences) ? n.dietPreferences[0] : null;
        const dietLabel = {
            OMNIVORE: 'OMNIVORE', VEGETARIAN: 'VEGETARIAN',
            VEGAN: 'VEGAN', KETO: 'KETO', GLUTEN_FREE: 'GLUTEN FREE'
        }[dietType] || (dietType || '—').toUpperCase();
        const activityLabel = {
            sedentary: 'SEDENTARY', light: 'LIGHT',
            moderate: 'MODERATE', active: 'ACTIVE', 'very active': 'VERY ACTIVE'
        }[n.activityLevel] || (n.activityLevel?.toUpperCase() || '—');

        app.innerHTML = `
            <div class="profiles-hero">
                <div class="hero-eyebrow">// REBEL ALLIANCE DATABASE</div>
                <h1>Pilot <span>${user.firstName} ${user.lastName}</span></h1>
                <p>${user.email || ''}</p>
            </div>

            <div class="profiles-meta">
                <div>
                    <div class="section-label">PROFILE STATUS</div>
                    <p>ID #${userId} — Active session</p>
                </div>
                <div class="profiles-signal">
                    <div class="status-dot"></div>
                    IDENTITY SYSTEM ONLINE
                </div>
            </div>

            <div class="profile-layout">

                <div class="profile-sidebar">
                    <div class="avatar-lg" style="border-color: ${getAvatarBorder(user.firstName)}; box-shadow: var(--glow); ${getAvatarMarkup(user) ? 'background: transparent;' : 'background: var(--cyan-dim);'}">
                        ${getAvatarMarkup(user) || initials.toUpperCase()}
                    </div>
                    <div class="avatar-name">${user.firstName} ${user.lastName}</div>
                    <div class="avatar-email">${user.email || ''}</div>
                    <div class="id-badge">ID #${userId}</div>
                    <div class="sidebar-divider"></div>
                    <div class="sidebar-stat"><span>Diet</span><strong>${dietLabel}</strong></div>
                    <div class="sidebar-stat"><span>Country</span><strong>${(user.country || '—').toUpperCase()}</strong></div>
                    <div class="sidebar-stat"><span>Activity</span><strong>${activityLabel}</strong></div>
                    <div class="sidebar-divider"></div>
                    <div class="sidebar-stat"><span>Weight</span><strong>${n.weight ? n.weight + ' KG' : '—'}</strong></div>
                    <div class="sidebar-stat"><span>Height</span><strong>${n.height ? n.height + ' CM' : '—'}</strong></div>
                    <div class="sidebar-stat"><span>Goal</span><strong>${n.goal ? n.goal.toUpperCase() : '—'}</strong></div>
                </div>

                <div class="profile-form">

                    <div class="form-section">
                        <div class="form-section-title">PERSONAL DATA</div>
                        <div class="form-grid">
                            <div class="field">
                                <label>FIRST NAME</label>
                                <input type="text" id="input-firstname" value="${user.firstName || ''}" />
                            </div>
                            <div class="field">
                                <label>LAST NAME</label>
                                <input type="text" id="input-lastname" value="${user.lastName || ''}" />
                            </div>
                            <div class="field" style="grid-column: 1 / -1;">
                                <label>EMAIL</label>
                                <input type="email" id="input-email" value="${user.email || ''}" />
                            </div>
                            <div class="field">
                                <label>PHONE</label>
                                <input type="text" id="input-phone" value="${user.phone || ''}" />
                            </div>
                            <div class="field">
                                <label>COUNTRY</label>
                                <input type="text" id="input-country" value="${user.country || ''}" />
                            </div>
                            <div class="field">
                                <label>DIET</label>
                                <select id="input-diet">
                                    <option value="OMNIVORE"    ${dietType === 'OMNIVORE'    ? 'selected' : ''}>Omnivore</option>
                                    <option value="VEGETARIAN"  ${dietType === 'VEGETARIAN'  ? 'selected' : ''}>Vegetarian</option>
                                    <option value="VEGAN"       ${dietType === 'VEGAN'       ? 'selected' : ''}>Vegan</option>
                                    <option value="KETO"        ${dietType === 'KETO'        ? 'selected' : ''}>Keto</option>
                                    <option value="GLUTEN_FREE" ${dietType === 'GLUTEN_FREE' ? 'selected' : ''}>Gluten Free</option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="form-section">
                        <div class="form-section-title">HEALTH DATA</div>
                        <div class="form-grid">
                            <div class="field">
                                <label>WEIGHT (KG)</label>
                                <input type="number" id="input-weight" value="${n.weight || ''}" placeholder="e.g. 60" />
                            </div>
                            <div class="field">
                                <label>HEIGHT (CM)</label>
                                <input type="number" id="input-height" value="${n.height || ''}" placeholder="e.g. 165" />
                            </div>
                            <div class="field" style="grid-column: 1 / -1;">
                                <label>GOAL</label>
                                <input type="text" id="input-goal" value="${n.goal || ''}" placeholder="Ex: Lose weight" />
                            </div>
                            <div class="field">
                                <label>ACTIVITY LEVEL</label>
                                <select id="input-activity">
                                    <option value="sedentary"   ${n.activityLevel === 'sedentary'   ? 'selected' : ''}>Sedentary</option>
                                    <option value="light"       ${n.activityLevel === 'light'       ? 'selected' : ''}>Light</option>
                                    <option value="moderate"    ${n.activityLevel === 'moderate'    ? 'selected' : ''}>Moderate</option>
                                    <option value="active"      ${n.activityLevel === 'active'      ? 'selected' : ''}>Active</option>
                                    <option value="very active" ${n.activityLevel === 'very active' ? 'selected' : ''}>Very Active</option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div id="profile-msg" class="result-box hidden"></div>

                    <div class="form-actions">
                        <button id="btn-delete" class="btn-danger">Delete Profile</button>
                        <button id="btn-save" class="btn-cyan">Save Changes</button>
                    </div>

                </div>
            </div>
        `;

        initProfile(userId, user);

    } catch (e) {
        app.innerHTML = `
            <div class="error-state">
                <p>COULD NOT LOAD PROFILE: ${e.message}</p>
                <button class="btn-cyan" onclick="navigate('profiles')">Back to Profiles</button>
            </div>
        `;
    }
}

// ── Existing profile events ──────────────────────────────────────────────────

function initProfile(userId, originalUser) {

    document.getElementById('btn-save').addEventListener('click', async () => {
        const msg = document.getElementById('profile-msg');

        const dto = {
            firstName: document.getElementById('input-firstname').value,
            lastName:  document.getElementById('input-lastname').value,
            email:     document.getElementById('input-email').value,
            phone:     document.getElementById('input-phone').value,
            country:   document.getElementById('input-country').value,
            nutritionDTO: {
                weight:          Number(document.getElementById('input-weight').value) || null,
                height:          Number(document.getElementById('input-height').value) || null,
                goal:            document.getElementById('input-goal').value,
                activityLevel:   document.getElementById('input-activity').value,
                dietPreferences: [document.getElementById('input-diet').value],
                allergies:       null,
            }
        };

        if (!dto.firstName || !dto.email) {
            msg.textContent = 'First name and email are required.';
            msg.classList.remove('hidden');
            return;
        }

        try {
            await UserAPI.update(userId, dto);

            if (App.currentUser?.id === userId) {
                await loadCurrentUser(userId);
            }

            updateProfileSidebar(dto);

            msg.textContent = 'Profile saved successfully!';
            msg.classList.remove('hidden');

        } catch (e) {
            msg.textContent = `Error saving profile: ${e.message}`;
            msg.classList.remove('hidden');
        }
    });

    document.getElementById('btn-delete').addEventListener('click', async () => {
        if (!confirm(`Are you sure you want to delete ${originalUser.firstName}'s profile?`)) return;

        try {
            await UserAPI.delete(userId);
            navigate('profiles');
        } catch (e) {
            alert(`Error deleting profile: ${e.message}`);
        }
    });
}

// ── Sidebar live update ──────────────────────────────────────────────────────

function updateProfileSidebar(data) {
    const n = data.nutritionDTO || {};
    const dietType = Array.isArray(n.dietPreferences) ? n.dietPreferences[0] : null;
    const dietLabel = {
        OMNIVORE: 'OMNIVORE', VEGETARIAN: 'VEGETARIAN',
        VEGAN: 'VEGAN', KETO: 'KETO', GLUTEN_FREE: 'GLUTEN FREE'
    }[dietType] || (dietType || '—').toUpperCase();

    const activityLabel = {
        sedentary: 'SEDENTARY', light: 'LIGHT',
        moderate: 'MODERATE', active: 'ACTIVE', 'very active': 'VERY ACTIVE'
    }[n.activityLevel] || (n.activityLevel?.toUpperCase() || '—');

    const sidebar = document.querySelector('.profile-sidebar');
    if (!sidebar) return;

    const initials = (data.firstName?.[0] || '?') + (data.lastName?.[0] || '');
    const avatarEl = sidebar.querySelector('.avatar-lg');
    if (avatarEl) avatarEl.textContent = initials.toUpperCase();
    const nameEl = sidebar.querySelector('.avatar-name');
    if (nameEl) nameEl.textContent = `${data.firstName} ${data.lastName}`;
    const emailEl = sidebar.querySelector('.avatar-email');
    if (emailEl) emailEl.textContent = data.email || '';

    sidebar.querySelectorAll('.sidebar-stat').forEach(row => {
        const label = row.querySelector('span')?.textContent?.trim();
        const val   = row.querySelector('strong');
        if (!val) return;
        if (label === 'Diet')     val.textContent = dietLabel;
        if (label === 'Country')  val.textContent = (data.country || '—').toUpperCase();
        if (label === 'Activity') val.textContent = activityLabel;
        if (label === 'Weight')   val.textContent = n.weight ? n.weight + ' KG' : '—';
        if (label === 'Height')   val.textContent = n.height ? n.height + ' CM' : '—';
        if (label === 'Goal')     val.textContent = n.goal ? n.goal.toUpperCase() : '—';
    });
}

// ── New profile ──────────────────────────────────────────────────────────────

function renderNewProfile() {
    const app = document.getElementById('main-content');

    app.innerHTML = `
        <div class="profiles-hero">
            <div class="hero-eyebrow">// REBEL ALLIANCE DATABASE</div>
            <h1>New <span>Profile</span></h1>
            <p>Join the Rebel Alliance — register your galactic identity.</p>
        </div>

        <div class="profiles-meta">
            <div>
                <div class="section-label">PROFILE STATUS</div>
                <p>New recruit — pending registration</p>
            </div>
            <div class="profiles-signal">
                <div class="status-dot"></div>
                IDENTITY SYSTEM ONLINE
            </div>
        </div>

        <div class="profile-layout">
            <div class="profile-sidebar">
                <div class="avatar-lg" style="background: var(--cyan-dim); border-color: var(--cyan-mid);">?</div>
                <div class="avatar-name">New Recruit</div>
                <div class="avatar-email">Fill in your data</div>
                <div class="sidebar-divider"></div>
                <div class="sidebar-stat"><span>Diet</span><strong>—</strong></div>
                <div class="sidebar-stat"><span>Country</span><strong>—</strong></div>
                <div class="sidebar-stat"><span>Activity</span><strong>—</strong></div>
                <div class="sidebar-divider"></div>
                <div class="sidebar-stat"><span>Weight</span><strong>—</strong></div>
                <div class="sidebar-stat"><span>Height</span><strong>—</strong></div>
                <div class="sidebar-stat"><span>Goal</span><strong>—</strong></div>
            </div>

            <div class="profile-form">
                <div class="form-section">
                    <div class="form-section-title">PERSONAL DATA</div>
                    <div class="form-grid">
                        <div class="field">
                            <label>FIRST NAME</label>
                            <input type="text" id="input-firstname" placeholder="Ex: Luke" />
                        </div>
                        <div class="field">
                            <label>LAST NAME</label>
                            <input type="text" id="input-lastname" placeholder="Ex: Skywalker" />
                        </div>
                        <div class="field" style="grid-column: 1 / -1;">
                            <label>EMAIL</label>
                            <input type="email" id="input-email" placeholder="your@email.com" />
                        </div>
                        <div class="field">
                            <label>PHONE</label>
                            <input type="text" id="input-phone" placeholder="Ex: +351 912 345 678" />
                        </div>
                        <div class="field">
                            <label>COUNTRY</label>
                            <input type="text" id="input-country" placeholder="Ex: Tatooine" />
                        </div>
                        <div class="field">
                            <label>DIET</label>
                            <select id="input-diet">
                                <option value="OMNIVORE">Omnivore</option>
                                <option value="VEGETARIAN">Vegetarian</option>
                                <option value="VEGAN">Vegan</option>
                                <option value="KETO">Keto</option>
                                <option value="GLUTEN_FREE">Gluten Free</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="form-section">
                    <div class="form-section-title">HEALTH DATA</div>
                    <div class="form-grid">
                        <div class="field">
                            <label>WEIGHT (KG)</label>
                            <input type="number" id="input-weight" placeholder="Ex: 75" />
                        </div>
                        <div class="field">
                            <label>HEIGHT (CM)</label>
                            <input type="number" id="input-height" placeholder="Ex: 175" />
                        </div>
                        <div class="field" style="grid-column: 1 / -1;">
                            <label>GOAL</label>
                            <input type="text" id="input-goal" placeholder="Ex: Become a Jedi Master" />
                        </div>
                        <div class="field">
                            <label>ACTIVITY LEVEL</label>
                            <select id="input-activity">
                                <option value="sedentary">Sedentary</option>
                                <option value="light">Light</option>
                                <option value="moderate" selected>Moderate</option>
                                <option value="active">Active</option>
                                <option value="very active">Very Active</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div id="profile-msg" class="result-box hidden"></div>

                <div class="form-actions">
                    <button class="btn-outline" onclick="navigate('profiles')">Cancel</button>
                    <button id="btn-save" class="btn-cyan">Create Profile</button>
                </div>

            </div>
        </div>
    `;

    initNewProfile();
}

// ── New profile events ───────────────────────────────────────────────────────

function initNewProfile() {
    document.getElementById('btn-save').addEventListener('click', async () => {
        const msg = document.getElementById('profile-msg');

        const dto = {
            firstName: document.getElementById('input-firstname').value,
            lastName:  document.getElementById('input-lastname').value,
            email:     document.getElementById('input-email').value,
            phone:     document.getElementById('input-phone').value,
            country:   document.getElementById('input-country').value,
            nutritionDTO: {
                weight:          Number(document.getElementById('input-weight').value) || null,
                height:          Number(document.getElementById('input-height').value) || null,
                goal:            document.getElementById('input-goal').value,
                activityLevel:   document.getElementById('input-activity').value,
                dietPreferences: [document.getElementById('input-diet').value],
                allergies:       null,
            }
        };

        if (!dto.firstName || !dto.email) {
            msg.textContent = 'First name and email are required.';
            msg.classList.remove('hidden');
            return;
        }

        try {
            const newUser = await UserAPI.register(dto);

            msg.textContent = `Profile created successfully! Welcome, ${dto.firstName}!`;
            msg.classList.remove('hidden');

            setTimeout(() => navigate('profile', { userId: newUser.id }), 1500);

        } catch (e) {
            msg.textContent = `Error creating profile: ${e.message}`;
            msg.classList.remove('hidden');
        }
    });
}