// helpers.js — Shared utility functions


// Returns a background color for the recipe card banner
function getRecipeBannerColor(name) {
    const n = (name || '').toLowerCase();
    if (n.includes('bantha') || n.includes('mandalorian')) return '#FFD70008';
    if (n.includes('dagobah') || n.includes('coruscant'))  return '#00FF8808';
    return '#00D4FF08';
}



// Returns a background color for the avatar based on the user's first name
function getAvatarBg(name) {
    const colors = ['#00D4FF22','#FFD70022','#00FF8822','#8B5CF622','#FF444422'];
    return colors[(name || 'A').charCodeAt(0) % colors.length];
}

// Returns a border color for the avatar based on the user's first name
function getAvatarBorder(name) {
    const colors = ['#00D4FF66','#FFD70066','#00FF8866','#8B5CF666','#FF444466'];
    return colors[(name || 'A').charCodeAt(0) % colors.length];
}

// Returns the initials of the currently active user from App.currentUser
function getUserInitials() {
    if (!App.currentUser) return '?';
    const first = App.currentUser.firstName?.[0] || '';
    const last  = App.currentUser.lastName?.[0]  || '';
    return (first + last).toUpperCase();
}


// Converts a difficulty level enum value to a readable label
function formatDifficulty(level) {
    return { EASY: 'Easy', MEDIUM: 'Medium', HARD: 'Hard' }[level] || level || '—';
}

// Converts a diet type enum value to a readable label
function formatDiet(d) {
    return {
        OMNIVORE: 'Omnivore', VEGAN: 'Vegan', VEGETARIAN: 'Vegetarian',
        KETO: 'Keto', PALEO: 'Paleo', GLUTEN_FREE: 'Gluten-free', LACTOSE_FREE: 'Lactose-free'}[d] || d;
}

// Calculates the BMI from weight (kg) and height (cm)
function calcIMC(w, h) {
    if (!w || !h) return '—';
    return (w / Math.pow(h / 100, 2)).toFixed(1);
}

// UI helpers 

// Replaces the main content area with the given HTML
function setContent(html) {
    document.getElementById('main-content').innerHTML = html;
}

// Displays a loading state with an optional message
function showLoading(msg = 'Loading...') {
    setContent(`<div class="loading"><p>${msg}</p></div>`);
}

// Displays an error state with a message and a back button
function showError(message) {
    setContent(`
        <div class="error-state">
            <p> ${message}</p>
            <button class="btn-cyan" onclick="navigate('home')">Back to home</button>
        </div>
    `);
}