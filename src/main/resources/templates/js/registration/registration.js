
var registrationButton = document.getElementById("registration");
registrationButton.addEventListener("click", registrateUser);

var messageBlock = document.getElementById("message");

async function registrateUser(ev) {

    try {

        const loginInfoDTO = {
            login: document.getElementById("login").value,
            password: document.getElementById("password").value
        };

        const response = await fetch("/registrateUser", {
            method: "POST",
            headers: {"Accept": "application/json", "Content-Type": "application/json"},
            body: JSON.stringify(loginInfoDTO)
        });

        const creatingResponseDTO = await response.json();

        messageBlock.textContent = creatingResponseDTO.creatingResult;

    } catch (error) {
        console.log(error);
    }
}
