document.addEventListener("DOMContentLoaded", function () {
  const btnCreate = document.getElementById("btnCreate");
  const btnAddAnswer = document.getElementById("btnAddAnswer");
  const addQuestionSection = document.getElementById("addQuestionSection");
  const answerListSection = document.getElementById("answerListSection");
  const addAnswerSection = document.getElementById("addAnswerSection");

  // Show add question form
  btnCreate.addEventListener("click", function () {
    addQuestionSection.classList.add("active");
    answerListSection.classList.remove("active");
    addAnswerSection.classList.remove("active");
  });

  // Show answers section
  btnShowAnswers.addEventListener("click", function () {
    answerListSection.classList.add("active");
    addAnswerSection.classList.remove("active");
  });


});