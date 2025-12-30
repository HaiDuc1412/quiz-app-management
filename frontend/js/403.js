function goHome() {
    globalThis.location.href = '/';

}

document.addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        goHome();
    }
});

console.warn('403 Forbidden: User attempted to access restricted page');
