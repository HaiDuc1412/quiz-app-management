document.addEventListener('DOMContentLoaded', function() {
  fetch('components/header.html')
    .then(response => response.text())
    .then(data => {
      document.getElementById('header-placeholder').innerHTML = data;
    })
    .catch(error => console.error('Error loading header:', error));

  fetch('components/sidebar.html')
    .then(response => response.text())
    .then(data => {
      document.getElementById('sidebar-placeholder').innerHTML = data;
    })
    .catch(error => console.error('Error loading sidebar:', error));
});
