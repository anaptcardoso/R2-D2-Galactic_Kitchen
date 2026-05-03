

//const pages = ['home', 'recipes', 'detail', 'plan', 'chat', 'profile'];

function navigate(page) {
  

  // update active link
  document.querySelectorAll('.nav-links a').forEach(a => {
    a.classList.remove('active');
  });

  const activeLink = document.querySelector(`.nav-links a[href="#${page}"]`);
  if (activeLink) activeLink.classList.add('active');

  // update url
  window.history.pushState({ page }, '', '#' + page);

  // inject page 
  const app = document.getElementById('app');

  if (page === 'home') {
    app.innerHTML = renderHome();
    initHome();
   }
  
}

// navigate when the page loads
navigate('home');


