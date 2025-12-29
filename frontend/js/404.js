// Hàm quay về trang chủ
function goHome() {
  window.location.href = "/";
}

document.addEventListener("keypress", function (e) {
  if (e.key === "Enter") {
    goHome();
  }
});
