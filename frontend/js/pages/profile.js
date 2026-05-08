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
    app.innerHTML = `<p class="loading">Loading profile...</p>`;

    try {
        // fetch the user from the backend
        const user = await UserAPI.getById(userId);

        app.innerHTML = `
            <section class="hero">
                <div class="profile-avatar" style="
                    background: ${getAvatarBg(user.firstName)};
                    border: 2px solid ${getAvatarBorder(user.firstName)};
                    width: 80px; height: 80px; border-radius: 50%;
                    display: flex; align-items: center; justify-content: center;
                    font-size: 2rem; margin: 0 auto 1rem;
                ">
                    ${(user.firstName?.[0] || '?') + (user.lastName?.[0] || '')}
                </div>
                <h1>${user.firstName} ${user.lastName}</h1>
                <p>${user.email || ''}</p>
            </section>

            <section class="profile-form">
                <h2>Personal Data</h2>

                <div class="form-group">
                    <label>First name</label>
                    <input type="text" id="input-firstname" value="${user.firstName || ''}"/>
                </div>

                <div class="form-group">
                    <label>Last name</label>
                    <input type="text" id="input-lastname" value="${user.lastName || ''}"/>
                </div>

                <div class="form-group">
                    <label>Email</label>
                    <input type="email" id="input-email" value="${user.email || ''}"/>
                </div>

                <div class="form-group">
                    <label>Phone</label>
                    <input type="text" id="input-phone" value="${user.phone || ''}"/>
                </div>

                <div class="form-group">
                    <label>Country</label>
                    <input type="text" id="input-country" value="${user.country || ''}"/>
                </div>

                <div class="form-group">
                    <label>Diet</label>
                    <select id="input-diet">
                        <option value="OMNIVORE"    ${user.dietType === 'OMNIVORE'    ? 'selected' : ''}>Omnivore</option>
                        <option value="VEGETARIAN"  ${user.dietType === 'VEGETARIAN'  ? 'selected' : ''}>Vegetarian</option>
                        <option value="VEGAN"       ${user.dietType === 'VEGAN'       ? 'selected' : ''}>Vegan</option>
                        <option value="KETO"        ${user.dietType === 'KETO'        ? 'selected' : ''}>Keto</option>
                        <option value="GLUTEN_FREE" ${user.dietType === 'GLUTEN_FREE' ? 'selected' : ''}>Gluten free</option>
                    </select>
                </div>

                <h2>Health Data</h2>

                <div class="form-group">
                    <label>Weight (kg)</label>
                    <input type="number" id="input-weight" value="${user.weight || ''}"/>
                </div>

                <div class="form-group">
                    <label>Height (cm)</label>
                    <input type="number" id="input-height" value="${user.height || ''}"/>
                </div>

                <div class="form-group">
                    <label>Goal</label>
                    <input type="text" id="input-goal" value="${user.goal || ''}" placeholder="Ex: Lose weight"/>
                </div>

                <div class="form-group">
                    <label>Activity level</label>
                    <select id="input-activity">
                        <option value="sedentary"   ${user.activityLevel === 'sedentary'   ? 'selected' : ''}>Sedentary</option>
                        <option value="light"       ${user.activityLevel === 'light'       ? 'selected' : ''}>Light</option>
                        <option value="moderate"    ${user.activityLevel === 'moderate'    ? 'selected' : ''}>Moderate</option>
                        <option value="active"      ${user.activityLevel === 'active'      ? 'selected' : ''}>Active</option>
                        <option value="very active" ${user.activityLevel === 'very active' ? 'selected' : ''}>Very active</option>
                    </select>
                </div>

                <div id="profile-msg" class="result-box hidden"></div>

                <div class="profile-actions">
                    <button id="btn-save" class="btn btn--primary">Save changes</button>
                    <button id="btn-delete" class="btn btn--danger">Delete profile</button>
                </div>
            </section>
        `;

        // initialize event listeners after the HTML is in the DOM
        initProfile(userId, user);

    } catch (e) {
        app.innerHTML = `
            <div class="error-state">
                <p>Could not load profile: ${e.message}</p>
                <button onclick="navigate('profiles')">Back to profiles</button>
            </div>
        `;
    }
}

// Existing profile events 

function initProfile(userId, originalUser) {

    // saves changes when clicking the Save button
    document.getElementById('btn-save').addEventListener('click', async () => {
        const msg = document.getElementById('profile-msg');

        const dto = {
            firstName:     document.getElementById('input-firstname').value,
            lastName:      document.getElementById('input-lastname').value,
            email:         document.getElementById('input-email').value,
            phone:         document.getElementById('input-phone').value,
            country:       document.getElementById('input-country').value,
            dietType:      document.getElementById('input-diet').value,
            weight:        Number(document.getElementById('input-weight').value) || null,
            height:        Number(document.getElementById('input-height').value) || null,
            goal:          document.getElementById('input-goal').value,
            activityLevel: document.getElementById('input-activity').value,
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

            msg.textContent = 'Profile saved successfully!';
            msg.classList.remove('hidden');

        } catch (e) {
            msg.textContent = `Error saving profile: ${e.message}`;
            msg.classList.remove('hidden');
        }
    });

    // deletes the profile when clicking Delete
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

//  New profile 

function renderNewProfile() {
    const app = document.getElementById('main-content');

    app.innerHTML = `
        <section class="hero">
            <h1>New Profile</h1>
            <p>Join the Rebel Alliance</p>
        </section>

        <section class="profile-form">
            <h2>Personal Data</h2>

            <div class="form-group">
                <label>First name</label>
                <input type="text" id="input-firstname" placeholder="Ex: Luke"/>
            </div>

            <div class="form-group">
                <label>Last name</label>
                <input type="text" id="input-lastname" placeholder="Ex: Skywalker"/>
            </div>

            <div class="form-group">
                <label>Email</label>
                <input type="email" id="input-email" placeholder="your@email.com"/>
            </div>

            <div class="form-group">
                <label>Phone</label>
                <input type="text" id="input-phone" placeholder="Ex: +351 912 345 678"/>
            </div>

            <div class="form-group">
                <label>Country</label>
                <input type="text" id="input-country" placeholder="Ex: Tatooine"/>
            </div>

            <div class="form-group">
                <label>Diet</label>
                <select id="input-diet">
                    <option value="OMNIVORE">Omnivore</option>
                    <option value="VEGETARIAN">Vegetarian</option>
                    <option value="VEGAN">Vegan</option>
                    <option value="KETO">Keto</option>
                    <option value="GLUTEN_FREE">Gluten free</option>
                </select>
            </div>

            <h2>Health Data</h2>

            <div class="form-group">
                <label>Weight (kg)</label>
                <input type="number" id="input-weight" placeholder="Ex: 75"/>
            </div>

            <div class="form-group">
                <label>Height (cm)</label>
                <input type="number" id="input-height" placeholder="Ex: 175"/>
            </div>

            <div class="form-group">
                <label>Goal</label>
                <input type="text" id="input-goal" placeholder="Ex: Become a Jedi Master"/>
            </div>

            <div class="form-group">
                <label>Activity level</label>
                <select id="input-activity">
                    <option value="sedentary">Sedentary</option>
                    <option value="light">Light</option>
                    <option value="moderate" selected>Moderate</option>
                    <option value="active">Active</option>
                    <option value="very active">Very active</option>
                </select>
            </div>

            <div id="profile-msg" class="result-box hidden"></div>

            <div class="profile-actions">
                <button id="btn-save" class="btn btn--primary">Create profile</button>
                <button class="btn btn--outline" onclick="navigate('profiles')">Cancel</button>
            </div>
        </section>
    `;

    // initialize events for new profile form
    initNewProfile();
}

//  New profile events 

function initNewProfile() {
    document.getElementById('btn-save').addEventListener('click', async () => {
        const msg = document.getElementById('profile-msg');

        const dto = {
            firstName:     document.getElementById('input-firstname').value,
            lastName:      document.getElementById('input-lastname').value,
            email:         document.getElementById('input-email').value,
            phone:         document.getElementById('input-phone').value,
            country:       document.getElementById('input-country').value,
            dietType:      document.getElementById('input-diet').value,
            weight:        Number(document.getElementById('input-weight').value) || null,
            height:        Number(document.getElementById('input-height').value) || null,
            goal:          document.getElementById('input-goal').value,
            activityLevel: document.getElementById('input-activity').value,
        };

        // validate required fields
        if (!dto.firstName || !dto.email) {
            msg.textContent = 'First name and email are required.';
            msg.classList.remove('hidden');
            return;
        }

        try {
            // register the new user
            const newUser = await UserAPI.register(dto);

            msg.textContent = `Profile created successfully! Welcome, ${dto.firstName}!`;
            msg.classList.remove('hidden');

            // navigate to the newly created profile
            setTimeout(() => navigate('profile', { userId: newUser.id }), 1500);

        } catch (e) {
            msg.textContent = `Error creating profile: ${e.message}`;
            msg.classList.remove('hidden');
        }
    });
}