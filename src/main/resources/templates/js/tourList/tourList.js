
var userId = sessionStorage.getItem("userId");

var addField = document.getElementById("addField");
var addButton = document.getElementById("addButton");

var addMessage = document.getElementById("addMessage");
var messageText = sessionStorage.getItem("messageText");
addMessage.innerText = messageText;

addButton.addEventListener("click", createNewTournament);

var ul = document.getElementById("ul-1");

getTournaments(ul);
sessionStorage.setItem("messageText", "");

async function getTournaments(ulElement) {
    try {
        const urlText = "/tournaments/" + userId;
        const response = await fetch(urlText, {
            method: "GET",
            headers: {"Accept": "application/json"}
        });

        const tournamentList = await response.json();

        console.log(tournamentList);

        var tournamentNum = 0;
        tournamentList.forEach(tournament => {
            tournamentNum++;

            var a = document.createElement("a");
            console.log(a.nodeType);
            a.setAttribute("class", "ma button-3 a-1");
            // a.setAttribute("href", "/tournament/" + tournament.id);
            // a.innerText = tournament.name;
            a.setAttribute("data-id", tournament.id); //хз зачем этот атрибут, не используется
            a.append(tournament.name)
            a.addEventListener("click", e => {
                getTournamentGames(tournament.id);
            });

            var a2 = document.createElement("a");
            console.log(a.nodeType);
            a2.setAttribute("class", "ma button-3 a-2");
            // a2.setAttribute("href", "/tournament/" + tournament.id + "/delete");
            // a2.innerText = "удалить";
            a2.setAttribute("data-id", tournament.id); //хз зачем этот атрибут, не используется
            a2.append("Удалить")
            a2.addEventListener("click", e => {
                deleteTournament(tournament.id);
            });

            var li = document.createElement("li");
            console.log(li.nodeType);
            if (tournamentNum % 2 == 1) {
                li.setAttribute("class", "df li-1 li-2");
            } else {
                li.setAttribute("class", "df li-1");
            }

            li.append(a);
            li.append(a2);
            console.log("смотрим li");
            console.log(li);
            ulElement.append(li);
        });

        console.log(ulElement.innerHTML);

    } catch (error) {
        console.log(error);
    }
}

async function getTournamentGames(id) {
    sessionStorage.setItem("tournamentId", id);
    location.assign("/tournament");
}

async function deleteTournament(id) {
    const deleteUrlText = "/tournament/" + id;
    const response = await fetch(deleteUrlText, {
        method: "DELETE",
        headers: { "Accept": "application/json" }
    });
    location.reload();
}

async function createNewTournament(ev) {
    console.log("go createNewTournament");

    const creatingRequestDTO = {
        userId: userId,
        tourName: addField.value
    };

    const response = await fetch("/tournament/add", {
        method: "POST",
        headers: {"Accept": "application/json", "Content-Type": "application/json"},
        body: JSON.stringify(creatingRequestDTO)
    });

    const creatingResponseDTO = await response.json();
    // addMessage.innerText = creatingResponseDTO.creatingResult;
    sessionStorage.setItem("messageText", creatingResponseDTO.creatingResult);

    location.reload();
}