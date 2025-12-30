function goHome() {
  globalThis.location.href = "/";
}

document.addEventListener("keypress", function (e) {
  if (e.key === "Enter") {
    goHome();
  }
});
