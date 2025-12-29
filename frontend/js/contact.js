
document.getElementById('contactForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const name = document.getElementById('name').value.trim();
    const email = document.getElementById('email').value.trim();
    const message = document.getElementById('message').value.trim();
    
    if (!name || !email || !message) {
        alert('Please fill in all fields!');
        return;
    }
    
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        alert('Please enter a valid email address!');
        return;
    }
    
    const feedbackData = {
        name,
        email,
        message,
        timestamp: new Date().toISOString()
    };
    
    console.log('Feedback submitted:', feedbackData);
    
    alert(`Thank you, ${name}! Your feedback has been received.\n\nWe will contact you at: ${email}`);
    
    document.getElementById('contactForm').reset();
});


document.querySelector('.user-avatar')?.addEventListener('click', function() {
    console.log('User avatar clicked');
});

document.querySelectorAll('.social-icon').forEach(icon => {
    icon.addEventListener('click', function(e) {
        e.preventDefault();
        const platform = this.classList.contains('tiktok') ? 'TikTok' :
                        this.classList.contains('facebook') ? 'Facebook' :
                        this.classList.contains('youtube') ? 'YouTube' : 'LinkedIn';
        console.log(`Opening ${platform}...`);
    });
});

const inputs = document.querySelectorAll('input, textarea');
inputs.forEach(input => {
    input.addEventListener('blur', function() {
        if (this.value.trim() === '' && this.hasAttribute('required')) {
            this.style.borderColor = '#dc3545';
        } else {
            this.style.borderColor = '#ddd';
        }
    });
    
    input.addEventListener('input', function() {
        if (this.style.borderColor === 'rgb(220, 53, 69)') {
            this.style.borderColor = '#ddd';
        }
    });
});
