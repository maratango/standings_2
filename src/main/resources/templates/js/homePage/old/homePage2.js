//document.write("chikimiki");

var loginInput = document.getElementById("login");
var passwordInput = document.getElementById("password");

var entranceButton = document.getElementById("entrance");
var registrationButton = document.getElementById("registration");
var skipLoginingButton = document.getElementById("skipLogining");

var messageBlock = document.getElementById("message");

// var entranceButton = document.homePage.entrance;
// var registrationButton = document.homePage.registration;
// var skipLoginingButton = document.homePage.skipLogining;

entranceButton.addEventListener("click", userEntrance);
registrationButton.addEventListener("click", userRegistration);
skipLoginingButton.addEventListener("click", userskipLogining);


function userEntrance(e) {
    console.log("go");

    var entranceDTO = {
        login: loginInput.value,
        age: passwordInput.value
    };

    try {
        fetch("/entrance", {
            method: "POST",
            headers: {"Accept": "application/json", "Content-Type": "application/json"},
            body: JSON.stringify(entranceDTO)
        })
            .then(response => response.json())
            .then(userInfo => console.log("result: userInfo{id=" + userInfo.id + "}"))
            .catch(error => console.log(error));
    } catch (e) {
        console.log(e);
    }

    console.log("end");
    // e.preventDefault();
}

async function userRegistration(e) {
    messageBlock.textContent = "нажата кнопка регистрации";
    e.preventDefault();
}

async function userskipLogining(e) {
    messageBlock.textContent = "нажата кнопка Пропустить";
    e.preventDefault();
}
