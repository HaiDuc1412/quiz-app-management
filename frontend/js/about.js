
document.querySelector('.user-avatar')?.addEventListener('click', function() {
    console.log('User avatar clicked');
});

document.querySelectorAll('.team-card').forEach(card => {
    card.addEventListener('click', function() {
        console.log('Team member:', this.querySelector('h3').textContent);
    });
});
