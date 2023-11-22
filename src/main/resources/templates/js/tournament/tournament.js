
var tournamentId = sessionStorage.getItem("tournamentId");

var addField = document.getElementById("addField");
var addButton = document.getElementById("addButton");
var addFromFileButton = document.getElementById("addFromFileButton");

var addMessage = document.getElementById("addMessage");
var messageText = sessionStorage.getItem("messageText");
addMessage.innerText = messageText;

addButton.addEventListener("click", createNewGame);
addFromFileButton.addEventListener("click", createNewGamesFromFile);

var matchList = document.getElementById("table_2");
var resultTable = document.getElementById("table_1");

getMatches(matchList);
getTournamentResult(resultTable);
sessionStorage.setItem("messageText", "");

async function getMatches(matchListElement) {
    try {
        const urlText = "/games/" + tournamentId;
        console.log("getMatches urlText: " + urlText);
        const response = await fetch(urlText, {
            method: "GET",
            headers: {"Accept": "application/json"}
        });

        const gameList = await response.json();

        var resultRowNum = 0;
        gameList.forEach(gameResult => {
            resultRowNum++;

            var tr = document.createElement("tr");
            if (resultRowNum % 2 == 1) {
                tr.setAttribute("class", "li-1 td-tr-1");
            } else {
                tr.setAttribute("class", "li-1");
            }

            tr.append(createTableTd("tb-td-ht", gameResult.homeTeam));
            tr.append(createTableTd("tb-td-e-2", null));
            tr.append(createTableTd("tb-td-g", "" + gameResult.homeGoal));
            tr.append(createTableTd("tb-td-p", ":"));
            tr.append(createTableTd("tb-td-g", "" + gameResult.guestGoal));
            tr.append(createTableTd("tb-td-e-2", null));
            tr.append(createTableTd("tb-td-gt", gameResult.guestTeam));

            const td = document.createElement("td");
            td.setAttribute("class", "tb-td-d");
            const d = document.createElement("div");
            d.setAttribute("class", "df")
            const a = document.createElement("a");
            a.setAttribute("class", "ma button-3 a-2");
            a.append("Удалить");

            a.addEventListener("click", e => {
                deleteGame(gameResult.id);
            });

            d.append(a);
            td.append(d);
            tr.append(td);

            matchListElement.append(tr);
        });

    } catch (error) {
        console.log(error);
    }
}

async function getTournamentResult(resultTableElement) {
    try {
        const urlText = "/tournament/" + tournamentId;
        console.log("getTournamentResult urlText: " + urlText);
        const response = await fetch(urlText, {
            method: "GET",
            headers: {"Accept": "application/json"}
        });

        const tournamentResultList = await response.json();

        var resultRowNum = 0;
        tournamentResultList.forEach(tournamentResult => {
            resultRowNum++;

            var tr = document.createElement("tr");
            if (resultRowNum % 2 == 1) {
                tr.setAttribute("class", "td-tr-1");
            }

            tr.append(createTableTd("tb-td-first", tournamentResult.place));
            tr.append(createTableTd("tb-td-team-name", tournamentResult.teamName));
            tr.append(createTableTd("tb-td", "" + tournamentResult.points));
            tr.append(createTableTd("tb-td", "" + tournamentResult.gameCount));
            tr.append(createTableTd("tb-td", "" + tournamentResult.winCount));
            tr.append(createTableTd("tb-td", "" + tournamentResult.drawCount));
            tr.append(createTableTd("tb-td", "" + tournamentResult.lossCount));
            tr.append(createTableTd("tb-td", "" + tournamentResult.goalsScoredCount));
            tr.append(createTableTd("tb-td", "" + tournamentResult.goalsСoncededCount));
            tr.append(createTableTd("tb-td", "" + tournamentResult.goalsDifferenceBetween));

            resultTableElement.append(tr);
        });

    } catch (error) {
        console.log(error);
    }
}

function createTableTd(cssClassName, tdValue) {
    const td = document.createElement("td");
    td.setAttribute("class", cssClassName);
    if (tdValue) {
        td.append(tdValue);
    }
    return td;
}

async function createNewGame(ev) {
    const creatingRequestDTO = {
        tournamentId: tournamentId,
        gameData: addField.value
    };

    const response = await fetch("/game/add", {
        method: "POST",
        headers: {"Accept": "application/json", "Content-Type": "application/json"},
        body: JSON.stringify(creatingRequestDTO)
    });

    const creatingResponseDTO = await response.json();
    sessionStorage.setItem("messageText", creatingResponseDTO.creatingResult);
    location.reload();
}

async function createNewGamesFromFile(ev) {
    console.log("createNewGamesFromFile start");

    const urlText = "/game/add-from-file/" + tournamentId;

    const loadFiles = document.getElementById("loadFiles");
    const formData = new FormData();
    formData.append('loadFile', loadFiles.files[0]);

    console.log("createNewGamesFromFile formData:" + formData.get("loadFile"));

    const response = await fetch(urlText, {
        method: "POST",
        body: formData
    });

    const creatingResponseDTO = await response.json();

    console.log("createNewGamesFromFile creatingResult:" + creatingResponseDTO.creatingResult);
    if (creatingResponseDTO.creatingResult) {
        sessionStorage.setItem("messageText", creatingResponseDTO.creatingResult);
    }
    location.reload();
}

async function deleteGame(id) {
    const deleteUrlText = "/game/" + id;
    const response = await fetch(deleteUrlText, {
        method: "DELETE",
        headers: { "Accept": "application/json" }
    });
    location.reload();
}