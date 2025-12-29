

document.getElementById('loginForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    if (!username || !password) {
        alert('Vui lòng điền đầy đủ thông tin!');
        return;
    }

    alert(`Đăng nhập với username: ${username}`);
});


function goHome() {
    globalThis.location.href = '/';
}


document.querySelector('.forgot-password').addEventListener('click', function (e) {
    e.preventDefault();
    alert('Chức năng quên mật khẩu đang được phát triển!');

});

const inputs = document.querySelectorAll('input');
inputs.forEach(input => {
    input.addEventListener('focus', function () {
        this.parentElement.classList.add('focused');
    });

    input.addEventListener('blur', function () {
        this.parentElement.classList.remove('focused');
    });
});
