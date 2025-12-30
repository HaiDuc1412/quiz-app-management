document.addEventListener("DOMContentLoaded", function () {
  const btnCreate = document.getElementById("btnCreate");
  const addRoleSection = document.getElementById("addRoleSection");

  btnCreate.addEventListener("click", function () {
    addRoleSection.classList.add("active");
  });

});
