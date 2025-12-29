document
  .getElementById("registerForm")
  .addEventListener("submit", function (e) {
    e.preventDefault();

    clearErrors();

    const firstName = document.getElementById("firstName").value.trim();
    const lastName = document.getElementById("lastName").value.trim();
    const email = document.getElementById("email").value.trim();
    const username = document.getElementById("username").value.trim();
    const phone = document.getElementById("phone").value.trim();
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    let isValid = true;

    if (!firstName) {
      showError("firstName", "First name is required");
      isValid = false;
    }

    if (!lastName) {
      showError("lastName", "Last name is required");
      isValid = false;
    }

    if (!email) {
      showError("email", "Email is required");
      isValid = false;
    } else if (!isValidEmail(email)) {
      showError("email", "Please enter a valid email address");
      isValid = false;
    }

    if (!username) {
      showError("username", "Username is required");
      isValid = false;
    } else if (username.length < 3) {
      showError("username", "Username must be at least 3 characters");
      isValid = false;
    }

    if (!phone) {
      showError("phone", "Phone number is required");
      isValid = false;
    } else if (!isValidPhone(phone)) {
      showError("phone", "Please enter a valid phone number");
      isValid = false;
    }

    if (!password) {
      showError("password", "Password is required");
      isValid = false;
    } else if (password.length < 6) {
      showError("password", "Password must be at least 6 characters");
      isValid = false;
    }

    if (!confirmPassword) {
      showError("confirmPassword", "Please confirm your password");
      isValid = false;
    } else if (password !== confirmPassword) {
      showError("confirmPassword", "Passwords do not match");
      isValid = false;
    }

    if (!isValid) {
      return;
    }

    const userData = {
      firstName,
      lastName,
      email,
      username,
      phone,
      password,
    };

    console.log("Registration data:", userData);

    alert(
      `Đăng ký thành công!\n\nThông tin:\nTên: ${firstName} ${lastName}\nEmail: ${email}\nUsername: ${username}`
    );
  });

function showError(fieldId, message) {
  const input = document.getElementById(fieldId);
  const formGroup = input.parentElement;

  formGroup.classList.add("error");

  let errorDiv = formGroup.querySelector(".error-message");
  if (!errorDiv) {
    errorDiv = document.createElement("div");
    errorDiv.className = "error-message";
    formGroup.appendChild(errorDiv);
  }
  errorDiv.textContent = message;
}

function clearErrors() {
  const errorGroups = document.querySelectorAll(".form-group.error");
  errorGroups.forEach((group) => {
    group.classList.remove("error");
    const errorMsg = group.querySelector(".error-message");
    if (errorMsg) {
      errorMsg.remove();
    }
  });
}

function isValidEmail(email) {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
}

function isValidPhone(phone) {
  const phoneRegex = /^[\d\s\-\+\(\)]+$/;
  return phoneRegex.test(phone) && phone.replace(/\D/g, "").length >= 10;
}

function goHome() {
  window.location.href = "/";
}

const inputs = document.querySelectorAll("input");
inputs.forEach((input) => {
  input.addEventListener("input", function () {
    const formGroup = this.parentElement;
    if (formGroup.classList.contains("error")) {
      formGroup.classList.remove("error");
      const errorMsg = formGroup.querySelector(".error-message");
      if (errorMsg) {
        errorMsg.remove();
      }
    }
  });

  input.addEventListener("focus", function () {
    this.parentElement.classList.add("focused");
  });

  input.addEventListener("blur", function () {
    this.parentElement.classList.remove("focused");
  });
});

const passwordInputs = document.querySelectorAll('input[type="password"]');
passwordInputs.forEach((input) => {
  input.addEventListener("input", function () {
    if (this.id === "password" && this.value.length > 0) {
      const strength = calculatePasswordStrength(this.value);
    }
  });
});

function calculatePasswordStrength(password) {
  let strength = 0;
  if (password.length >= 8) strength++;
  if (/[a-z]/.test(password)) strength++;
  if (/[A-Z]/.test(password)) strength++;
  if (/[0-9]/.test(password)) strength++;
  if (/[^a-zA-Z0-9]/.test(password)) strength++;
  return strength;
}
