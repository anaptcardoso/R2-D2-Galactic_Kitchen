
// generate html

function renderProfile() {
  return `
    <div class="profile">
    <h2>My Profile</h2>
    
    <div class="profile-form">
      <div class="form-group">
        <label>Name</name>
        <input type="text" id="imput-name" placeholder="your name"/>
    </div>
    
    <div class="form-group">
      <label>Email</label>
      <input type="Email" id="input-email" placeholder="your@email.com"/>
    </div>

    <div class="form-group">
      <label>Diet</label>
      <select id="input-diet">
        <option value="OMNIVORE">Omnivore</option>
        <option value="VEGETARIAN">Vegetarian</option>
        <option value="VEGAN">Vegan</option>
        <option value="KETO">Keto</option>
        <option value="GLUTEN">Gluten</option>
        <option value="LACTOSE">Lactose</option>
      </select>
    </div>

    <button id="btn-save">Save</button>
  </div>
  </div>
  `;
}

// initialize page ( eventos, API, etc)

function initProfile() {
  // logic

  document.getElementById('btn-save').addEventListener('click', () => {
    const name = document.getElementById('input-name').value;
    const email = document.getElementById('input-email').value;
    const diet = document.getElementById('input-diet').value;

    if (!name || !email) {
      alert('Please fill in all fields');
      return;
    }
    
    console.log('Profile saved:', {name, email, diet});
    alert('Profile saved sucessfully!');
  })
}
