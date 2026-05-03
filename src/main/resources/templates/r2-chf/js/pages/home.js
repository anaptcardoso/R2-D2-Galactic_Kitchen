
// generate html

function renderHome() {
  return `
    <div class="hero">
      <h1>What are we cooking today?</h1>
      <p>Discover recipes and let R2-CHEF take care of the rest.</p>
    </div>

    <div class="home-grid">
      <div class="home-card" id="card-recipes">Recipes</div>
      <div class="home-card" id="card-plan">Weekly Plan</div>
      <div class="home-card" id="card-chat">R2-Chat</div>
      <div class="home-card" id="card-profile">Profile</div>
    </div>
  `;
}

// initialize page ( eventos, API, etc)

function initHome() {
  // logic
  document.getElementById('card-recipes').addEventListener('click', () => navigate('recipes'));
  document.getElementById('card-plan').addEventListener('click', () => navigate('plan'));
  document.getElementById('card-chat').addEventListener('click', () => navigate('chat'));
  document.getElementById('card-profile').addEventListener('click', () => navigate('profile'));
}
