
var entranceButton = document.getElementById("entrance");
var registrationButton = document.getElementById("registration");
var skipLoginingButton = document.getElementById("skipLogining");

entranceButton.addEventListener("click", userEntrance);
registrationButton.addEventListener("click", userRegistration);
skipLoginingButton.addEventListener("click", userskipLogining);

var messageBlock = document.getElementById("message");


async function userEntrance(ev) {
    console.log("go");

    try {
        const loginInfoDTO = {
            login: document.getElementById("login").value,
            password: document.getElementById("password").value
        };

        const response = await fetch("/entrance", {
            method: "POST",
            headers: {"Accept": "application/json", "Content-Type": "application/json"},
            body: JSON.stringify(loginInfoDTO)
        });

        const entranceInfoDTO = await response.json();
        console.log("result: entranceInfoDTO{userId=" + entranceInfoDTO.userId + "}")

        if (entranceInfoDTO.userId != 0) {
            sessionStorage.setItem("userId", entranceInfoDTO.userId);
            // location.assign("/templates/html/tourList.html");
            location.assign("/tourList");
        } else {
            messageBlock.textContent = "неверный логин или пароль";
        }

    } catch (error) {
        console.log(error);
    }

    console.log("end");
}

async function userRegistration(ev) {
    location.assign("/registration");
}

async function userskipLogining(ev) {
    messageBlock.textContent = "нажата кнопка Пропустить";
}
