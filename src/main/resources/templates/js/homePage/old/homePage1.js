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


async function userEntrance(e) {
    // messageBlock.textContent = document.homePage.login.value;
    messageBlock.textContent = loginInput.value;
    // console.log(loginInput.value);

    setTimeout(() => {console.log("поехали");}, 1000);

    var entranceDTO = {
        login: loginInput.value,
        age: passwordInput.value
    };
    setTimeout(() => {console.log(entranceDTO);}, 2000);

    var entranceDtoJson = JSON.stringify(entranceDTO);
    // setTimeout(() => {console.log(entranceDtoJson);}, 2000);

    try {
        setTimeout(() => {console.log(JSON.stringify(entranceDTO));}, 3000);
        // отправляет запрос и получаем ответ
        await fetch("/entrance", {
            method: "POST",
            headers: {"Accept": "application/json", "Content-Type": "application/json"},
            body: JSON.stringify(entranceDTO)
        });
        // const resp = await response.json();
        // console.log(resp);
    } catch (e) {
        console.log(e);
    }

    // console.log(response);

    // // если запрос прошел нормально
    // if (response.ok === true) {
    //     // получаем данные
    //     const userInf = await response.json();
    //     console.log(userInf);
    //     // переходим на страницу турниров
    //     location.assign("/tournaments/" + userInf.id);
    // }

    e.preventDefault();
}

async function userRegistration(e) {
    messageBlock.textContent = "нажата кнопка регистрации";
    e.preventDefault();
}

async function userskipLogining(e) {
    messageBlock.textContent = "нажата кнопка Пропустить";
    e.preventDefault();
}
