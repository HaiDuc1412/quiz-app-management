// Load header and sidebar components
document.addEventListener('DOMContentLoaded', function() {
  // Load header
  fetch('components/header.html')
    .then(response => response.text())
    .then(data => {
      document.getElementById('header-placeholder').innerHTML = data;
    })
    .catch(error => console.error('Error loading header:', error));

  // Load sidebar
  fetch('components/sidebar.html')
    .then(response => response.text())
    .then(data => {
      document.getElementById('sidebar-placeholder').innerHTML = data;
    })
    .catch(error => console.error('Error loading sidebar:', error));
});
