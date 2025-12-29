function submitQuizCode() {
    const quizCode = document.getElementById('quizCode').value.trim();
    
    if (!quizCode) {
        alert('Please enter a quiz code!');
        return;
    }
    
    if (quizCode.length < 6) {
        alert('Quiz code must be at least 6 characters!');
        return;
    }
    
    console.log('Taking quiz with code:', quizCode);

    alert(`Starting quiz with code: ${quizCode}`);
}

document.getElementById('quizCode').addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        submitQuizCode();
    }
});

function startQuiz(quizId) {
    console.log('Starting quiz:', quizId);
    alert(`Starting Quiz #${quizId}!`);
}

document.querySelector('.user-avatar')?.addEventListener('click', function() {
    console.log('User avatar clicked');
});
